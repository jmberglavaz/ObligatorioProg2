package um.edu.uy.Sistema.Consulta4;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.HeapKT.MyHeapKT;
import um.edu.uy.TADs.HeapKT.MyHeapKTImplementation;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.TADs.Sorting;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;

public class CuartaConsulta {
    private static final Sorting ordenamiento = new Sorting();

    public static void realizarConsulta(MyHash<String, Director> listaDeDirectores, MyHash<Integer, Pelicula> listaDePeliculas){
        long inicio = System.currentTimeMillis();
        MyHeapKT<Float,Director> resultadoDirectores = new MyHeapKTImplementation<>(listaDeDirectores.size(),false);
        for (Director tempDirector : listaDeDirectores){
            float mediana;
            MyList<Float> evaluaciones = new MyLinkedListImpl<>();

            if (tempDirector.getCantidadPeliculas() <= 1){continue;}

            for (Integer idPelicula : tempDirector.getListaPeliculas()){
                Pelicula tempPelicula = listaDePeliculas.get(idPelicula);
                if (tempPelicula.getCantidadEvaluaciones() == 0){continue;}
                for (Evaluacion tempEvaluacion : tempPelicula.getListaEvaluaciones()){
                    evaluaciones.add(tempEvaluacion.getCalificacion());
                }
            }

            if (evaluaciones.size() <= 100){continue;}


        }

        System.out.println("Top 10 Directores con mejor calificacion");
        for (int iter  = 1; iter <= 10 ; iter++){
            Director tempDirector = resultadoDirectores.deleteAndObtain();
            System.out.println("\nTop " + iter + ": " + tempDirector.getNombre() + " " + tempDirector.getCantEvaluaciones() + " " + tempDirector.getMediana());
        }
        System.out.println("\nTiempo total de consulta: " + (System.currentTimeMillis() - inicio) + "ms\n");
    }
}
