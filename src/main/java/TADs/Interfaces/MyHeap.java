package TADs.Interfaces;

import um.edu.uy.Exceptions.EmptyListException;

public interface MyHeap<T extends Comparable<T>> {
    void insert (T data);
    T deleteAndObtain() throws EmptyListException;
    int size();
}
