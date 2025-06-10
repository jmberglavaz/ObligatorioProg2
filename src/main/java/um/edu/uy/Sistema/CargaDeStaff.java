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

public class CargaDeStaff {
    private CSVReader readerCSV;
    private final MyHash<Integer, Director> directores;
    private boolean developer;

    private static final String TRABAJO_DIRECTOR = "\"job\": \"Director\"";
    private static final String CLAVE_NOMBRE = "\"name\": \"";
    private static final String CLAVE_ID = "\"id\": ";
    private static final String CLAVE_NOMBRE_ACTOR = "'name': '"; //Se usa una distinta respecto a director por formatos distinos

    public CargaDeStaff(boolean developer) {
        this.developer = developer;
        this.directores = new MyHashImplCloseLineal<>(60000);
        try {
            InputStream archivoDatos = CargaDePeliculas.class.getResourceAsStream("/credits.csv");
            BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(archivoDatos));
            this.readerCSV = new CSVReader(bufferLectura);
            this.readerCSV.readNext();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void cargaDeDatos(MyHash<Integer, Pelicula> peliculas) throws CsvValidationException, IOException {
        long inicio = developer ? System.currentTimeMillis() : 0;
        System.out.println("Iniciando carga de créditos...");

        String[] lineaDatos;
        while ((lineaDatos = readerCSV.readNext()) != null) {
            if (lineaDatos.length < 3) continue;

            int idPelicula;
            try {
                idPelicula = Integer.parseInt(lineaDatos[2]);
            } catch (NumberFormatException e) {
                continue;
            }

            Pelicula pelicula = peliculas.get(idPelicula);
            if (pelicula == null) continue;

            String actoresRaw = lineaDatos[0];
            if (actoresRaw != null && !actoresRaw.isEmpty()) {
                pelicula.setListaDeActores(verifyActores(actoresRaw));
            }

            String equipoRaw = lineaDatos[1];
            if (equipoRaw != null && equipoRaw.contains("Director")) {
                agregarDirectores(equipoRaw, idPelicula);
            }
        }

        if (developer) {
            long fin = System.currentTimeMillis();
            System.out.println("\nTiempo de demora de credits: " + (fin - inicio) + "ms\n");
        }
    }

    private MyList<String> verifyActores(String entrada) {
        MyList<String> actores = new MyLinkedListImpl<>();
        MyHash<String, Boolean> actoresVistos = new MyHashImplCloseLineal<>(100);

        int posicionInicial = 0;
        int longitud = entrada.length();

        while (posicionInicial < longitud) {
            int inicioNombre = entrada.indexOf(CLAVE_NOMBRE_ACTOR, posicionInicial);
            if (inicioNombre == -1) break;

            inicioNombre += CLAVE_NOMBRE_ACTOR.length();
            int finNombre = entrada.indexOf("'", inicioNombre);

            if (finNombre == -1 || finNombre <= inicioNombre) {
                posicionInicial = inicioNombre;
                continue;
            }

            String nombreActor = entrada.substring(inicioNombre, finNombre); // Extraigo el nombre del actor

            if (nombreActor.length() >= 3 && actoresVistos.get(nombreActor) == null) {
                try {
                    actoresVistos.insert(nombreActor, true);
                    actores.add(nombreActor);
                } catch (ElementAlreadyExist ignored) {}
            }

            posicionInicial = finNombre + 1;
        }

        return actores;
    }

    private void agregarDirectores(String entrada, int idPelicula) {
        int posicionInicial = 0;
        int longitud = entrada.length();

        while (posicionInicial < longitud) {
            int posDirector = entrada.indexOf(TRABAJO_DIRECTOR, posicionInicial);
            if (posDirector == -1) break;

            int posId = entrada.lastIndexOf(CLAVE_ID, posDirector);
            if (posId == -1) {
                posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
                continue;
            }

            int inicioId = posId + CLAVE_ID.length();
            int finId = entrada.indexOf(",", inicioId);
            if (finId == -1) finId = entrada.indexOf("}", inicioId);
            if (finId == -1) {
                posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
                continue;
            }

            int idDirector;
            try {
                String idStr = entrada.substring(inicioId, finId).trim();
                idDirector = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
                continue;
            }

            int posNombre = entrada.indexOf(CLAVE_NOMBRE, posDirector);
            if (posNombre == -1) {
                posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
                continue;
            }

            int inicioNombre = posNombre + CLAVE_NOMBRE.length();
            int finNombre = entrada.indexOf("\"", inicioNombre);
            if (finNombre == -1) {
                posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
                continue;
            }

            String nombreDirector = entrada.substring(inicioNombre, finNombre);

            try {
                 Director director = new Director(nombreDirector, idDirector);
                 directores.insert(idDirector, director);
                 director.getListaPeliculas().add(idPelicula);
            } catch (ElementAlreadyExist ignored) {
                Director director = directores.get(idDirector);
                if (director != null) { //Siempre se deberia cumplir esta condicion
                    director.getListaPeliculas().add(idPelicula);
                }
            }

            posicionInicial = posDirector + TRABAJO_DIRECTOR.length();
        }
    }

    public MyHash<Integer, Director> getDirectores() {
        return directores;
    }
}
