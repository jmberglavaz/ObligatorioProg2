package um.edu.uy.TADs.Interfaces;

import um.edu.uy.Exceptions.ElementAlreadyExist;

public interface MyHash<K,T> {
    void insert(K clave, T data) throws ElementAlreadyExist;
    boolean contains(K clave);
    void delete(K clave);
    int size();
    T get(K clave);
}
