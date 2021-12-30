package project.SangHyun.common.advice.exception;

public class InvalidAuthCodeException extends RuntimeException {
    public InvalidAuthCodeException() {
    }

    public InvalidAuthCodeException(String message) {
        super(message);
    }

    public InvalidAuthCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
