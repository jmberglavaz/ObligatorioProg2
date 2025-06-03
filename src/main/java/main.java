import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.Sistema.CargaDePeliculas;
import um.edu.uy.entities.UMovie;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws CsvValidationException, IOException {
        UMovie plataforma = new UMovie();
        plataforma.iniciar();
    }
}
