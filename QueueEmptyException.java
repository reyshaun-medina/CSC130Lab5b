package lab5;

public class QueueEmptyException extends RuntimeException {
	private String message;
	
	public QueueEmptyException() {
		message = "Queue is empty.";
	}
	
	public QueueEmptyException(String s) {
		message = s;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
