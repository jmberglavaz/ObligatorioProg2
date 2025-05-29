package um.edu.uy.entities;

public class Coleccion {
    private int id;
    private String titulo;
    private int cantidadDePeliculas;

    public Coleccion(int id, String titulo, int cantidadDePeliculas) {
        this.id = id;
        this.titulo = titulo;
        this.cantidadDePeliculas = cantidadDePeliculas;
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

    public int getCantidadDePeliculas() {
        return cantidadDePeliculas;
    }

    public void setCantidadDePeliculas(int cantidadDePeliculas) {
        this.cantidadDePeliculas = cantidadDePeliculas;
    }
}
