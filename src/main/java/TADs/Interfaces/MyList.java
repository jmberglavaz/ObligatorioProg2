package TADs.Interfaces;

import um.edu.uy.Exceptions.EmptyListException;
import um.edu.uy.Exceptions.ListOutOfIndex;
import um.edu.uy.Exceptions.ValueNoExist;

public interface MyList<T> {
    void add(T data);
    void add(T data, int index) throws ListOutOfIndex;
    void addFirst(T data);


    T delete(int index) throws ListOutOfIndex, EmptyListException;
    T deleteLast() throws EmptyListException;
    T deleteFirst() throws EmptyListException;
    void deleteValue(T data) throws EmptyListException, ListOutOfIndex, ValueNoExist;

    int size();

    boolean contains(T data);

    T get(int index) throws ListOutOfIndex, EmptyListException;
    void intercambiate(int firstIndex, int secondIndex) throws EmptyListException, ListOutOfIndex;
    boolean isEmpty();
}
