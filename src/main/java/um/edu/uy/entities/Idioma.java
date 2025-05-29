package um.edu.uy.entities;

import um.edu.uy.TADs.Interfaces.MyList;

public class Idioma {
    private String nombre;
    private String acronimo;
    private MyList<Integer> listaPeliculas;

    public Idioma(String nombre, String acronimo, MyList<Integer> listaPeliculas) {
        this.nombre = nombre;
        this.acronimo = acronimo;
        this.listaPeliculas = listaPeliculas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public MyList<Integer> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(MyList<Integer> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }
}
