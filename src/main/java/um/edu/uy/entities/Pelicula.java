package um.edu.uy.entities;

import um.edu.uy.TADs.Interfaces.MyList;

import java.util.Date;

public class Pelicula {
    private int id;
    private String titulo;
    private String fechaDeEstreno;
    private long ingresos;
    private MyList<Evaluacion> listaEvaluaciones;

    public Pelicula(int id, String titulo, String fechaDeEstreno, long ingresos) {
        this.id = id;
        this.titulo = titulo;
        this.fechaDeEstreno = fechaDeEstreno;
        this.ingresos = ingresos;
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

    public String getFechaDeEstreno() {
        return fechaDeEstreno;
    }

    public void setFechaDeEstreno(String fechaDeEstreno) {
        this.fechaDeEstreno = fechaDeEstreno;
    }

    public long getIngresos() {
        return ingresos;
    }

    public void setIngresos(long ingresos) {
        this.ingresos = ingresos;
    }
}
