package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyArrayListImpl;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Idioma;
import um.edu.uy.entities.Pelicula;

public class CargaDePeliculas {
    private CSVReader reader;
    private MyHash<Integer, Pelicula> listaDePeliculas;
    private MyHash<Integer, Genero> listaDeGeneros;
    private MyHash<String, Idioma> listaDeIdiomas;
    private MyHash<Integer, Coleccion> listaDeColecciones;
    private final Pattern collectionPattern = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");
    private final Pattern generoPattern = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");

    public CargaDePeliculas(){
        try{
            InputStream rutaDeDatos = CargaDePeliculas.class.getResourceAsStream("/movies_metadata.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(rutaDeDatos));
            this.reader = new CSVReader(bufferedReader);
            reader.readNext();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        this.listaDePeliculas = new MyHashImplCloseLineal<>(59999);
        this.listaDeGeneros = new MyHashImplCloseLineal<>(53);
        this.listaDeIdiomas = new MyHashImplCloseLineal<>(97);
        this.listaDeColecciones = new MyHashImplCloseLineal<>(1709);

        try{
            cargaDeDatos();
        } catch (IOException | CsvValidationException ignored) {
            System.out.println("Error al cargar los datos de las peliculas");
        }
    }

    public void cargaDeDatos() throws IOException, CsvValidationException {
        long inicio = System.currentTimeMillis();
        int peliculasProcesadas = 0;
        int peliculasValidas = 0;

        System.out.println("Iniciando carga de peliculas...");
        String[] dataLine;
        while ((dataLine = reader.readNext()) != null) {
            peliculasProcesadas++;

            // Progreso cada 5000 películas
//            if (peliculasProcesadas % 5000 == 0) {
//                System.out.println("\n####### Se han ingresado: " + peliculasProcesadas + " peliculas #######\n");
//            }

            int numericId;
            try {
                numericId = Integer.parseInt(dataLine[5]);
                peliculasValidas++;
            } catch (NumberFormatException e) {
                continue;
            }

            long ganancias = 0;
            try {
                ganancias = Long.parseLong(dataLine[13]);
            } catch (NumberFormatException ignored) {}

            Pelicula tempPelicula = new Pelicula(numericId, dataLine[8], dataLine[12], ganancias);
            try {
                listaDePeliculas.insert(numericId, tempPelicula);
            } catch (ElementAlreadyExist ignored) {continue;}

            // Procesar géneros
            MyList<Genero> generos = verifiyGeneros(dataLine[3]);
            for (Genero tempGenero : generos) {
                try{
                    this.listaDeGeneros.insert(tempGenero.getId(),tempGenero);
                    tempGenero.agregarPelicula(numericId);
                } catch (ElementAlreadyExist ignored) {
                    tempGenero = listaDeGeneros.get(tempGenero.getId());
                    tempGenero.agregarPelicula(numericId);
                }
            }

            // Procesar idiomas
            String idioma = dataLine[7];
            if (idioma != null && !idioma.trim().isEmpty()) {
                Idioma tempIdioma = new Idioma(idioma);
                try {
                    listaDeIdiomas.insert(idioma, tempIdioma);
                    tempIdioma.agregarPelicula(numericId);
                } catch (ElementAlreadyExist ignored) {
                    tempIdioma = listaDeIdiomas.get(idioma);
                    tempIdioma.agregarPelicula(numericId);
                }
            }

            // Procesar colecciones
            Coleccion tempColeccion = verififyColeccion(dataLine[1]);
            if (tempColeccion != null){
                try {
                    listaDeColecciones.insert(tempColeccion.getId(), tempColeccion);
                    tempColeccion.agregarPelicula(numericId);
                } catch (ElementAlreadyExist ignored) {
                    tempColeccion = listaDeColecciones.get(tempColeccion.getId());
                    tempColeccion.agregarPelicula(numericId);
                }
            }
        }

        long fin = System.currentTimeMillis();
        estadisticasDeCarga(inicio, fin, peliculasProcesadas, peliculasValidas);
//        estadisitcasDeTablasHash();
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

    public MyHash<Integer, Coleccion> getListaDeColecciones() {
        return listaDeColecciones;
    }

    private MyList<Genero> verifiyGeneros(String input){
        MyList<Genero> generos = new MyLinkedListImpl<>();
        if (input == null || input.trim().isEmpty()) {
            return generos;
        }

        Matcher matcher = generoPattern.matcher(input);
        while (matcher.find()) {
            try {
                int id = Integer.parseInt(matcher.group(1));
                String nombre = matcher.group(2);
                generos.add(new Genero(id, nombre));
            } catch (NumberFormatException ignored) {}
        }
        return generos;
    }

    private Coleccion verififyColeccion(String input){
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        Matcher matcher = collectionPattern.matcher(input);
        if (matcher.find()){
            try {
                int id = Integer.parseInt(matcher.group(1));
                String nombre = matcher.group(2);
                return new Coleccion(id, nombre);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }


    // Devolper Method
    private void estadisticasDeCarga(long inicio, long fin, int peliculasProcesadas, int peliculasValidas){
        System.out.println("\n=== ESTADISTICAS DE CARGA DE PELICULAS ===");
        System.out.println("Tiempo total de carga: " + (fin - inicio) + " ms");
        System.out.println("Peliculas procesadas: " + peliculasProcesadas);
        System.out.println("Peliculas validas cargadas: " + peliculasValidas);
        System.out.println("Generos unicos: " + listaDeGeneros.size());
        System.out.println("Idiomas unicos: " + listaDeIdiomas.size());
        System.out.println("Colecciones unicas: " + listaDeColecciones.size());
    }

    // Developer method
    private void estadisitcasDeTablasHash(){
        System.out.println("\n=== ESTADISTICAS DE HASH TABLES ===");
        System.out.println("Peliculas:");
        ((MyHashImplCloseLineal<Integer, Pelicula>) listaDePeliculas).printStats();

        System.out.println("\nGeneros:");
        ((MyHashImplCloseLineal<Integer, Genero>) listaDeGeneros).printStats();

        System.out.println("\nIdiomas:");
        ((MyHashImplCloseLineal<String, Idioma>) listaDeIdiomas).printStats();

        System.out.println("\nColecciones:");
        ((MyHashImplCloseLineal<Integer, Coleccion>) listaDeColecciones).printStats();
    }
}