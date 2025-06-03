package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class CargaDeEvaluaciones {
    CSVReader reader;
    String[] dataLine;

    public CargaDeEvaluaciones() {
        try{
            InputStream rutaDeDatos = CargaDePeliculas.class.getResourceAsStream("/ratings_1mm.csv"); //Ruta del archivo ratings_1mm.csv
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(rutaDeDatos));
            this.reader = new CSVReader(bufferedReader);
            this.dataLine = reader.readNext(); // Se lee la primera fila ya que esta indica solamente los nombres de cada columna y no es una evaluacion
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void cargaDeDatos(MyHash<Integer, Pelicula> listaDePeliculas) throws CsvValidationException, IOException {
        System.out.println("Iniciando carga de evaluaciones...");
        long totalInicio = System.currentTimeMillis();
        int contador = 0;
        long totalDeRecorridos = 0;
        long totalBusqueda = 0;
        long totalInsert = 0;

        while ((dataLine = reader.readNext()) != null) {
            long t0 = System.nanoTime();

            int idUsuario = -1;
            Date fecha = null;
            Integer idPelicula = -1;
            float rating = 0;
            try {
                idUsuario = Integer.parseInt(dataLine[0]);
                idPelicula = Integer.parseInt(dataLine[1]);
                rating = Float.parseFloat(dataLine[2]);
                fecha = new Date(Long.parseLong(dataLine[3]));
            } catch (Exception ignored) {}

            long t1 = System.nanoTime();

            if (idUsuario >= 0) {
                long t2 = System.nanoTime();
                Pelicula tempPelicula = listaDePeliculas.get(idPelicula);
                long t3 = System.nanoTime();

                if (tempPelicula != null) {
                    tempPelicula.agregarEvaluacion(new Evaluacion(idUsuario, rating, fecha));
                }

                long t4 = System.nanoTime();

                totalDeRecorridos += (t1 - t0);
                totalBusqueda += (t3 - t2);
                totalInsert += (t4 - t3);
                contador++;
            }
        }

        long totalFin = System.currentTimeMillis();

//        informeDeTiemposDeCarga(totalInicio, totalFin, totalDeRecorridos, totalBusqueda, totalInsert, contador);
    }

    // Developer method
    private void informeDeTiemposDeCarga(long totalInicio, long totalFin, long totalDeRecorridos, long totalBusqueda, long totalInsert, long contador){
        System.out.println("##### Tiempos de carga de evaluaciones #####");
        System.out.println("Total: " + (totalFin - totalInicio) + " ms");
        System.out.println("Promedio de recorrido por línea: " + totalDeRecorridos / contador / 1_000_000.0 + " ms");
        System.out.println("Promedio busqueda en hash: " + totalBusqueda / contador / 1_000_000.0 + " ms");
        System.out.println("Promedio inserción en película: " + totalInsert / contador / 1_000_000.0 + " ms");
    }
}
