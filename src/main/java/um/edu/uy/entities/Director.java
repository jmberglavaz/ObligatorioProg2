package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.TADs.Sorting;

public class Director {
    private String nombre;
    private final MyList<Pelicula> listaPeliculas;
    private int cantEvaluaciones = 0;
    private float mediana = 0;

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

    public MyList<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void agregarPelicula(Pelicula tempPelicula) {
        this.listaPeliculas.add(tempPelicula);
    }

    public int getCantidadPeliculas(){
        return listaPeliculas.size();
    }

    public int getCantEvaluaciones() {
        return cantEvaluaciones;
    }

    public void setCantEvaluaciones(int cantEvaluaciones) {
        this.cantEvaluaciones = cantEvaluaciones;
    }

    public float getMediana() {
        return mediana;
    }

    public void setMediana(float mediana) {
        this.mediana = mediana;
    }
}
