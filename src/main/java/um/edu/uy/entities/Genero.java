package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

public class Genero {
    private int id;
    private String nombre;
    private MyList<Pelicula> listaPeliculas;

    public Genero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.listaPeliculas = new MyLinkedListImpl<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MyList<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(MyList<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    public void agregarPelicula(Pelicula tempPeli) {
        listaPeliculas.add(tempPeli);
    }
}
