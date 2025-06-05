package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CargaDeStaff {
    CSVReader reader;
    String[] dataLine;
    MyHash<Integer, Director> listaDeDirectores;

    public CargaDeStaff() {
        try{
            InputStream rutaDeDatos = CargaDePeliculas.class.getResourceAsStream("/credits.csv"); //Ruta del archivo ratings_1mm.csv
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(rutaDeDatos));
            this.reader = new CSVReader(bufferedReader);
            this.dataLine = reader.readNext(); // Se lee la primera fila ya que esta indica solamente los nombres de cada columna y no es una evaluacion
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        this.listaDeDirectores = new MyHashImplCloseLineal<>(60000);
    }

    public void cargaDeDatos(MyHash<Integer, Pelicula> listaDePelicula){
        int id = -1;
        System.out.println("Iniciando carga de creditos...");

        try{
            id = Integer.parseInt(dataLine[2]);
        } catch (NumberFormatException ignored) {}

        if (id >= 0){
            Pelicula tempPelicula = listaDePelicula.get(id);
            MyList<String> tempActores = verifiyActors(dataLine[0]);

            tempPelicula.setListaDeActores(tempActores);

            MyList<Director> directores = verifiyDirectors(dataLine[1]);
            for (Director tempDirector : directores){
                listaDeDirectores.insert(tempDirector.getId(),tempDirector);
            }
        }
    }

    public MyHash<Integer, Director> getListaDeDirectores() {
        return listaDeDirectores;
    }

    private MyList<String> verifiyActors(String input){
        MyList<String> actores = new MyArrayListImpl<>(5);
        if (input == null || input.trim().isEmpty()) {
            return actores;
        }

        Pattern pattern = Pattern.compile("'name':\\s*'([^']+)'");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            try {
                String nombre = matcher.group(1);
                actores.add(nombre);
            } catch (NumberFormatException ignored) {}
        }
        return actores;
    }

    private MyList<Director> verifiyDirectors(String input){
        MyList<Director> directores = new MyArrayListImpl<>(5);
        if (input == null || input.trim().isEmpty()) {
            return directores;
        }

        Pattern pattern = Pattern.compile("\"id\":\\s*(\\d+),.*?\"job\":\\s*\"([^\"]+)\",.*?\"name\":\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String trabajo = matcher.group(2);
            if (trabajo.equals("Director")) {
                int id = 0;
                try{
                    id = Integer.parseInt(matcher.group(1));
                } catch (NumberFormatException ignored) {}
                directores.add(new Director(matcher.group(3), id));
            }
        }

        return directores;
    }
}
