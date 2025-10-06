package app.exeptions;


public class EmailAlreadyExistInExceptionDB extends RuntimeException {
    public EmailAlreadyExistInExceptionDB(String message) {
        super(message);
    }

    public EmailAlreadyExistInExceptionDB() {}
}
