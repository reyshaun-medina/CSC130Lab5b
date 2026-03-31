package lab5b;

/**
 * Producer thread that simulates customer arrivals over a configured
 * simulation duration. New customers are created and either enqueued or
 * rejected if the queue is full.
 */
public class ProducerThread extends Thread {
	private ArrayQueue<Customer> line; // the line customers wait in
	private long simulationTime; // how long to run the simulation (ms)
	private int averageArrivalTime; // average time between customer arrivals (ms)
	private int count; // number of customers handled
	private final long startTime = System.currentTimeMillis();; // simulation start time (ms)

	/**
	 * @param line the shared queue where customers wait
	 * @param simTime total simulation time in milliseconds
	 * @param avgArrTime mean arrival interval in milliseconds
	 */
	public ProducerThread(ArrayQueue<Customer> line, long simTime, int avgArrTime) {
		this.line = line;
		count = 0;
		simulationTime = simTime;
		averageArrivalTime = avgArrTime;
	}

	/**
	 * Main producer loop. Waits an initial 10 seconds (simulates bank opening)
	 * and then generates customers until the simulation time elapses.
	 */
	@Override
	public void run() {
		try {
			// simulate a short startup delay (bank opening delay)
			Thread.sleep(10_000);
			
			// run until the configured simulationTime has elapsed
			while ((System.currentTimeMillis() - startTime) < simulationTime) {
				int id = 0; // local placeholder to report id in error paths
				
				try {
					// create a new customer using the simulation minute for arrival
					Customer customer = new Customer(count, SimulationTime.timeSinceStart(System.currentTimeMillis()));
					System.out.println("\nCustomer " + customer.getIdNumber() + " arrived at minute " + SimulationTime.timeSinceStart(System.currentTimeMillis()) + ".");
					id = customer.getIdNumber();
					
					// if the queue is full, the arriving customer will be turned away
					if (line.isFull()) {
						System.out.println("Queue is full. Customer " + customer.getIdNumber() + " left the bank.");
					} else {
						// otherwise enqueue the customer for the tellers to process
						line.enqueue(customer);
						System.out.println("Customer " + customer.getIdNumber() + " added to the queue.\n");
					}
					
					// compute a (random) time until the next arrival using the mean arrival time
					long waitTime = SimulationTime.timeTillNext(averageArrivalTime);
					// sleep this producer thread to simulate the interval until the next customer
					Thread.sleep(waitTime);
					// increment the internal customer counter
					count++;
				} catch (QueueFullException e) {
					// defensive catch - should be rare because of isFull() check, but handle it anyway
					System.err.println("Queue is full. Customer " + id + " left the bank.");
				}
			}
		} catch (InterruptedException e) {
			// if the thread is externally interrupted, print the exception and end
			System.out.println(e.toString());
		}
	}
}
