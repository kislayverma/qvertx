package io.qvertx.service.exception;

/**
 *
 * @author Kislay Verma
 */
public class BadServerException extends RuntimeException {
    public BadServerException() {
        super("Internal Server Error");
    }

    public BadServerException(String message) {
        super(message);
    }

    public BadServerException(Throwable cause) {
        super(cause);
    }

    public BadServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
