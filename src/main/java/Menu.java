import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    String menuPrincipal = """
            ╔═══════════════════════════════════╗
            ║   Selecione la opcion que desse:  ║
            ╠═══════════════════════════════════╣
            ║  1. Carga de Datos                ║
            ║  2. Ejeutar Consultas.            ║
            ║  3. Salir                         ║
            ╚═══════════════════════════════════╝
            """;
    String menuConsultas = """
                ╔════════════════════════════════════════════════════════════════════════════════════════════╗
                ║                                      Menu de opciones                                      ║
                ╠════════════════════════════════════════════════════════════════════════════════════════════╣
                ║ 1. Top 5 de las películas que más calificaciones por idioma.                               ║
                ║ 2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.  ║
                ║ 3. Top 5 de las colecciones que más ingresos generaron.                                    ║
                ║ 4. Top 10 de los directores que mejor calificación tienen.                                 ║
                ║ 5. Actor con más calificaciones recibidas en cada mes del año.                             ║
                ║ 6. Usuarios con más calificaciones por género                                              ║
                ║ 7. Salir                                                                                   ║
                ╚════════════════════════════════════════════════════════════════════════════════════════════╝
                Elija una opcion(1-7): \s""";

    String errorPrincipal = """
                ╔════════════════════════════════════════════════════════╗
                ║                         ERROR:                         ║
                ║    Opcion no valida, ingrese un numero entre 1 y 3.    ║
                ╚════════════════════════════════════════════════════════╝
                """;

    String errorConsultas = """
                ╔════════════════════════════════════════════════════════╗
                ║                         ERROR:                         ║
                ║    Opcion no valida, ingrese un numero entre 1 y 7.    ║
                ╚════════════════════════════════════════════════════════╝
                """;

    public void iniciarMenuPrincipal() {
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

    //Falta cambiar los prints por las funciones correspondientes.
    private boolean verificarOpcionPrincipal(int opcion){
        switch (opcion) {
            case 1 -> System.out.println("(Falta cargar datos) \n" + "Carga de datos exitosa, tiempo de ejecución de la carga");
            case 2 -> iniciarMenuConsultas();
            case 3 -> {
                System.out.println("Saliendo del sistema...");
                return false;
            }default -> System.out.println(errorPrincipal);
        }
        return true;
    }

    private boolean verificarOpcionConsultas(int opcion){
        switch (opcion) {
            case 1 -> System.out.println("Funcion de peliculas mas evaluadas por idionma (Pendiente)");
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
}
