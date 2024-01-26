package org.example.cookieretceptg27.common.exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(String message) {
        super(message);
    }

    public EmailAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
