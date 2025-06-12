package um.edu.uy.Sistema.Consulta4;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Sorting;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;

public class CuartaConsulta {
    private static final Sorting ordenamiento = new Sorting();

    public static void realizarConsulta(MyHash<Integer, Director> listaDeDirectores, MyHash<Integer, Pelicula> listaDePeliculas){
        for (Director tempDirector : listaDeDirectores){
            int count = 0, cantEvaluaciones = 0, largo = tempDirector.getCantidadPeliculas();
            float mediana;

            if (largo <= 1){continue;}

            Float[] listaCalificaciones = new Float[largo];
            for (Integer idPelicula : tempDirector.getListaPeliculas()){
                Pelicula tempPelicula = listaDePeliculas.get(idPelicula);
                listaCalificaciones[count] = tempPelicula.getPromedioDeEvaluaciones();
                cantEvaluaciones += tempPelicula.getListaEvaluaciones().size();
                count++;
            }

            if (cantEvaluaciones <= 100){continue;}

            listaCalificaciones = (Float[]) ordenamiento.quickSort(listaCalificaciones);
            if (listaCalificaciones.length % 2 == 0){
                mediana = (listaCalificaciones[largo/2] + listaCalificaciones[(largo/2) + 1])/2;
            } else {
                mediana = listaCalificaciones[(largo + 1)/2];
            }
        }

    }
}
