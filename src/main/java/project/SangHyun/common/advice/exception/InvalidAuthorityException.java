package project.SangHyun.common.advice.exception;

public class InvalidAuthorityException extends RuntimeException {
    public InvalidAuthorityException() {
    }

    public InvalidAuthorityException(String message) {
        super(message);
    }

    public InvalidAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }
}
