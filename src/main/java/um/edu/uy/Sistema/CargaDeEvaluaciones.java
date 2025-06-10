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
    private boolean developer;

    public CargaDeEvaluaciones(boolean developer) {
        this.developer = developer;
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
        long inicio = developer ? System.currentTimeMillis() : 0;
        System.out.println("Iniciando carga de evaluaciones...");

        while ((lineaDatos = lectorCSV.readNext()) != null) {
            int idUsuario;
            Date fecha;
            int idPelicula;
            float calificacion;

            try {
                idUsuario = Integer.parseInt(lineaDatos[0]);
                idPelicula = Integer.parseInt(lineaDatos[1]);
                calificacion = Float.parseFloat(lineaDatos[2]);
                fecha = new Date(Long.parseLong(lineaDatos[3])*1000);
            } catch (Exception e) {continue;}


            if (idUsuario >= 0) {
                Pelicula pelicula = peliculas.get(idPelicula);

                if (pelicula != null) {
                    pelicula.agregarEvaluacion(new Evaluacion(idUsuario, calificacion, fecha));
                }
            }
        }
        if(developer){
            long fin = System.currentTimeMillis();
            System.out.println("\nTiempo de carga de ratings_1mm: " + (fin - inicio) + "ms\n");
        }
    }
}
