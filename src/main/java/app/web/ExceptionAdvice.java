package app.web;

import app.exeptions.*;
import app.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(EmailAlreadyExistInExceptionDB.class)
    public ResponseEntity<ErrorResponse> handleEmailExistException(EmailAlreadyExistInExceptionDB exception) {

        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);

    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ErrorResponse> handlePasswordException(PasswordException exception) {

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<ErrorResponse> handleBookException(BookException exception) {

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<ErrorResponse> handleAuthorException(AuthorException exception) {

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException exception) {

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
}
