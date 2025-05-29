package um.edu.uy.Exceptions;

public class ListOutOfIndex extends RuntimeException {
    public ListOutOfIndex(String message) {
        super(message);
    }
}
