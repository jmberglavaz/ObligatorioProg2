package um.edu.uy.TADs.Implementations;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.Exceptions.ValueNoExist;
import um.edu.uy.TADs.Interfaces.MyHash;

public class MyHashImplCloseLineal<T> implements MyHash<T> {
    private HashNode<String,T>[] table;
    private final HashNode<String,T> deleteNode = new HashNode<>(null, null);


    public MyHashImplCloseLineal(int size) {
        this.table =  new HashNode[size];
    }

    @Override
    public void insert(String clave, T data) throws ElementAlreadyExist {
        if (contains(clave)){
            throw new ElementAlreadyExist("The object already exist");
        }

        int index = Math.abs(clave.hashCode()) % table.length;
        int originalIndex = index;

        while ((this.table[index] != null) && (this.table[index] != deleteNode)){
            index = (index + 1) % table.length;

            if (index == originalIndex){
                incrementLength();
                index = Math.abs(clave.hashCode()) % table.length;
                originalIndex = index;
            }
        }
        this.table[index] = new HashNode<>(data, clave);
    }

    @Override
    public boolean contains(String clave) {
        return !(search(clave)<0);
    }

    @Override
    public void delete(String clave) {
        int index = search(clave);
        if (index < 0){
            throw new ValueNoExist("This object not exist");
        }
        this.table[index] = deleteNode;
    }

    private void incrementLength(){
        HashNode<String,T>[] oldTable = this.table;
        int temp = this.table.length * 2;
        boolean isPrime = semiPrimo(temp);
        while (!isPrime){
            temp++;
            isPrime = semiPrimo(temp);
        }

        this.table = new HashNode[temp];

        for (HashNode<String,T> node : oldTable){
            if ((node != null) && (node != deleteNode)){
                int index = Math.abs(node.getKey().hashCode()) % this.table.length;
                while (this.table[index] != null) {
                    index = (index + 1) % table.length;
                }
                this.table[index] = node;
            }
        }
    }


    private boolean semiPrimo(int number){
        int[] listaPrimos = {2, 3, 5, 7, 11, 13, 17, 19, 23};
        for (int numeroIter : listaPrimos){
            if (number % numeroIter == 0){
                return false;
            }
        }
        return true;
    }

    private int search(String clave) {
        int index = Math.abs(clave.hashCode()) % table.length;
        int initialIndex = index;

        while (this.table[index] != null) {
            if (this.table[index] != deleteNode && this.table[index].getKey().equals(clave)) {
                return index;
            }
            index = (index + 1) % table.length;
            if (initialIndex == index){
                return -1;
            }
        }
        return -1;
    }
}
