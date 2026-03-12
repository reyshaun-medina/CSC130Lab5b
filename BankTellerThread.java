package lab5;

import java.util.Random;

public class BankTellerThread extends Thread {
	private int idNumber; // teller's id number
	private ArrayQueue<Customer> queue; // the queue for the customers
	private long startIdleTime = System.currentTimeMillis(); // start of the idle time
	private long endIdleTime = startIdleTime; // end of idle time
	private int count; // number of customers processed by this teller
	private ProducerThread producer; // the producer thread

	public BankTellerThread(int id, ArrayQueue<Customer> queue, ProducerThread producer) {
		idNumber = id;
		final int len = queue.getSize();

		for (int i = 0; i < len; i++) {
			this.queue.enqueue(queue.dequeue());
		}

		this.producer = producer;
	}

	@Override
	public void run() {
		while (producer.isAlive()) {
			if (!queue.isEmpty()) {
				try {
					Customer customer = queue.dequeue();
					startIdleTime = System.currentTimeMillis();
					customer.setTransactionStartTime(startIdleTime);

					long a = 1_000;
					long b = 15_000;
					Random rand = new Random();
					long sleepTimeMillis = rand.nextLong(b - a + 1) + a;

					System.out.println("Minute: " + startIdleTime + "\nTeller " + idNumber + ": [idle time: "
							+ customer.getWaitTime() + " minutes, processing transaction for cutomer: "
							+ customer.getIdNumber() + "]");

					try {
						sleep(sleepTimeMillis);
						startIdleTime = System.currentTimeMillis();
						customer.setEndTime(startIdleTime);
						
						System.out.println("Minute: " + startIdleTime + "\nTeller " + idNumber + 
								"idle time: " + (((endIdleTime - startIdleTime) / 1_000)) + ", processed:");
						System.out.println(customer.toString());
						count++;
						startIdleTime = System.currentTimeMillis();						
					} catch (InterruptedException e) {
						System.out.println(e.toString());
					}
				} catch (QueueEmptyException e) {
					System.out.println(e.toString());
				}
			}
		}
		
		System.out.println("Teller " + idNumber + " processed " + count + " customer(s).");
	}
}
