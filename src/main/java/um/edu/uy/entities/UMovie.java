package um.edu.uy.entities;

import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.Sistema.CargaDeEvaluaciones;
import um.edu.uy.Sistema.CargaDePeliculas;
import um.edu.uy.TADs.Implementations.MyLinkedListImpl;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.TADs.Interfaces.MyList;

import java.io.IOException;

public class UMovie {
    private MyHash<Integer, Pelicula> catalogoDePeliculas;
    private MyHash<Integer, Genero> listaDeGeneros;
    private MyHash<String, Idioma> listaDeIdioma;
    private MyList<Director> listaDeDirectores;
    private MyHash<Integer, Coleccion> listaDeColecciones;

    public UMovie() {
        CargaDePeliculas carga = new CargaDePeliculas();
        CargaDeEvaluaciones cargaEvaluaciones = new CargaDeEvaluaciones();
        this.catalogoDePeliculas = carga.getListaDePeliculas();
        this.listaDeGeneros = carga.getListaDeGeneros();
        this.listaDeIdioma = carga.getListaDeIdiomas();
        this.listaDeColecciones = carga.getListaDeColecciones();
        try {
            cargaEvaluaciones.cargaDeDatos(catalogoDePeliculas);
        } catch (IOException | CsvValidationException ignored) {}
        this.listaDeDirectores = new MyLinkedListImpl<>();
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
}
