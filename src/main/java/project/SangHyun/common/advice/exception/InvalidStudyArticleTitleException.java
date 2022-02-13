package project.SangHyun.common.advice.exception;

public class InvalidStudyArticleTitleException extends RuntimeException {
    public InvalidStudyArticleTitleException() {
    }

    public InvalidStudyArticleTitleException(String message) {
        super(message);
    }

    public InvalidStudyArticleTitleException(String message, Throwable cause) {
        super(message, cause);
    }
}
