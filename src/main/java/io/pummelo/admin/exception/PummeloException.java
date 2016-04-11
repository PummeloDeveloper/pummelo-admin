package io.pummelo.admin.exception;

/**
 * Created by PummeloDeveloper on 16/4/10.
 */
public class PummeloException extends RuntimeException {

    public PummeloException(String message) {
        super(message);
    }

    public PummeloException(String message, Throwable cause) {
        super(message, cause);
    }
}
