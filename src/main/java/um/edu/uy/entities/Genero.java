package um.edu.uy.entities;

import um.edu.uy.TADs.Interfaces.MyList;

public class Genero {
    private int id;
    private String nombre;
    private MyList<Integer> listaPeliculas;

    public Genero(int id, String nombre, MyList<Integer> listaPeliculas) {
        this.id = id;
        this.nombre = nombre;
        this.listaPeliculas = listaPeliculas;
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

    public MyList<Integer> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(MyList<Integer> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }
}
