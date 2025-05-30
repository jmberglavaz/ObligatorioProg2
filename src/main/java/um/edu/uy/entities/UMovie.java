package um.edu.uy.entities;

import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.CargaDatos.CargaDePeliculas;
import um.edu.uy.TADs.Implementations.MyLinkedListImpl;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.TADs.Interfaces.MyList;

import java.io.IOException;

public class UMovie {
    private MyHash<Pelicula> catalogoDePeliculas;
    private MyList<Genero> listaDeGeneros;
    private MyList<Idioma> listaDeIdioma;
    private MyList<Director> lsitaDeDirectores;
    private CargaDePeliculas carga = new CargaDePeliculas();

    public UMovie() {
        try {
            this.catalogoDePeliculas = carga.lecturaDePeliculas();
        } catch (IOException | CsvValidationException ignored) {
            System.out.println("Hubo un error al cargar las peliculas");
        }

        this.listaDeGeneros =new MyLinkedListImpl<>();
        this.listaDeIdioma = new MyLinkedListImpl<>();
        this.lsitaDeDirectores = new MyLinkedListImpl<>();
    }

    public int cantPeliculas(){
        return this.catalogoDePeliculas.size();
    }

    public MyHash<Pelicula> getCatalogoDePeliculas() {
        return catalogoDePeliculas;
    }

    public void setCatalogoDePeliculas(MyHash<Pelicula> catalogoDePeliculas) {
        this.catalogoDePeliculas = catalogoDePeliculas;
    }

    public MyList<Genero> getListaDeGeneros() {
        return listaDeGeneros;
    }

    public void setListaDeGeneros(MyList<Genero> listaDeGeneros) {
        this.listaDeGeneros = listaDeGeneros;
    }

    public MyList<Idioma> getListaDeIdioma() {
        return listaDeIdioma;
    }

    public void setListaDeIdioma(MyList<Idioma> listaDeIdioma) {
        this.listaDeIdioma = listaDeIdioma;
    }

    public MyList<Director> getLsitaDeDirectores() {
        return lsitaDeDirectores;
    }

    public void setLsitaDeDirectores(MyList<Director> lsitaDeDirectores) {
        this.lsitaDeDirectores = lsitaDeDirectores;
    }
}
