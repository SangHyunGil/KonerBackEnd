package project.SangHyun.common.advice.exception;

public class InvalidMessageContentException extends RuntimeException {
    public InvalidMessageContentException() {
    }

    public InvalidMessageContentException(String message) {
        super(message);
    }

    public InvalidMessageContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
