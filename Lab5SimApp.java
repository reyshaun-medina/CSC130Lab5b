package lab5b;

public class Lab5SimApp {
	private static final int ARGS_SIZE = 5;
	
	public static void main(String[] args) {
		if (args.length != ARGS_SIZE) {
			System.out.println("Enter " + ARGS_SIZE + " values");
		}
		
		int[] input = new int[ARGS_SIZE];
		int tellers;
		int queueSize;
		int averageArrivalTime;
		long simiulationTime;
		
		ThreadGroup tg = new ThreadGroup("tellers");
		Thread[] teller = null;
		ProducerThread producer;
		ArrayQueue queue;
		
		for (int i = 0; i < ARGS_SIZE; i++) {
			try {
				input[i] = Integer.parseInt(args[i]);
			} catch (NumberFormatException error) {
				System.err.print(error);
			}
		}
		
		tellers = input[0];
		queueSize = input[1];
		averageArrivalTime = input[2] * 1_000;
		simiulationTime = SimulationTime.minutesToMilisecs(input[3]);
	}
}
