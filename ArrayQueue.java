package lab5b;

/**
 * A simple thread-safe circular array-based queue implementation.
 * This queue uses a fixed-size array as a circular buffer and tracks
 * the front index, the next available rear index, and the current
 * number of items stored.
 *
 * @param <T> the type of elements held in this queue
 */
public class ArrayQueue<T> implements Queue<T> {
	// internal array to hold queue data; created as Object[] and cast to T[]
	private T[] data;
	// index of the next element to dequeue
	private int front, rear, numItems;
	// default capacity used by the no-arg constructor
	public final int CAPACITY = 100;
	
	/**
	 * Default constructor - creates a queue with default capacity.
	 */
	public ArrayQueue(){
		// Generic array creation via Object[] then cast: unavoidable in Java generics
		data = (T[]) new Object[CAPACITY];
		// front and rear both start at 0 (empty queue)
		front = rear = numItems = 0;
	}
	
	/**
	 * Constructs a queue with the requested capacity.
	 *
	 * @param numItems the capacity of the queue (must be &gt;= 1)
	 * @throws RuntimeException if the requested capacity is less than 1
	 */
	public ArrayQueue(int numItems) throws RuntimeException{
		if(numItems < 1)
			throw new RuntimeException("Invalid numItems for the queue");
		// allocate backing array with the requested size
		data = (T[]) new Object[numItems];
		front = rear = numItems = 0;
	}	
	/**
	 * Adds an element to the rear of the queue.
	 * This method is synchronized to allow safe concurrent access.
	 *
	 * @param d the item to enqueue
	 * @throws RuntimeException if the queue is full
	 */
	public synchronized void enqueue(T d) throws QueueFullException {
		// defensive check: ensure there's available capacity
		if(isFull())
			throw new RuntimeException("Queue full exception...");
		// place the item at the current rear index in the circular buffer
		data[rear] = d;
		// advance rear index; wrap around when reaching end of array
		rear = (rear + 1) % data.length;
		// increase stored item count
		numItems++;
	}
	
	/**
	 * Removes and returns the element at the front of the queue.
	 * This method is synchronized to allow safe concurrent access.
	 *
	 * @return the dequeued item
	 * @throws RuntimeException if the queue is empty
	 */
	public synchronized T dequeue() throws RuntimeException{
		// verify there is at least one element to remove
		if(isEmpty())
			throw new RuntimeException("Queue empty exception...");
		// fetch the value at front
		T temp = data[front];
		// optionally clear reference to help GC (not required): data[front] = null;
		// move front pointer forward, wrapping around at array length
		front = (front + 1) % data.length;
		// decrement number of stored items
		numItems--;
		return temp;
	}
	
	/**
	 * Returns (without removing) the item at the front.
	 *
	 * @return the front item
	 * @throws RuntimeException if the queue is empty
	 */
	public synchronized T front() throws RuntimeException{
		if(isEmpty())
			throw new RuntimeException("Queue empty exception...");
		// return element at front index
		return  data[front];
	}
	
	/**
	 * Returns (without removing) the item at the rear.
	 * The rear item is computed from front + numItems - 1 modulo length.
	 *
	 * @return the last item in the queue
	 * @throws RuntimeException if the queue is empty
	 */
	public synchronized T rear() throws RuntimeException{
		if(isEmpty())
			throw new RuntimeException("Queue empty exception...");
		// compute last stored element's index in circular buffer
		return  data[(front+numItems-1) % data.length];
	}
	
	/**
	 * @return true if the queue has no elements
	 */
	public synchronized boolean isEmpty(){
		return numItems == 0;
	}
	
	/**
	 * @return true if the queue has reached its capacity
	 */
	public synchronized boolean isFull(){
		return numItems == data.length;
	}
	public synchronized int getSize(){
		return numItems;
	}

	public String toString(){
		String str = "Queue: " + numItems + "\n";
		int trav = front;
		// iterate through stored elements and append their toString
		for(int i = 0; i < numItems; i++){
			str += data[trav] + "->";
			trav = (trav + 1) % data.length; // advance circularly
		}
		return str;
	}

	/**
	 * Exposes the current front index (useful for debugging/inspection)
	 *
	 * @return the front index within the internal array
	 */
	public synchronized int getFront() {
		return front;
		//return (data.length - numItems - rear) % data.length ;
	}
	
	/**
	 * Computes the internal index used for the last element in the queue.
	 *
	 * @return the internal index for the rear-most stored element
	 */
	public synchronized int getRear() {
		// compute rear-most stored element index based on front and count
		return ((front+numItems)-1) % data.length;
	}
}
