package um.edu.uy.TADs.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayListIterator<T> implements Iterator<T> {

    private final T[] array;
    private final int size;
    private int currentIndex;

    public MyArrayListIterator(T[] array, int size) {
        this.array = array;
        this.size = size;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < size;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos en el iterador");
        }
        return array[currentIndex++];
    }
}