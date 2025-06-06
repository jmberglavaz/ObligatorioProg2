package um.edu.uy.entities;

import um.edu.uy.TADs.Implementations.MyArrayListImpl;
import um.edu.uy.TADs.Implementations.MyLinkedListImpl;
import um.edu.uy.TADs.Interfaces.MyList;

public class Idioma {
    private String nombre;
    private String acronimo;
    private MyList<Integer> listaPeliculas;

    public Idioma(String acronimo) {
        this.nombre = null;
        this.acronimo = acronimo;
        this.listaPeliculas = new MyLinkedListImpl<>();
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

    public void agregarPelicula(int id) {
        listaPeliculas.add(id);
    }

    public void printPeliculas() {
        System.out.println("#####" + acronimo + "######");
        for (int i = 0; i < listaPeliculas.size(); i++) {
            System.out.println(listaPeliculas.get(i));
        }
        System.out.println("#####" + acronimo + "######");
    }
}
