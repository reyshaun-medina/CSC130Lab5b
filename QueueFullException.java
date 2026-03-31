package lab5b;

/**
 * Exception thrown when an enqueue is attempted on a full queue.
 */
public class QueueFullException extends RuntimeException {
	private String message;
	
	public QueueFullException() {
		// default message used when no custom text is provided
		message = "Queue is full.";
	}
	
	public QueueFullException(String s) {
		// allow custom message text
		message = s;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
