package um.edu.uy.entities;

import um.edu.uy.TADs.Implementations.MyArrayListImpl;
import um.edu.uy.TADs.Interfaces.MyList;

public class Genero {
    private int id;
    private String nombre;
    private MyList<Integer> listaPeliculas;

    public Genero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.listaPeliculas = new MyArrayListImpl<>();
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

    public void agregarPelicula(int id) {
        listaPeliculas.add(id);
    }

    //Funcion de prueba
    public void printPeliculas() {
        System.out.println(nombre);
        for (int i = 0; i < listaPeliculas.size(); i++) {
            System.out.println(listaPeliculas.get(i));
        }
    }
}
