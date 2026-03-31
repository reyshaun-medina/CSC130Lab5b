package lab5b;

/**
 * Simulation application entry point. Expects 5 command-line arguments to
 * configure the bank teller simulation and starts the producer and teller threads.
 *
 * Arguments (5 expected):
 * 1. number of tellers
 * 2. queue size
 * 3. average arrival time (in seconds)
 * 4. unused placeholder (kept for backwards compatibility)
 * 5. simulation duration (in minutes)
 */
public class Lab5SimApp {
	private static final int ARGS_SIZE = 5;

	public static void main(String[] args) {
		// verify the expected number of arguments
		if (args.length != ARGS_SIZE) {
			System.out.println("Enter " + ARGS_SIZE + " values");
			return;
		}

		// integer array to hold converted argument values
		int[] input = new int[ARGS_SIZE];
		int tellers;
		int queueSize;
		int averageArrivalTime;
		long simiulationTime;

		// ThreadGroup to group all teller threads for easy monitoring
		ThreadGroup tg = new ThreadGroup("tellers");
		Thread[] teller = null;
		ProducerThread producer;
		ArrayQueue<Customer> queue;

		// parse and validate integer arguments
		try {
			for (int i = 0; i < ARGS_SIZE; i++) {
				input[i] = Integer.parseInt(args[i]);
			}
		} catch (NumberFormatException error) {
			// malformed integer input; print and exit
			System.err.print(error);
			return;
		}

		// assign parsed values to descriptive variables
		tellers = input[0];
		queueSize = input[1];
		// convert seconds to milliseconds for average arrival time
		averageArrivalTime = input[2] * 1_000;
		// create the shared queue with the requested capacity
		queue = new ArrayQueue<Customer>(queueSize);
		// create array to hold thread references for each teller
		teller = new Thread[tellers];
		// convert simulation duration (minutes) into milliseconds
		simiulationTime = SimulationTime.minutesToMilisecs(input[4]);
		// create the producer thread with the queue and timing configuration
		producer = new ProducerThread(queue, simiulationTime, averageArrivalTime);

		System.out.println("Tellers are getting ready. The bank will open in 10 minutes...");
		// instantiate SimulationTime (not strictly necessary because methods are static)
		SimulationTime time = new SimulationTime();
		// start producer: will wait 10s before generating customers
		producer.start();

		// create and start teller threads; they will poll the shared queue
		for (int i = 0; i < tellers; i++) {
			teller[i] = new Thread(tg, new BankTellerThread(i, queue, producer));
			teller[i].start();
		}

		// wait for the producer to finish generating customers (busy-wait)
		while (producer.isAlive()) {}
		System.out.println("\nThe producer thread has finished...");
		// wait for all teller threads to finish processing (busy-wait)
		while (tg.activeCount() > 0) {}
		System.out.println("\nThe tellers have completed all transactions...\nEnd of program...");
	}
}
