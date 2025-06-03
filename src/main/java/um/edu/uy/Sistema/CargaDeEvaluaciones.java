package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;

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
            this.reader = new CSVReader(new InputStreamReader(rutaDeDatos));
            this.dataLine = reader.readNext(); // Se lee la primera fila ya que esta indica solamente los nombres de cada columna y no es una evaluacion
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void cargaDeDatos(MyHash<Integer, Pelicula> listaDePeliculas) throws CsvValidationException, IOException {
        while((dataLine = reader.readNext()) != null){
            int idUsuario = -1;
            Date fecha = null;
            Integer idPelicula = -1;
            float rating = 0;
            try {
                idUsuario = Integer.parseInt(dataLine[0]);
                idPelicula = Integer.parseInt(dataLine[1]);
                rating = Float.parseFloat(dataLine[2]);
                fecha = new Date(Long.parseLong(dataLine[3]));
            } catch (Exception ignored) {
            }

            if (idUsuario >= 0) {
                Pelicula tempPelicula = listaDePeliculas.get(idPelicula);
                if (tempPelicula != null) {
                    tempPelicula.agregarEvaluacion(new Evaluacion(idUsuario, rating, fecha));
                }
            }
        }
    }
}
