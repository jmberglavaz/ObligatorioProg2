package um.edu.uy.entities;

import um.edu.uy.TADs.Interfaces.MyList;

import java.util.Date;

public class Pelicula {
    private int id;
    private String titulo;
    private Date fechaDeEstreno;
    private int ingresos;
    private MyList<Evaluacion> listaEvaluaciones;

    public Pelicula(int id, String titulo, Date fechaDeEstreno, int ingresos) {
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

    public Date getFechaDeEstreno() {
        return fechaDeEstreno;
    }

    public void setFechaDeEstreno(Date fechaDeEstreno) {
        this.fechaDeEstreno = fechaDeEstreno;
    }

    public int getIngresos() {
        return ingresos;
    }

    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }
}
