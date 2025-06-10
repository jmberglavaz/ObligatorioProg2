package um.edu.uy.entities;

import um.edu.uy.Sistema.CargaDeEvaluaciones;
import um.edu.uy.Sistema.CargaDePeliculas;
import um.edu.uy.Sistema.CargaDeStaff;
import um.edu.uy.Sistema.Consulta1.PrimeraConsulta;
import um.edu.uy.TADs.Hash.MyHash;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UMovie {
    private MyHash<Integer, Pelicula> peliculas;
    private MyHash<Integer, Genero> generos;
    private MyHash<String, Idioma> idiomas;
    private MyHash<Integer,Director> directores;
    private MyHash<Integer, Coleccion> colecciones;
    private boolean datosCargados = false;

    public UMovie() {
    }

    public int cantPeliculas() {
        return peliculas.size();
    }

    public int cantidadGeneros() {
        return generos.size();
    }

    public void mostrarPeliculasPorGenero(int idGenero){
        generos.get(idGenero).printPeliculas();
    }

    public void mostrarPeliculasPorIdioma(String acronimoIdioma){
        idiomas.get(acronimoIdioma).printPeliculas();
    }

    public MyHash<Integer, Pelicula> getCatalogoDePeliculas() {
        return peliculas;
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
            }
            case 71 -> {
                if(!datosCargados){
                    cargarDatosDeveloper();
                    datosCargados = true;
                } else {
                    System.out.println("Los datos ya estan cargados.");
                }
            }
            default -> System.out.println(errorPrincipal);
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
            case 1 -> PrimeraConsulta.realizarConsulta(peliculas, idiomas);
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
        CargaDePeliculas cargaPeliculas = new CargaDePeliculas(false);
        CargaDeEvaluaciones cargaEvaluaciones = new CargaDeEvaluaciones(false);
        CargaDeStaff cargaDeStaff = new CargaDeStaff(false);

        this.peliculas = cargaPeliculas.getPeliculas();
        this.generos = cargaPeliculas.getGeneros();
        this.idiomas = cargaPeliculas.getIdiomas();
        this.colecciones = cargaPeliculas.getColecciones();
        System.out.println("Carga de peliculas completada");

        try {
            cargaEvaluaciones.cargarDatos(peliculas);
        } catch (Exception ignored) {}
        System.out.println("Carga de evaluaciones completada.");

        try {
            cargaDeStaff.cargaDeDatos(peliculas);
            this.directores = cargaDeStaff.getDirectores();
        } catch (Exception ignored) {}
        System.out.println("Carga de creditos completada.");
    }

    private void cargarDatosDeveloper(){
        long inicio = System.currentTimeMillis();
        CargaDePeliculas cargaPeliculas = new CargaDePeliculas(true);
        CargaDeEvaluaciones cargaEvaluaciones = new CargaDeEvaluaciones(true);
        CargaDeStaff cargaDeStaff = new CargaDeStaff(true);

        this.peliculas = cargaPeliculas.getPeliculas();
        this.generos = cargaPeliculas.getGeneros();
        this.idiomas = cargaPeliculas.getIdiomas();
        this.colecciones = cargaPeliculas.getColecciones();
        System.out.println("Carga de peliculas completada");

        try {
            cargaEvaluaciones.cargarDatos(peliculas);
        } catch (Exception ignored) {}
        System.out.println("Carga de evaluaciones completada.");

        try {
            cargaDeStaff.cargaDeDatos(peliculas);
            this.directores = cargaDeStaff.getDirectores();
        } catch (Exception ignored) {}
        System.out.println("Carga de creditos completada.");
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de carga: " + (fin-inicio) + "ms");
    }
}
