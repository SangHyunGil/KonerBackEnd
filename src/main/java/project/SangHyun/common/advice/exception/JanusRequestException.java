package project.SangHyun.common.advice.exception;

public class JanusRequestException extends RuntimeException {
    public JanusRequestException() {
    }

    public JanusRequestException(String message) {
        super(message);
    }

    public JanusRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
