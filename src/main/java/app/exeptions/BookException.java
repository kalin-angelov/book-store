package app.exeptions;

public class BookException extends RuntimeException {
    public BookException(String message) {
        super(message);
    }

    public BookException() {};
}
