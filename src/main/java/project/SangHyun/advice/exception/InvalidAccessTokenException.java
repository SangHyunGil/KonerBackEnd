package project.SangHyun.advice.exception;

public class InvalidAccessTokenException extends RuntimeException {
    public InvalidAccessTokenException() {
    }

    public InvalidAccessTokenException(String message) {
        super(message);
    }

    public InvalidAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
