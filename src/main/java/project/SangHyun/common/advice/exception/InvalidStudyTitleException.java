package project.SangHyun.common.advice.exception;

public class InvalidStudyTitleException extends RuntimeException {
    public InvalidStudyTitleException() {
    }

    public InvalidStudyTitleException(String message) {
        super(message);
    }

    public InvalidStudyTitleException(String message, Throwable cause) {
        super(message, cause);
    }
}
