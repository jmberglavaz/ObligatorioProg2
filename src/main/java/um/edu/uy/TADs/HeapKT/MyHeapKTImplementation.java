package um.edu.uy.TADs.HeapKT;

import um.edu.uy.Exceptions.EmptyHeapException;
import um.edu.uy.TADs.HeapKT.MyHeapKT;

import java.util.Arrays;

public class MyHeapKTImplementation<K extends Comparable<K>, T> implements MyHeapKT<K, T> {
    private HeapNode<K, T>[] heap;
    private int size;
    private final boolean esMinHeap;

    public MyHeapKTImplementation(int tamanioInicial, boolean esMinHeap) {
        if (tamanioInicial < 1) {
            throw new IllegalArgumentException("El tamaño inicial tiene que ser mayor o igual a 1");
        }
        this.heap = (HeapNode<K, T>[]) new HeapNode[tamanioInicial];
        this.size = 0;
        this.esMinHeap = esMinHeap;
    }

    @Override
    public void insert(K key, T data) {
        if (key == null || data == null) {
            throw new IllegalArgumentException("Key y data no pueden ser nulos");
        }

        if (size == heap.length) {
            expandirCapacidad();
        }

        HeapNode<K, T> nuevoNodo = new HeapNode<>(key, data);
        heap[size] = nuevoNodo;
        size++;
        subirNodo(size - 1);
    }

    @Override
    public T deleteAndObtain() throws EmptyHeapException {
        if (isEmpty() || heap[0] == null) {
            throw new EmptyHeapException("Heap vacío");
        }

        T dataRaiz = heap[0].getData();
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        if (size > 0) {
            bajarNodo(0);
        }

        return dataRaiz;
    }

    @Override
    public int size() {
        return size;
    }

    private void subirNodo(int posicion) {
        while (posicion > 0) {
            int posicionPadre = (posicion - 1) / 2;

            if (debeIntercambiar(heap[posicionPadre], heap[posicion])) {
                intercambiarNodos(posicionPadre, posicion);
                posicion = posicionPadre;
            } else {
                break;
            }
        }
    }

    private void bajarNodo(int posicion) {
        int hijoIzquierdo = 2 * posicion + 1;
        while (hijoIzquierdo < size) {
            hijoIzquierdo = 2 * posicion + 1;
            int hijoDerecho = 2 * posicion + 2;
            int hijoSeleccionado = hijoIzquierdo;

            if (hijoDerecho < size) {
                if (esMinHeap) {
                    hijoSeleccionado = heap[hijoIzquierdo].getKey().compareTo(heap[hijoDerecho].getKey()) < 0 ?
                            hijoIzquierdo : hijoDerecho;
                } else {
                    hijoSeleccionado = heap[hijoIzquierdo].getKey().compareTo(heap[hijoDerecho].getKey()) > 0 ?
                            hijoIzquierdo : hijoDerecho;
                }
            }

            if (debeIntercambiar(heap[posicion], heap[hijoSeleccionado])) {
                intercambiarNodos(posicion, hijoSeleccionado);
                posicion = hijoSeleccionado;
            } else {
                break;
            }
        }
    }

    private boolean debeIntercambiar(HeapNode<K, T> padre, HeapNode<K, T> hijo) {
        if (esMinHeap) {
            return padre.getKey().compareTo(hijo.getKey()) > 0;
        } else {
            return padre.getKey().compareTo(hijo.getKey()) < 0;
        }
    }

    private void intercambiarNodos(int i, int j) {
        HeapNode<K, T> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void expandirCapacidad() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    public boolean isEmpty() {
        return size == 0;
    }

//    @Override
//    public void mostrarHeap() {
//        if (heapVacio()) {
//            System.out.println("Heap vacío");
//            return;
//        }
//
//        int niveles = (int) (Math.log(size) / Math.log(2)) + 1;
//        int elementosPorNivel = 1;
//        int count = 0;
//
//        for (int i = 0; i < size; i++) {
//            System.out.print(heap[i].getData() + " ");
//            count++;
//
//            if (count == elementosPorNivel) {
//                System.out.println();
//                elementosPorNivel *= 2;
//                count = 0;
//            }
//        }
//        System.out.println();
//    }
}
