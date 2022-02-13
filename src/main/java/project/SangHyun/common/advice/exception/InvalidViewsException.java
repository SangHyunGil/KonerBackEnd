package project.SangHyun.common.advice.exception;

public class InvalidViewsException extends RuntimeException {
    public InvalidViewsException() {
    }

    public InvalidViewsException(String message) {
        super(message);
    }

    public InvalidViewsException(String message, Throwable cause) {
        super(message, cause);
    }
}
