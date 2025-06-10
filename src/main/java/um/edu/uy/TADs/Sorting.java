package um.edu.uy.TADs;

import um.edu.uy.TADs.Heap.MyHeapImpl;
import um.edu.uy.TADs.Heap.MyHeap;

import java.util.Arrays;


public class Sorting<T extends Comparable<T>> {

    public T[] bubbleSort(T[] array){
        for (int extIter = array.length - 1; extIter > 0 ; extIter--){
            for (int interIter = 0 ; interIter < extIter ; interIter++){
                if (array[interIter].compareTo(array[interIter+1]) > 0){
                    T tempObject = array[interIter];
                    array[interIter] = array[interIter + 1];
                    array[interIter + 1] = tempObject;
                }
            }
        }
        return array;
    }

    public T[] bubbleSortMejorado(T[] arreglo) {
        boolean huboIntercambio;
        for (int iteracionExterna = arreglo.length - 1; iteracionExterna > 0; iteracionExterna--) {
            huboIntercambio = false;
            for (int iteracionInterna = 0; iteracionInterna < iteracionExterna; iteracionInterna++) {
                if (arreglo[iteracionInterna].compareTo(arreglo[iteracionInterna + 1]) > 0) {
                    T temporal = arreglo[iteracionInterna];
                    arreglo[iteracionInterna] = arreglo[iteracionInterna + 1];
                    arreglo[iteracionInterna + 1] = temporal;
                    huboIntercambio = true;
                }
            }
            // Si no hubo intercambios, el arreglo ya está ordenado
            if (!huboIntercambio) {
                break;
            }
        }
        return arreglo;
    }

    public T[] ordenamientoParImpar(T[] arreglo) {
        int longitudArreglo = arreglo.length;

        for (int pasada = 0; pasada < longitudArreglo; pasada++) {
            for (int indice = 1; indice < longitudArreglo - 1; indice += 2) {
                if (arreglo[indice].compareTo(arreglo[indice + 1]) > 0) {
                    intercambiar(arreglo, indice, indice + 1);
                }
            }

            for (int indice = 0; indice < longitudArreglo - 1; indice += 2) {
                if (arreglo[indice].compareTo(arreglo[indice + 1]) > 0) {
                    intercambiar(arreglo, indice, indice + 1);
                }
            }
        }
        return arreglo;
    }

    public T[] selectionSort(T[] array){
        for (int externalIter = 0; externalIter < array.length ; externalIter++){
            T tempObject = array[externalIter];
            int indexToChange = externalIter;
            for (int internalIter = externalIter; internalIter < array.length ; internalIter++){
                if (tempObject.compareTo(array[internalIter]) > 0){
                    tempObject = array[internalIter];
                    indexToChange = internalIter;
                }
            }
            array[indexToChange] = array[externalIter];
            array[externalIter] = tempObject;
        }
        return array;
    }

    public T[] insercionSort(T[] array){
        for (int externalIter = 0; externalIter < array.length; externalIter++){
            for (int internalIter = externalIter; internalIter < array.length - 1; internalIter++) {
                T tempObject = array[internalIter];
                if (tempObject.compareTo(array[internalIter + 1]) > 0) {
                    array[internalIter] = array[internalIter + 1];
                    array[internalIter + 1] = tempObject;
                }
            }
        }
        return array;
    }

    public T[] heapSort(T[] array){
        MyHeap<T> tempHeap = new MyHeapImpl<>(array.length, true);
        for (T insertIter : array){
            tempHeap.insert(insertIter);
        }
        for (int deleteIter = 0; deleteIter < array.length; deleteIter++){
            array[deleteIter] = tempHeap.deleteAndObtain();
        }
        return array;
    }

    public T[] mergeSort(T[] array) {
        if (array.length < 2) {
            return array;
        }
        int midIndex = array.length / 2;

        T[] tempArray1 = Arrays.copyOfRange(array, 0, midIndex);
        T[] tempArray2 = Arrays.copyOfRange(array, midIndex, array.length);

        tempArray1 = mergeSort(tempArray1);
        tempArray2 = mergeSort(tempArray2);

        return merge(array, tempArray1, tempArray2);
    }

    public T[] merge(T[] array, T[] tempArray1, T[] tempArray2) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < tempArray1.length && j < tempArray2.length) {
            if (tempArray1[i].compareTo(tempArray2[j]) <= 0) {
                array[k++] = tempArray1[i++];
            } else {
                array[k++] = tempArray2[j++];
            }
        }

        while (i < tempArray1.length) {
            array[k++] = tempArray1[i++];
        }

        while (j < tempArray2.length) {
            array[k++] = tempArray2[j++];
        }

        return array;
    }

    public T[] quickSort(T[] arreglo) {
        if (arreglo.length < 2) {
            return arreglo;
        }
        quickSortAuxiliar(arreglo, 0, arreglo.length - 1);
        return arreglo;
    }

    private void quickSortAuxiliar(T[] arreglo, int indiceBajo, int indiceAlto) {
        if (indiceBajo < indiceAlto) {
            int indicePivote = particion(arreglo, indiceBajo, indiceAlto);

            quickSortAuxiliar(arreglo, indiceBajo, indicePivote - 1);
            quickSortAuxiliar(arreglo, indicePivote + 1, indiceAlto);
        }
    }

    private int particion(T[] arreglo, int indiceBajo, int indiceAlto) {
        T pivote = arreglo[indiceAlto];
        int indiceMenor = indiceBajo - 1;

        for (int indiceActual = indiceBajo; indiceActual < indiceAlto; indiceActual++) {
            if (arreglo[indiceActual].compareTo(pivote) <= 0) {
                indiceMenor++;
                intercambiar(arreglo, indiceMenor, indiceActual);
            }
        }

        intercambiar(arreglo, indiceMenor + 1, indiceAlto);
        return indiceMenor + 1;
    }

    private void intercambiar(T[] arreglo, int posicion1, int posicion2) {
        T temporal = arreglo[posicion1];
        arreglo[posicion1] = arreglo[posicion2];
        arreglo[posicion2] = temporal;
    }
}