package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class CargaDeEvaluaciones {
    private CSVReader lectorCSV;
    private String[] lineaDatos;

    public CargaDeEvaluaciones() {
        try{
            InputStream archivoDatos = CargaDePeliculas.class.getResourceAsStream("/ratings_1mm.csv");
            BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(archivoDatos));
            this.lectorCSV = new CSVReader(bufferLectura);
            this.lineaDatos = lectorCSV.readNext();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos(MyHash<Integer, Pelicula> peliculas) throws CsvValidationException, IOException {
        long tiempoInicio = System.currentTimeMillis();
        int cantidad = 0;
        long tiempoRecorrido = 0;
        long tiempoBusqueda = 0;
        long tiempoInsercion = 0;

        System.out.println("Iniciando carga de evaluaciones...");

        while ((lineaDatos = lectorCSV.readNext()) != null) {
            long t0 = System.nanoTime();

            int idUsuario = -1;
            Date fecha = null;
            Integer idPelicula = -1;
            float calificacion = 0;
            try {
                idUsuario = Integer.parseInt(lineaDatos[0]);
                idPelicula = Integer.parseInt(lineaDatos[1]);
                calificacion = Float.parseFloat(lineaDatos[2]);
                fecha = new Date(Long.parseLong(lineaDatos[3]));
            } catch (Exception ignored) {}

            long t1 = System.nanoTime();

            if (idUsuario >= 0) {
                long t2 = System.nanoTime();
                Pelicula pelicula = peliculas.get(idPelicula);
                long t3 = System.nanoTime();

                if (pelicula != null) {
                    pelicula.agregarEvaluacion(new Evaluacion(idUsuario, calificacion, fecha));
                }

                long t4 = System.nanoTime();

                tiempoRecorrido += (t1 - t0);
                tiempoBusqueda += (t3 - t2);
                tiempoInsercion += (t4 - t3);
                cantidad++;
            }
        }

        long tiempoFin = System.currentTimeMillis();
        mostrarEstadisticasCarga(tiempoInicio, tiempoInsercion, tiempoFin, tiempoBusqueda, tiempoRecorrido, cantidad);
    }

    private void mostrarEstadisticasCarga(long tiempoInicio, long tiempoInsercion, long tiempoFin, long tiempoBusqueda, long tiempoRecorrido, long cantidad){
        System.out.println("===== ESTADISTICAS DE CARGA DE EVALUACIONES =====");
        System.out.println("Total: " + (tiempoFin - tiempoInicio) + " ms");
        System.out.println("Promedio de recorrido por línea: " + tiempoRecorrido / cantidad / 1_000_000.0 + " ms");
        System.out.println("Promedio búsqueda en hash: " + tiempoBusqueda / cantidad / 1_000_000.0 + " ms");
        System.out.println("Promedio inserción en película: " + tiempoInsercion / cantidad / 1_000_000.0 + " ms");
    }
}
