package um.edu.uy.Sistema.Consulta2;

import um.edu.uy.TADs.Interfaces.MyList;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;

public interface RatingsProcessor<K> {
    void procesar(Integer clave, Pelicula pelicula, MyList<Evaluacion> evaluaciones);
}
