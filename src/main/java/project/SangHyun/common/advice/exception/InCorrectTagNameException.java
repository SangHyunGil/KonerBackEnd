package project.SangHyun.common.advice.exception;

public class InCorrectTagNameException extends RuntimeException {
    public InCorrectTagNameException() {
    }

    public InCorrectTagNameException(String message) {
        super(message);
    }

    public InCorrectTagNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
