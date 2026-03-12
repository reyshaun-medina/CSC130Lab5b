package lab5;

public class Customer {
	private int number;
	private long transactionStartTime;
	private long arrivalTime;
	private long transactionEndTime;
	
	public Customer(int number, long arrivalTime) {
		this.number = number;
		this.arrivalTime = arrivalTime;
	}
	
	public final int getIdNumber() {
		return number;
	}
	
	public final long getTransactionStartTime() {
		return transactionStartTime;
	}
	
	public final long getArrivalTime() {
		return arrivalTime;
	}
	
	public final long getTransactionEndTime() {
		return transactionEndTime;
	}
	
	public final long getWaitTime() {
		return transactionStartTime - arrivalTime;
	}
	
	public void setTransactionStartTime(long transactionStartTime) {
		this.transactionStartTime = transactionStartTime;
	}
	
	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public void setEndTime(long transactionEndTime) {
		this.transactionEndTime = transactionEndTime;
	}
	
	@Override
	public String toString() {
		return "Customer: " + getTransactionEndTime() + ", entered the bank at minute " + getArrivalTime() +
				",[Transaction Time: " +(getTransactionStartTime() - getTransactionEndTime()) +
				" mins, Wait Time" + getWaitTime() + " mins]";
	}
}
