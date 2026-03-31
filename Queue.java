package lab5b;

/**
 * Simple queue interface used by the simulation. Implementations may be
 * thread-safe (like ArrayQueue) and throw RuntimeException or specific
 * QueueEmptyException/QueueFullException on error conditions.
 *
 * @param <T> the type stored in the queue
 */
public interface Queue<T> {
	/**
	 * Insert an item at the rear of the queue.
	 * Implementations should document concurrency semantics.
	 * @param item the item to add
	 * @throws RuntimeException or QueueFullException if the queue cannot accept the item
	 */
	void enqueue(T item) throws RuntimeException;
	/**
	 * Remove and return the item at the front of the queue.
	 * @return the removed item
	 * @throws RuntimeException or QueueEmptyException if the queue is empty
	 */
	T dequeue() throws RuntimeException;
	/**
	 * Peek at the front element without removing it.
	 * @return the front element
	 * @throws RuntimeException if the queue is empty
	 */
	T front() throws RuntimeException;
	/**
	 * @return current number of items in the queue
	 */
	int getSize();
	/**
	 * @return true if the queue buffer is at capacity
	 */
	boolean isFull();
	/**
	 * @return true if there are no items in the queue
	 */
	boolean isEmpty();
}
