package um.edu.uy.Exceptions;

public class ParentNotFoundException extends RuntimeException {
    public ParentNotFoundException() {
        super("Parent key not found");
    }
}
