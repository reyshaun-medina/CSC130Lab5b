package lab5b;

/**
 * Exception thrown when a dequeue or peek is attempted on an empty queue.
 */
public class QueueEmptyException extends RuntimeException {
	private String message;
	
	public QueueEmptyException() {
		// default message used when no custom text is provided
		message = "Queue is Empty";
	}
	
	public QueueEmptyException(String s) {
		// allow custom message text
		message = s;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
