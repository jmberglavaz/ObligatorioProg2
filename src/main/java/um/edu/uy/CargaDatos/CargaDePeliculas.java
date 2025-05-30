package um.edu.uy.CargaDatos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.Implementations.MyHashImplCloseLineal;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.UMovie;

public class CargaDePeliculas {
    URL resource;
    CSVReader reader;
    MyHash<Pelicula> listaDePeliculas;

    public CargaDePeliculas(){
        try {
            resource = getClass().getClassLoader().getResource("movies_metadata.csv");
            reader = new CSVReader(new FileReader(resource.getFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.listaDePeliculas = new MyHashImplCloseLineal<>(45437);
    }

    public MyHash<Pelicula> lecturaDePeliculas() throws CsvValidationException, IOException {
        String[] lineData = reader.readNext();
        while ((lineData=reader.readNext()) != null){
            String id = lineData[5];
            String presupuesto = lineData[13];
            if (id.matches("\\d+")){
                Pelicula peliculaAAgregar;
                if (presupuesto.matches("\\d+")) {
                    peliculaAAgregar = new Pelicula(Integer.parseInt(id), lineData[8], lineData[12], Long.parseLong(lineData[13]));
                } else {
                    peliculaAAgregar = new Pelicula(Integer.parseInt(id), lineData[8], lineData[12], 0);
                }
                try {
                    this.listaDePeliculas.insert(id, peliculaAAgregar);
                } catch (ElementAlreadyExist ignored) {
                }
            }
        }
        return this.listaDePeliculas;
    }
}
