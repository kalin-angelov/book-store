package app.exeptions;

public class PasswordException extends RuntimeException {
    public PasswordException(String message) {
        super(message);
    }

    public PasswordException() {};
}
