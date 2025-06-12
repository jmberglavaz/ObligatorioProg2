package um.edu.uy.TADs.Hash;

import java.util.Iterator;

public class MyHashIterator<T> implements Iterator<T> {
    private final HashNode<?, T>[] table;
    private final HashNode<?, T> deleteNode;
    private int currentIndex;

    public MyHashIterator(HashNode<?, T>[] table, HashNode<?, T> deleteNode) {
        this.table = table;
        this.deleteNode = deleteNode;
        this.currentIndex = 0;
        moveToNextValid(); // Mover al primer elemento válido
    }

    @Override
    public boolean hasNext() {
        return currentIndex < table.length;
    }

    @Override
    public T next() {
        if (!hasNext()){
            throw new RuntimeException("");// Cambiar excepcion
        }
        T currentData = table[currentIndex].getData();
        moveToNextValid();
        return currentData;
    }

    private void moveToNextValid() {
        while ((currentIndex < table.length) && (table[currentIndex + 1] != null || table[currentIndex + 1] != deleteNode)) {
            currentIndex++;
        }
    }
}
