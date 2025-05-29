package um.edu.uy.entities;

import um.edu.uy.TADs.Implementations.MyHashImpl;
import um.edu.uy.TADs.Implementations.MyLinkedListImpl;
import um.edu.uy.TADs.Interfaces.MyHash;
import um.edu.uy.TADs.Interfaces.MyList;

public class UMovie {
    private MyHash<Pelicula> catalogoDePeliculas;
    private MyList<Genero> listaDeGeneros;
    private MyList<Idioma> listaDeIdioma;
    private MyList<Director> lsitaDeDirectores;

    public UMovie() {
        this.catalogoDePeliculas = new MyHashImpl<>();
        this.listaDeGeneros =new MyLinkedListImpl<>();
        this.listaDeIdioma = new MyLinkedListImpl<>();
        this.lsitaDeDirectores = new MyLinkedListImpl<>();
    }
}
