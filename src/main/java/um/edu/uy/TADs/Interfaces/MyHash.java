package um.edu.uy.TADs.Interfaces;

import um.edu.uy.Exceptions.ElementAlreadyExist;

public interface MyHash<T> {
    void insert(String clave, T data) throws ElementAlreadyExist;
    boolean contains(String clave);
    void delete(String clave);
    int size();
}
