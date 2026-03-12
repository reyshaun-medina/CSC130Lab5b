package lab5;

public class ArrayQueue<T> implements Queue<T> {
	private T[] data;
	private int front, rear, numItems;
	public final int CAPACITY = 100;
	
	public ArrayQueue(){
		data = (T[]) new Object[CAPACITY];
		front = rear = numItems = 0;
	}
	public ArrayQueue(int numItems) throws RuntimeException{
		if(numItems < 1)
			throw new RuntimeException("Invalid numItems for the queue");
		data = (T[]) new Object[numItems];
		front = rear = numItems = 0;
	}	
	public synchronized void enqueue(T d) throws QueueFullException {
		if(isFull())
			throw new RuntimeException("Queue full exception...");
		data[rear] = d;
		rear = (rear + 1) % data.length;
		numItems++;
	}
	public synchronized T dequeue() throws RuntimeException{
		if(isEmpty())
			throw new RuntimeException("Queue empty exception...");
		T temp = data[front];
		front = (front + 1) % data.length;
		numItems--;
		return temp;
	}
	public synchronized T front() throws RuntimeException{
		if(isEmpty())
			throw new RuntimeException("Queue empty exception...");
		return  data[front];
	}
	public synchronized T rear() throws RuntimeException{
		if(isEmpty())
			throw new RuntimeException("Queue empty exception...");
		return  data[(front+numItems-1) % data.length];
	}
	
	public synchronized boolean isEmpty(){
		return numItems == 0;
	}
	public synchronized boolean isFull(){
		return numItems == data.length;
	}
	public synchronized int getSize(){
		return numItems;
	}

	public String toString(){
		String str = "Queue: " + numItems + "\n";
		int trav = front;
		for(int i = 0; i < numItems; i++){
			str += data[trav] + "->";
			trav = (trav + 1) % data.length;
		}
		return str;
	}

	public synchronized int getFront() {
		return front;
		//return (data.length - numItems - rear) % data.length ;
	}
	
	public synchronized int getRear() {
		return ((front+numItems)-1) % data.length;
	}
}
