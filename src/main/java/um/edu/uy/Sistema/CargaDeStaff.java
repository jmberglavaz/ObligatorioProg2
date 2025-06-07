package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CargaDeStaff {
    private CSVReader reader;
    private final MyHash<Integer, Director> listaDeDirectores;
    private final Pattern directorPattern = Pattern.compile("\"id\":\\s*(\\d+),.*?\"job\":\\s*\"Director\",.*?\"name\":\\s*\"([^\"]+)\"");
    private final Pattern actorPattern = Pattern.compile("'name': '([^']{3,})'");

    public CargaDeStaff() {
        this.listaDeDirectores = new MyHashImplCloseLineal<>(60000);
        try {
            InputStream rutaDeDatos = CargaDePeliculas.class.getResourceAsStream("/credits.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(rutaDeDatos));
            this.reader = new CSVReader(bufferedReader);
            this.reader.readNext(); // descartar encabezado
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void cargaDeDatos(MyHash<Integer, Pelicula> listaDePeliculas) throws CsvValidationException, IOException {
        System.out.println("Iniciando carga de créditos...");
        long inicio = System.currentTimeMillis();
        int count = 0;

        String[] dataLine;
        while ((dataLine = reader.readNext()) != null) {
            if (dataLine.length < 3) continue;

            int idPelicula;
            try {
                idPelicula = Integer.parseInt(dataLine[2]);
            } catch (NumberFormatException e) {
                continue;
            }

            Pelicula pelicula = listaDePeliculas.get(idPelicula);
            if (pelicula == null) continue;

            String actoresRaw = dataLine[0];
            if (actoresRaw != null && !actoresRaw.isEmpty()) {
                pelicula.setListaDeActores(parseActores(actoresRaw));
            }

            String crewRaw = dataLine[1];
            if (crewRaw != null && crewRaw.contains("Director")) {
                agregarDirectores(crewRaw, idPelicula);
            }

            count++;
        }

        long fin = System.currentTimeMillis();
        System.out.printf("Créditos cargados: %d entradas en %d ms%n", count, (fin - inicio));
    }

    private MyList<String> parseActores(String input) {
        MyList<String> actores = new MyLinkedListImpl<>();
        HashSet<String> vistos = new HashSet<>();

        Matcher matcher = actorPattern.matcher(input);
        while (matcher.find()) {
            String actor = matcher.group(1);
            if (vistos.add(actor)) {
                actores.add(actor);
            }
        }
        return actores;
    }

    private void agregarDirectores(String input, int idPelicula) {
        Matcher matcher = directorPattern.matcher(input);
        while (matcher.find()) {
            try {
                int idDirector = Integer.parseInt(matcher.group(1));
                Director director = listaDeDirectores.get(idDirector);

                if (director == null) {
                    director = new Director(matcher.group(2), idDirector);
                    listaDeDirectores.insert(idDirector, director);
                }

                director.getListaPeliculas().add(idPelicula);
            } catch (NumberFormatException | ElementAlreadyExist ignored) {
            }
        }
    }

    public MyHash<Integer, Director> getListaDeDirectores() {
        return listaDeDirectores;
    }
}
