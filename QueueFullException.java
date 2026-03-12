package lab5;

public class QueueFullException extends RuntimeException {
	private String message;
	
	public QueueFullException() {
		message = "Queue is full.";
	}
	
	public QueueFullException(String s) {
		message = s;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
