package project.SangHyun.common.advice.exception;

public class InvalidRelatedURLException extends RuntimeException {
    public InvalidRelatedURLException() {
    }

    public InvalidRelatedURLException(String message) {
        super(message);
    }

    public InvalidRelatedURLException(String message, Throwable cause) {
        super(message, cause);
    }
}
