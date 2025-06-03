package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.Implementations.MyArrayListImpl;
import um.edu.uy.TADs.Implementations.MyHashImplCloseLineal;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.TADs.Interfaces.MyList;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Idioma;
import um.edu.uy.entities.Pelicula;

public class CargaDePeliculas {
    CSVReader reader;
    MyHash<Integer, Pelicula> listaDePeliculas;
    MyHash<Integer, Genero> listaDeGeneros;
    MyHash<String, Idioma> listaDeIdiomas;
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
        this.listaDeGeneros = new MyHashImplCloseLineal<>(131);
        this.listaDeIdiomas = new MyHashImplCloseLineal<>(23);
        try{
            cargaDeDatos();
        } catch (IOException | CsvValidationException ignored) {
            System.out.println("Error al cargar los datos de las peliculas");
        }
    }

    public void cargaDeDatos() throws IOException, CsvValidationException {
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

                Pelicula tempPelicula = new Pelicula(numericId, dataLine[8], dataLine[12], ganancias);
                try {
                    listaDePeliculas.insert(numericId, tempPelicula);
                } catch (ElementAlreadyExist ignored) {}

                // Se registran los generos no registrados, si ya existe solo agrega la pelicula a la lista del genero
                MyList<Genero> generos = verifiyGeneros(dataLine[3]);
                for (int iter = 0; iter < generos.size(); iter++) {
                    Genero tempGenero = generos.get(iter);
                    try{
                        this.listaDeGeneros.insert(tempGenero.getId(),tempGenero);
                        tempGenero.agregarPelicula(numericId);
                    } catch (ElementAlreadyExist ignored) {
                        tempGenero = listaDeGeneros.get(tempGenero.getId());
                        tempGenero.agregarPelicula(numericId);
                    }
                }

                // Se registran los idiomas no registrados, si ya existe solo agrega la pelicula a la lista del idioma
                Idioma tempIdioma = new Idioma(dataLine[7]);
                try {
                    listaDeIdiomas.insert(dataLine[7],tempIdioma);
                    tempIdioma.agregarPelicula(numericId);
                } catch (ElementAlreadyExist ignored) {
                    tempIdioma = listaDeIdiomas.get(dataLine[7]);
                    tempIdioma.agregarPelicula(numericId);
                }
            }
        }
    }

    public MyHash<Integer, Pelicula> getListaDePeliculas() {
        return listaDePeliculas;
    }

    public MyHash<Integer, Genero> getListaDeGeneros() {
        return listaDeGeneros;
    }

    public MyHash<String, Idioma> getListaDeIdiomas() {
        return listaDeIdiomas;
    }

    private MyList<Genero> verifiyGeneros(String input){
        MyList<Genero> generos = new MyArrayListImpl<>();
        Pattern pattern = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            generos.add(new Genero(id, name));
        }
        return generos;
    }
}
