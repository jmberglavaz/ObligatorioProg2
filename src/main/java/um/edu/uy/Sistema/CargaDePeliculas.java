package um.edu.uy.Sistema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import um.edu.uy.Exceptions.ElementAlreadyExist;
import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.Hash.MyHashImplCloseLineal;
import um.edu.uy.TADs.Hash.MyHash;
import um.edu.uy.TADs.List.MyList;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Idioma;
import um.edu.uy.entities.Pelicula;

public class CargaDePeliculas {
    private CSVReader lectorCSV;
    private MyHash<Integer, Pelicula> peliculas;
    private MyHash<Integer, Genero> generos;
    private MyHash<String, Idioma> idiomas;
    private MyHash<Integer, Coleccion> colecciones;
    private final Pattern patternColeccion = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");
    private final Pattern patternGenero = Pattern.compile("'id':\\s*(\\d+),\\s*'name':\\s*'([^']+)'");

    public CargaDePeliculas(){
        try{
            InputStream direccionArchivoDatos = CargaDePeliculas.class.getResourceAsStream("/movies_metadata.csv");
            this.lectorCSV = new CSVReader(new InputStreamReader(direccionArchivoDatos));
            lectorCSV.readNext(); // Se lee la primera línea (cabecera) y se descarta
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); //No deberia de ocurrir, pero si ocurre, se imprime el error
        }

        this.peliculas = new MyHashImplCloseLineal<>(59999);
        this.generos = new MyHashImplCloseLineal<>(53);
        this.idiomas = new MyHashImplCloseLineal<>(97);
        this.colecciones = new MyHashImplCloseLineal<>(1709);

        try{
            cargarDatos();
        } catch (IOException | CsvValidationException ignored) {
            System.out.println("Error al cargar los datos de las peliculas"); // No deberia de ocurrir, pero si ocurre, se imprime el error
        }
    }

    public void cargarDatos() throws IOException, CsvValidationException {
        long inicio = System.currentTimeMillis();
        int peliculasProcesadas = 0;
        int peliculasValidas = 0;

        System.out.println("Iniciando carga de peliculas...");
        String[] dataLine;
        while ((dataLine = lectorCSV.readNext()) != null) {
            peliculasProcesadas++;

            int idPelicula;
            try {
                idPelicula = Integer.parseInt(dataLine[5]);
                peliculasValidas++;
            } catch (NumberFormatException e) {
                continue;
            }

            long ganancias = 0;
            try {
                ganancias = Long.parseLong(dataLine[13]);
            } catch (NumberFormatException ignored) {}

            Pelicula pelicula = new Pelicula(idPelicula, dataLine[8], dataLine[12], ganancias);
            try {
                peliculas.insert(idPelicula, pelicula);
            } catch (ElementAlreadyExist ignored) {
                continue;
            }

            MyList<Genero> listaGeneros = searchGeneros(dataLine[3]);
            for (Genero genero : listaGeneros) {
                try{
                    this.generos.insert(genero.getId(), genero);
                    genero.agregarPelicula(idPelicula);
                } catch (ElementAlreadyExist ignored) {
                    genero = generos.get(genero.getId());
                    genero.agregarPelicula(idPelicula);
                }
            }

            String acronimoIdioma = dataLine[7];
            if (acronimoIdioma != null && !acronimoIdioma.trim().isEmpty()) {
                Idioma idioma = new Idioma(acronimoIdioma);
                try {
                    idiomas.insert(acronimoIdioma, idioma);
                    idioma.agregarPelicula(idPelicula);
                } catch (ElementAlreadyExist ignored) {
                    idioma = idiomas.get(acronimoIdioma);
                    idioma.agregarPelicula(idPelicula);
                }
            }

            Coleccion coleccion = searchColecciones(dataLine[1]);
            if (coleccion != null){
                try {
                    colecciones.insert(coleccion.getId(), coleccion);
                    coleccion.agregarPelicula(idPelicula);
                } catch (ElementAlreadyExist ignored) {
                    coleccion = colecciones.get(coleccion.getId());
                    coleccion.agregarPelicula(idPelicula);
                }
            }
        }

        long fin = System.currentTimeMillis();
        //mostrarEstadisticasCarga(inicio, fin, peliculasProcesadas, peliculasValidas);
    }

    public MyHash<Integer, Pelicula> getPeliculas() {
        return peliculas;
    }

    public MyHash<Integer, Genero> getGeneros() {
        return generos;
    }

    public MyHash<String, Idioma> getIdiomas() {
        return idiomas;
    }

    public MyHash<Integer, Coleccion> getColecciones() {
        return colecciones;
    }

    private MyList<Genero> searchGeneros(String entrada){
        MyList<Genero> listaGeneros = new MyLinkedListImpl<>();
        if (entrada == null || entrada.trim().isEmpty()) {
            return listaGeneros;
        }

        Matcher matcher = patternGenero.matcher(entrada);
        while (matcher.find()) {
            try {
                int id = Integer.parseInt(matcher.group(1));
                String nombre = matcher.group(2);
                listaGeneros.add(new Genero(id, nombre));
            } catch (NumberFormatException ignored) {}
        }
        return listaGeneros;
    }

    private Coleccion searchColecciones(String entrada){
        if (entrada == null || entrada.trim().isEmpty()) {
            return null;
        }

        Matcher matcher = patternColeccion.matcher(entrada);
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

    private void mostrarEstadisticasCarga(long inicio, long fin, int peliculasProcesadas, int peliculasValidas){
        System.out.println("\n=== ESTADISTICAS DE CARGA DE PELICULAS ===");
        System.out.println("Tiempo total de carga: " + (fin - inicio) + " ms");
        System.out.println("Peliculas procesadas: " + peliculasProcesadas);
        System.out.println("Peliculas validas cargadas: " + peliculasValidas);
        System.out.println("Generos unicos: " + generos.size());
        System.out.println("Idiomas unicos: " + idiomas.size());
        System.out.println("Colecciones unicas: " + colecciones.size());
    }
}
