package um.edu.uy.Sistema.Consulta3;

import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.Heap.MyHeapImpl;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Idioma;
import um.edu.uy.entities.Pelicula;

public class Consulta3 {

    public void realizarConsulta(MyHash<Integer, Pelicula> listaPeliculas, MyHash<String, Coleccion> listaColecciones) {
        MyHeapImpl<Integer, Coleccion> coleccionesPorIngresos = new MyHeapImpl<>(listaColecciones.size(), false); // cambiar a <K, T>

        for (int iter = 0; iter < listaColecciones.size(); iter++) { // no sé si es size ak
            if (!(listaColecciones.obtain(iter) == null)) {
                Coleccion coleccion = listaColecciones.obtain(iter);
                long ingresosColeccion = 0;

                for (int idPelicula : coleccion.getListaDePeliculas()) {
                    Pelicula pelicula = listaPeliculas.get(idPelicula);
                    ingresosColeccion += pelicula.getIngresos();
                }

                HeapNode<long, Coleccion> coleccionConIngreso = new HeapNode(ingresosColeccion, coleccion);
                coleccionesPorIngresos.insert(coleccionConIngreso);
            }
        }

        // Imprimir top 10:
        for (int top = 1; top <=10 ; top++) {
            HeapNode<long, Coleccion> coleccionConIngreso = coleccionesPorIngresos.deleteAndObtain();
            Coleccion coleccionTop = coleccionConIngreso.getData();
            long ingresosColeccion = coleccionConIngreso.getKey();
            System.out.println("\n\n Top " + top, "\n     ID de la Colección: " + coleccionTop.getId() + "\n     Título de la Colección: " + coleccionTop.getTitulo() + "\n     Cantidad de películas: " + coleccionTop.getCantidadPeliculas() + "\n     Películas que la componen: " + coleccionTop.getListaDePeliculas() + "\n     Ingresos generados: " + ingresosColeccion);
        }
    }
}
