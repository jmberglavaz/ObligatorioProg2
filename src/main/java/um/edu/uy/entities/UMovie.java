package um.edu.uy.entities;

import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.Sistema.CargaDeEvaluaciones;
import um.edu.uy.Sistema.CargaDePeliculas;
import um.edu.uy.TADs.Implementations.MyLinkedListImpl;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.TADs.Interfaces.MyList;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UMovie {
    private MyHash<Integer, Pelicula> catalogoDePeliculas;
    private MyHash<Integer, Genero> listaDeGeneros;
    private MyHash<String, Idioma> listaDeIdioma;
    private MyHash<Integer,Director> listaDeDirectores;
    private MyHash<Integer, Coleccion> listaDeColecciones;
    private boolean datosCargados = false;

    public UMovie() {
    }

    public int cantPeliculas() {
        return catalogoDePeliculas.size();
    }

    public int cantGeneros() {
        return listaDeGeneros.size();
    }

    public void pruebaPeliculasGenero(int idGenero){
        listaDeGeneros.get(idGenero).printPeliculas();
    }

    public void pruebaPeliculasIdioma(String AcronimoIdioma){
        listaDeIdioma.get(AcronimoIdioma).printPeliculas();
    }

    String menuPrincipal = """
            ╔═══════════════════════════════════╗
            ║       Seleccione una opción:      ║
            ╠═══════════════════════════════════╣
            ║  1. Carga de Datos                ║
            ║  2. Ejecutar Consultas            ║
            ║  3. Salir                         ║
            ╚═══════════════════════════════════╝
            """;
    String menuConsultas = """
                ╔════════════════════════════════════════════════════════════════════════════════════════════╗
                ║                                      Menú de opciones                                      ║
                ╠════════════════════════════════════════════════════════════════════════════════════════════╣
                ║ 1. Top 5 de las películas que más calificaciones por idioma                                ║
                ║ 2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios   ║
                ║ 3. Top 5 de las colecciones que más ingresos generaron                                     ║
                ║ 4. Top 10 de los directores que mejor calificación tienen                                  ║
                ║ 5. Actor con más calificaciones recibidas en cada mes del año                              ║
                ║ 6. Usuarios con más calificaciones por género                                              ║
                ║ 7. Salir                                                                                   ║
                ╚════════════════════════════════════════════════════════════════════════════════════════════╝
                Elija una opción(1-7): \s""";

    String errorPrincipal = """
                ╔════════════════════════════════════════════════════════╗
                ║                         ERROR:                         ║
                ║    Opción no válida, ingrese un número entre 1 y 3.    ║
                ╚════════════════════════════════════════════════════════╝
                """;

    String errorConsultas = """
                ╔════════════════════════════════════════════════════════╗
                ║                         ERROR:                         ║
                ║    Opción no válida, ingrese un número entre 1 y 7.    ║
                ╚════════════════════════════════════════════════════════╝
                """;

    public void iniciar() {
        boolean encendido = true;

        while (encendido) {
            System.out.println(menuPrincipal);
            Scanner scanner = new Scanner(System.in);
            try {
                int opcion = scanner.nextInt();
                encendido = verificarOpcionPrincipal(opcion);
            } catch (InputMismatchException e){
                System.out.println(errorPrincipal);
            }
        }
    }

    //Falta cambiar los prints por las funciones correspondientes.
    private boolean verificarOpcionPrincipal(int opcion){
        switch (opcion) {
            case 1 -> {
                if(!datosCargados){
                    cargarDatos();
                    datosCargados = true;
                } else {
                    System.out.println("Los datos ya estan cargados.");
                }
            }
            case 2 -> iniciarMenuConsultas();
            case 3 -> {
                System.out.println("Saliendo del sistema...");
                return false;
            }default -> System.out.println(errorPrincipal);
        }
        return true;
    }

    private void iniciarMenuConsultas(){
        boolean encendido = true;

        while (encendido) {
            System.out.println(menuConsultas);
            Scanner scanner = new Scanner(System.in);
            try {
                int opcion = scanner.nextInt();
                encendido = verificarOpcionConsultas(opcion);
            } catch (InputMismatchException e){
                System.out.println(errorConsultas);
            }
        }
    }

    private boolean verificarOpcionConsultas(int opcion){
        switch (opcion) {
            case 1 -> System.out.println("Funcion de peliculas mas evaluadas por idioma (Pendiente)");
            case 2 -> System.out.println("Funcion de peliculas mejor evaluadas (Pendiente)");
            case 3 -> System.out.println("Funcion de sagas con mayores ingresos (Pendiente)");
            case 4 -> System.out.println("Funcion de directores con mejores clasificaciones (Pendiente)");
            case 5 -> System.out.println("Funcion de actor mejor calificado por cada mes (Pendiente)");
            case 6 -> System.out.println("Funcion de Mayor evaluador por cada uno de los 10 mejores generos (Pendiente)");
            case 7 -> {
                System.out.println("Volviendo atras...");
                return false;
            }
            default -> System.out.println(errorConsultas);
        }
        return true;
    }

    private void cargarDatos(){
        CargaDePeliculas cargaPeliculas = new CargaDePeliculas();
        CargaDeEvaluaciones cargaEvaluaciones = new CargaDeEvaluaciones();

        this.catalogoDePeliculas = cargaPeliculas.getListaDePeliculas();
        this.listaDeGeneros = cargaPeliculas.getListaDeGeneros();
        this.listaDeIdioma = cargaPeliculas.getListaDeIdiomas();
        this.listaDeColecciones = cargaPeliculas.getListaDeColecciones();
        System.out.println("Carga de peliculas finalizada");

        try {
            cargaEvaluaciones.cargaDeDatos(catalogoDePeliculas);
        } catch (IOException | CsvValidationException ignored) {}
        System.out.println("Carga de evaluaciones completas.");
    }
}
