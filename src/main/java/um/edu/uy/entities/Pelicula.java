package um.edu.uy.entities;

import um.edu.uy.TADs.Implementations.MyLinkedListImpl;
import um.edu.uy.TADs.Interfaces.MyList;

public class Pelicula {
    private int id;
    private String titulo;
    private String fechaDeEstreno;
    private long ingresos;
    private MyList<Evaluacion> listaEvaluaciones;
    private MyList<String> listaDeActores;

    public Pelicula(int id, String titulo, String fechaDeEstreno, long ingresos) {
        this.id = id;
        this.titulo = titulo;
        this.fechaDeEstreno = fechaDeEstreno;
        this.ingresos = ingresos;
        listaEvaluaciones = new MyLinkedListImpl<>();
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

    public void agregarEvaluacion(Evaluacion evaluacion){
        listaEvaluaciones.add(evaluacion);
    }

    public MyList<Evaluacion> getListaEvaluaciones() {
        return listaEvaluaciones;
    }

    private int cantidadEvaluaciones() {
        return listaEvaluaciones.size();
    }

    public float getPromedioDeEvaluaciones() {
        float sumaDeCalificaciones = 0;
        for (int i = 0; i < listaEvaluaciones.size(); i++) {
            Evaluacion evaluacion = listaEvaluaciones.get(i);
            float calificacion = evaluacion.getCalificacion();
            sumaDeCalificaciones += calificacion;
        }
        return (float) (sumaDeCalificaciones / cantidadEvaluaciones());
    }
  
   public void setListaDeActores(MyList<String> actores){
        listaDeActores = actores;
}
