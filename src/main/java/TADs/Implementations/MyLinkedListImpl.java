package TADs.Implementations;

import um.edu.uy.Exceptions.EmptyListException;
import um.edu.uy.Exceptions.ListOutOfIndex;
import um.edu.uy.Exceptions.ValueNoExist;
import um.edu.uy.Interfaces.MyList;
import um.edu.uy.Nodos.SimpleNode;

public class MyLinkedListImpl<T> implements MyList<T> {
    private SimpleNode<T> head;
    private SimpleNode<T> tail;
    private int size;

    @Override
    public void add(T data) {
        SimpleNode<T> aAgregar = new SimpleNode<>(data);
        if (isEmpty()){
            this.head = aAgregar;
            this.tail = aAgregar;
        } else {
            this.tail.setNextNode(aAgregar);
            this.tail = aAgregar;
        }
        this.size++;
    }

    @Override
    public void add(T data, int index) throws ListOutOfIndex {
        if (index == 0) {
            addFirst(data);
        } else if (index == size) {
            addLast(data);
        } else {
            SimpleNode<T> tempNode = new SimpleNode<>(data);
            SimpleNode<T> prevNode = search(index - 1);
            tempNode.setNextNode(prevNode.getNextNode());
            prevNode.setNextNode(tempNode);
            size++;
        }
    }

    @Override
    public void addFirst(T data) {
        SimpleNode<T> tempNode = this.head;
        this.head = new SimpleNode<>(data);
        this.head.setNextNode(tempNode);
        size++;
    }

    public void addLast(T data){
        if (isEmpty()){
            add(data);
            return;
        }
        this.tail.setNextNode(new SimpleNode<>(data));
        this.tail = this.tail.getNextNode();
        size++;
    }

    @Override
    public T delete(int index) throws ListOutOfIndex, EmptyListException {
        if (isEmpty()){
            throw new EmptyListException("Empty list, cannot remove an object");
        }
        if (index >= size){
            throw new ListOutOfIndex("Index is invalid");
        } else if (index == size-1){
            return deleteLast();
        } else if (index == 0){
            return deleteFirst();
        }

        SimpleNode<T> previousNode = search(index-1);
        T result = previousNode.getNextNode().getData();
        previousNode.setNextNode(previousNode.getNextNode().getNextNode());
        return result;
    }

    @Override
    public T deleteLast() throws EmptyListException {
        if (isEmpty()){
            throw new EmptyListException("Empty List, cannot remove an object");
        }
        T result = this.tail.getData();
        if (size == 1){
            this.head = null;
            this.tail = null;
            size--;
            return result;
        }



        SimpleNode<T> currentNode = this.head;
        while (!currentNode.equals(this.tail)){
            currentNode = currentNode.getNextNode();
        }

        this.tail = currentNode;
        this.tail.setNextNode(null);
        size--;
        return result;
    }

    public T deleteFirst() throws EmptyListException {
        if (isEmpty()){
            throw new EmptyListException("Empty list, cannot remove an object");
        }
        T result = this.head.getData();
        this.head = this.head.getNextNode();
        size--;
        return result;
    }

    @Override
    public void deleteValue(T data) throws EmptyListException, ListOutOfIndex, ValueNoExist {
        if (isEmpty()){
            throw new EmptyListException("Empty list, cannot remove an object");
        }
        int indexNodeToRemove = searchIndex(data);
        if (indexNodeToRemove < 0){
            throw new ValueNoExist("Object is not in the list");
        }
        SimpleNode<T> previousNode = search(indexNodeToRemove-1);
        previousNode.setNextNode(previousNode.getNextNode().getNextNode());
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T data) {
        SimpleNode<T> currentNode = this.head;
        while (currentNode != null){
            if (currentNode.getData().equals(data)){
                return true;
            }
            currentNode = currentNode.getNextNode();
        }
        return false;
    }

    @Override
    public T get(int index) throws ListOutOfIndex, EmptyListException {
        return search(index).getData();
    }

    @Override
    public void intercambiate(int firstIndex, int secondIndex) throws EmptyListException, ListOutOfIndex {
        SimpleNode<T> firstNode = search(firstIndex);
        SimpleNode<T> secondNode = search(secondIndex);
        T tempData = firstNode.getData();
        firstNode.setData(secondNode.getData());
        secondNode.setData(tempData);
    }

    @Override
    public boolean isEmpty() {
        return this.head==null;
    }

    private SimpleNode<T> search(int index){
        if (isEmpty()){
            throw new EmptyListException("Empty List");
        }
        if (index >= size || index < 0){
            throw new ListOutOfIndex("Not valid Index");
        } else if (index == size-1){
            return this.tail;
        } else {
            SimpleNode<T> result = this.head;
            for (int iter = 0; iter < index; iter++) {
                result = result.getNextNode();
            }
            return result;
        }
    }

    private SimpleNode<T> search(T data){
        if (isEmpty()){
            throw new EmptyListException("Empty List");
        }
        SimpleNode<T> current = this.head;
        while (current != null){
            if (current.getData().equals(data)){
                return current;
            }
            current = current.getNextNode();
        }
        throw new ValueNoExist("The object is not in the list");
    }

    private int searchIndex(T data){
        SimpleNode<T> currentNode = this.head;
        int indexToSend = 0;
        while (currentNode != null){
            if (currentNode.getData().equals(data)){
                return indexToSend;
            }
            currentNode = currentNode.getNextNode();
            indexToSend++;
        }
        return -1;
    }
}
