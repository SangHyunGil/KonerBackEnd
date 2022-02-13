package project.SangHyun.common.advice.exception;

public class InvalidStudyDescriptionException extends RuntimeException {
    public InvalidStudyDescriptionException() {
    }

    public InvalidStudyDescriptionException(String message) {
        super(message);
    }

    public InvalidStudyDescriptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
