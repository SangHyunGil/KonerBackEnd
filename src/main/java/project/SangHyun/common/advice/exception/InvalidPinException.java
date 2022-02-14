package project.SangHyun.common.advice.exception;

public class InvalidPinException extends RuntimeException {
    public InvalidPinException() {
    }

    public InvalidPinException(String message) {
        super(message);
    }

    public InvalidPinException(String message, Throwable cause) {
        super(message, cause);
    }
}
