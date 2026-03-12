package lab5;

public interface Queue<T> {
	void enqueue(T item) throws RuntimeException;
	T dequeue() throws RuntimeException;
	T front() throws RuntimeException;
	int getSize();
	boolean isFull();
	boolean isEmpty();
}