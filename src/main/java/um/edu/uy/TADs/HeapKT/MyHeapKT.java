package um.edu.uy.TADs.HeapKT;

import um.edu.uy.Exceptions.EmptyHeapException;

public interface MyHeapKT<K, T> {
    void insert(K key, T data);
    T deleteAndObtain() throws EmptyHeapException;
    int size();
}
