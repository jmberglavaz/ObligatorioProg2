package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.Implementations.MyHashImplCloseLineal;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.entities.Pelicula;

public class CargaDePeliculas {
    CSVReader reader;
    MyHash<Pelicula> listaDePeliculas;
    String[] dataLine;

    public CargaDePeliculas(){
        try{
           InputStream rutaDeDatos = CargaDePeliculas.class.getResourceAsStream("/movies_metadata.csv"); //Ruta del archivo movie_metadata
            this.reader = new CSVReader(new InputStreamReader(rutaDeDatos));
           this.dataLine = reader.readNext(); // Se lee la primera fila ya que esta indica solamente los nombres de cada columna y no es una pelicula
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        this.listaDePeliculas = new MyHashImplCloseLineal<>(49999); // Tamaño decidido para que no haya colisiones y que se mejore el tiempo de carga a cambio de ocupar ligeramente mas memoria
    }

    public MyHash<Pelicula> registrarPeliculas() throws IOException, CsvValidationException {
        while ((dataLine = reader.readNext()) != null) {
            String id = dataLine[5];
            int numericId = -1;

            try {
                numericId = Integer.parseInt(id);
            } catch (NumberFormatException ignored) {}

            if (numericId >= 0){

                long ganancias = 0;
                try {
                    ganancias = Long.parseLong(dataLine[13]);
                } catch (NumberFormatException ignored) {}

                try {
                    listaDePeliculas.insert(id, new Pelicula(numericId, dataLine[8], dataLine[12], ganancias));
                } catch (ElementAlreadyExist ignored) {}
            }
        }
        return listaDePeliculas;
    }
}
