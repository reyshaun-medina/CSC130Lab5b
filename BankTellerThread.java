package lab5b;

import java.util.Random;

/**
 * Represents a bank teller that processes customers from a shared queue.
 * Each teller runs as a separate thread and polls the queue while the
 * producer (customer generator) is alive.
 */
public class BankTellerThread extends Thread {
	// unique identifier for this teller
	private int idNumber; // teller's id number
	// shared queue instance where customers wait
	private ArrayQueue<Customer> queue; // the queue for the customers
	// timestamp when the teller most recently became idle (ms)
	private long startIdleTime = System.currentTimeMillis(); // start of the idle time
	// timestamp when the teller last stopped idling to service a customer (ms)
	private long endIdleTime = startIdleTime;// end of idle time
	// how many customers this teller has processed in its lifetime
	private int count; // number of customers processed by this teller
	// reference to the producer thread so the teller knows when to stop polling
	private ProducerThread producer; // the producer thread

	/**
	 * Creates a teller with the given id that will serve customers from the queue.
	 *
	 * @param id the teller id
	 * @param queue the shared customer queue
	 * @param producer the producer thread (used to know when to stop)
	 */
	public BankTellerThread(int id, ArrayQueue<Customer> queue, ProducerThread producer) {
		// assign the passed-in id
		this.idNumber = id;
		// keep a reference to the shared queue
		this.queue = queue;
		// keep a reference to the producing thread (customer generator)
		this.producer = producer;
		// initialize processed counter
		count = 0;
	}

	/**
	 * Main loop for the teller thread. While the producer is still alive the teller
	 * will try to dequeue customers and process them. Processing simulates a random
	 * transaction time and prints relevant timing and bookkeeping information.
	 */
	@Override
	public void run() {
		// keep working as long as the producer is generating customers
		while (producer.isAlive()) {
			// quick non-blocking check to see if there is anyone in the queue
			if (!queue.isEmpty()) {
				try {
					// Remove next customer from the queue (may throw QueueEmptyException)
					Customer customer = queue.dequeue();
					// record service start instant for idle-time calculation
					this.endIdleTime = System.currentTimeMillis();
					// convert ms to simulation minutes (or seconds depending on SimulationTime impl)
					long startMinute = SimulationTime.timeSinceStart(this.endIdleTime);
					// mark when this customer's transaction began (simulation time)
					customer.setTransactionStartTime(startMinute);

					// Setup random transaction time bounds (real-time ms)
					Random rand = new Random();
					long a = 1_000, b = 15_000; // lower and upper bounds (1s to 15s)
					// generate a random duration in the inclusive range [a, b]
					long transactionTime = rand.nextLong(b - a + 1) + a;

					// Log the start of service (useful during debugging or for tracing)
					System.out.println("Minute: " + startMinute);
					System.out
							.println("Teller " + this.idNumber + " [idle time: " + ((endIdleTime - startIdleTime) / 1_000)
							+ " minutes, processing transaction for customer: " + customer.getIdNumber() + "]");

					// Simulate transaction processing time by sleeping this thread
					try {
						Thread.sleep(transactionTime); // sleep for the computed transaction duration
					} catch (InterruptedException error) {
						// If the teller thread is interrupted while sleeping, print the exception
						System.out.println(error);
					}

					// record when transaction completed (ms) and convert to simulation minute
					long completionTimeMillis = System.currentTimeMillis();
					long endMinute = SimulationTime.timeSinceStart(completionTimeMillis);
					// update the customer's recorded end time in simulation units
					customer.setEndTime(endMinute);

					// Print completion details and the customer's summary
					System.out.println("\nMinute: " + endMinute);
					// show idle time again and indicate the processed customer
					System.out.println("Teller " + this.idNumber + ": " + ((endIdleTime - startIdleTime) / 1_000)
						+ " minutes, processed:");
					System.out.println(customer);
					
					// increment processed customer count for this teller
					count++;
					// update startIdleTime to now — teller will be idle after this instant
					this.startIdleTime = System.currentTimeMillis();
				} catch (QueueEmptyException error) {
					// This can happen if multiple tellers pass the isEmpty check concurrently
					// and one dequeues the last customer before another calls dequeue().
					System.err.println("Queue is empty... Teller " + idNumber + " waiting for a customer");
				}
			}
			// If queue is empty, loop again and check producer.isAlive();
			// Consider adding a small sleep here to avoid busy-waiting in a hot loop.
		}

		// Producer has stopped producing and this teller loop has exited — print final stats
		System.out.println("\nTeller " + idNumber + " processed " + count + " customers.");
	}
}
