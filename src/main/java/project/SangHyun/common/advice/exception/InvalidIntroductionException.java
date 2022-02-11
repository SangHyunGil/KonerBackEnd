package project.SangHyun.common.advice.exception;

public class InvalidIntroductionException extends RuntimeException {
    public InvalidIntroductionException() {
    }

    public InvalidIntroductionException(String message) {
        super(message);
    }

    public InvalidIntroductionException(String message, Throwable cause) {
        super(message, cause);
    }
}
