package TADs.Interfaces;

import um.edu.uy.Exceptions.EmptyStack;

public interface MyStack<T> extends Iterable<T> {

    public void push(T value);

    public T pop() throws EmptyStack;

    public T top() throws EmptyStack;

    public boolean isEmpty();

    public void makeEmpty();

    public int getSize();

}
