package um.edu.uy.Exceptions;

public class ElementAlreadyExist extends RuntimeException {
    public ElementAlreadyExist(String message) {
        super(message);
    }
}
