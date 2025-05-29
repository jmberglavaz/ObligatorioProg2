package um.edu.uy.entities;

import um.edu.uy.TADs.Interfaces.MyList;

public class Director {
    private String nombre;
    private MyList<Integer> listaPeliculas;

    public Director(String nombre, MyList<Integer> listaPeliculas) {
        this.nombre = nombre;
        this.listaPeliculas = listaPeliculas;
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
