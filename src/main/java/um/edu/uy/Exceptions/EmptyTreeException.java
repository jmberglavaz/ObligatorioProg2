package um.edu.uy.Exceptions;

public class EmptyTreeException extends RuntimeException {
    public EmptyTreeException() {
        super("The tree is empty");
    }
}
