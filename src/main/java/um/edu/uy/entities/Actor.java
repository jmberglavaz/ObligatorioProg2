package um.edu.uy.entities;

import um.edu.uy.TADs.List.Linked.MyLinkedListImpl;
import um.edu.uy.TADs.List.MyList;

public class Actor {
    private String nombre;
    private final MyList<Integer> peluculasId;

    public Actor(String nombre) {
        this.nombre = nombre;
        this.peluculasId = new MyLinkedListImpl<>();
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public MyList<Integer> getPeluculasId() {
        return peluculasId;
    }
    public void agregarPelicula(int id) {
        peluculasId.add(id);
    }
}
