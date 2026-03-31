package lab5b;

/**
 * Represents a bank customer used by the simulation.
 * Tracks the customer's id, arrival time, transaction start and end times
 * and provides convenience methods to compute wait and transaction durations.
 */
public class Customer {
	// unique id assigned by the producer
	private int number;
	// when the teller started processing this customer's transaction (simulation time units)
	private long transactionStartTime;
	// when the customer arrived at the bank (simulation time units)
	private long arrivalTime;
	// when the transaction completed (simulation time units)
	private long transactionEndTime;
	
	/**
	 * Create a new customer with an id and the minute they arrived.
	 *
	 * @param number the customer's id number
	 * @param arrivalTime the minute the customer arrived (in simulation minutes)
	 */
	public Customer(int number, long arrivalTime) {
		this.number = number;
		this.arrivalTime = arrivalTime;
		transactionStartTime = 0;
		transactionEndTime = 0;
	}
	
	/**
	 * @return the customer's id number
	 */
	public final int getIdNumber() {
		return number;
	}
	
	/**
	 * @return the arrival minute for this customer (simulation units)
	 */
	public final long getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * @return how long the transaction lasted (end - start) in simulation minutes
	 */
	public final long getTransactionTime() {
		return transactionEndTime - transactionStartTime;
	}
	
	/**
	 * @return how long the customer waited before the teller began service
	 */
	public final long getWaitTime() {
		return transactionStartTime - arrivalTime;
	}
	
	/**
	 * Record when the transaction started (in simulation minutes)
	 */
	public void setTransactionStartTime(long transactionStartTime) {
		this.transactionStartTime = transactionStartTime;
	}
	
	/**
	 * Update the recorded arrival time (not commonly used after construction)
	 */
	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Record when the transaction completed (in simulation minutes)
	 */
	public void setEndTime(long transactionEndTime) {
		this.transactionEndTime = transactionEndTime;
	}
	
	@Override
	public String toString() {
		// provide a readable summary of the customer's timeline
		return "Customer: " + getIdNumber() + ", entered the bank at minute " + getArrivalTime() +
				", [Transaction Time: " + getTransactionTime() +
				" mins, Wait Time: " + getWaitTime() + " mins]";
	}
}
