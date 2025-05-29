package um.edu.uy.Exceptions;

public class FullNodoException extends RuntimeException {
    public FullNodoException() {
        super("Nodo already have left and right child");
    }
}
