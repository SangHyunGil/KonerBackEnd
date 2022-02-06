package project.SangHyun.common.advice.exception;

public class InvalidHeadCountException extends RuntimeException {
    public InvalidHeadCountException() {
    }

    public InvalidHeadCountException(String message) {
        super(message);
    }

    public InvalidHeadCountException(String message, Throwable cause) {
        super(message, cause);
    }
}
