package TADs.Interfaces;

import um.edu.uy.Exceptions.EmptyQueue;

public interface MyQueue<T> extends Iterable<T>{

    void enqueue (T value);

    void enqueueWithPriority (T value);

    T dequeue () throws EmptyQueue;

    boolean isEmpty();

    int getSize();


}
