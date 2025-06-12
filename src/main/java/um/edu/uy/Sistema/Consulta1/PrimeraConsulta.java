package um.edu.uy.Sistema.Consulta1;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Heap.MyHeap;
import um.edu.uy.TADs.Heap.MyHeapImpl;
import um.edu.uy.entities.Idioma;
import um.edu.uy.entities.Pelicula;

public class PrimeraConsulta {
    private static final String[] idiomas = {"en","fr","it","es","pt"};
    private static final String[] nombresIdiomas = {"Ingles", "Frances", "Italiano", "Español", "Portugues"};

    public static void realizarConsulta(MyHash<Integer, Pelicula> listaDePeliculas, MyHash<String, Idioma> listaDeIdiomas) {
        long inicio = System.currentTimeMillis();
        for (int iter = 0; iter < idiomas.length; iter++) {
            String idioma = idiomas[iter];
            String nombreIdioma = nombresIdiomas[iter];
            Idioma idiomaActual = listaDeIdiomas.get(idioma);

            MyHeap<Pelicula> heapPeliculas = new MyHeapImpl<>(1000, false);
            for (Integer idPeliActual : idiomaActual.getListaPeliculas()) {
                heapPeliculas.insert(listaDePeliculas.get(idPeliActual));
            }
            System.out.println("\nTop peliculas en " + nombreIdioma);
            int count = 0;
            while (heapPeliculas.size() > 0 && count < 5) {
                Pelicula tempPeli = heapPeliculas.deleteAndObtain();
                System.out.println(tempPeli.getId() + ", " + tempPeli.getTitulo() + ", " + tempPeli.getListaEvaluaciones().size() + ", " + nombreIdioma);
                count++;
            }
        }
        long fin = System.currentTimeMillis();
        System.out.println("\nTiempo de demora de la consulta: " + (fin - inicio) + "ms");
    }
}
