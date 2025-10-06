package app.exeptions;

public class AuthorException extends RuntimeException {
    public AuthorException(String message) {
        super(message);
    }

    public AuthorException() {};
}
