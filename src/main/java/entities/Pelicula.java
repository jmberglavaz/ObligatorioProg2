package entities;

import java.util.Date;

public class Pelicula {
    private int id;
    private String titulo;
    private String idiomaOriginal;
    private String genero;
    private Date fechaDeEstreno;
    private int ingresos;

    // [] evaluaciones


    public Pelicula(int id, String titulo, String idiomaOriginal, String genero, Date fechaDeEstreno, int ingresos) {
        this.id = id;
        this.titulo = titulo;
        this.idiomaOriginal = idiomaOriginal;
        this.genero = genero;
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

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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
