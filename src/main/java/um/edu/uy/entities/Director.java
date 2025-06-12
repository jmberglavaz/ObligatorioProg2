package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.TADs.Sorting;

public class Director {
    private String nombre;
    private final MyList<Integer> listaPeliculas;

    public Director(String nombre) {
        this.nombre = nombre;
        this.listaPeliculas = new MyLinkedListImpl<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MyList<Integer> getListaPeliculas() {
        return listaPeliculas;
    }

    public void agregarPelicula(int idPelicula) {
        this.listaPeliculas.add(idPelicula);
    }
}
