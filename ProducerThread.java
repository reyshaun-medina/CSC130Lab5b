package lab5;

public class ProducerThread extends Thread {
	private ArrayQueue<Customer> line; // the line customers wait in
	private long simulationTime; // how long to run the simulation
	private int averageArrivalTime; // average time between customer arrivals
	private int count; // number of customers handled
	private final long startTime = System.currentTimeMillis(); // current time

	public ProducerThread(ArrayQueue<Customer> line, long simTime, int avgArrTime) {
		final int len = line.getSize();

		for (int i = 0; i < len; i++) {
			this.line.enqueue(line.dequeue());
		}

		simulationTime = simTime;
		averageArrivalTime = avgArrTime;
	}

	@Override
	public void run() {
		try {
			sleep(10_000);

			while (startTime < simulationTime) {
				try {
					Customer customer = new Customer(count, System.currentTimeMillis());
					
					if (line.isFull()) {
						System.out.println("Queue is full. Customer " + customer.getIdNumber() + " left the bank.");
					} else {
						System.out.println("Customer " + customer.getIdNumber() + " arrived at minute 24.");
						line.enqueue(customer);
						System.out.println("Customer " + customer.getIdNumber() + " added to the queue.");
						
						long waitTime = SimulationTime.timeTillNext(averageArrivalTime);
						sleep(waitTime);
						count++;
					}
				} catch (QueueFullException e) {
					System.out.println(e.toString());
				}
			}
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		
		System.out.println("Thread has stopped and " + count + " customer(s) have processed.");
	}
}
