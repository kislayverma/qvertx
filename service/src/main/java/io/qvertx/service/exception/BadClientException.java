package io.qvertx.service.exception;

/**
 *
 * @author Kislay Verma
 */
public class BadClientException extends RuntimeException {
    public BadClientException() {
        super("Invalid Request");
    }

    public BadClientException(String message) {
        super(message);
    }

    public BadClientException(Throwable cause) {
        super(cause);
    }

    public BadClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
