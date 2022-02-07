package project.SangHyun.common.advice.exception;

public class InvalidStudyBoardTitleException extends RuntimeException {
    public InvalidStudyBoardTitleException() {
    }

    public InvalidStudyBoardTitleException(String message) {
        super(message);
    }

    public InvalidStudyBoardTitleException(String message, Throwable cause) {
        super(message, cause);
    }
}
