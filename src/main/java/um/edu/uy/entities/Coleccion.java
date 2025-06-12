package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

public class Coleccion extends Comparable<Coleccion> {
    private int id;
    private String titulo;
    private MyList<Integer> listaDePeliculas;
    public Coleccion(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.listaDePeliculas = new MyLinkedListImpl<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public MyList<Integer> getListaDePeliculas() {
        return listaDePeliculas;
    }

    public void setListaDePeliculas(MyList<Integer> listaDePeliculas) {
        this.listaDePeliculas = listaDePeliculas;
    }

    public void agregarPelicula(int idPelicula){
        listaDePeliculas.add(idPelicula);
    }

    public int getCantidadPeliculas(){
        return listaDePeliculas.size();
    }
}
