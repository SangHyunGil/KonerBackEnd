package project.SangHyun.common.advice.exception;

public class InvalidStudyCommentContentException extends RuntimeException {
    public InvalidStudyCommentContentException() {
    }

    public InvalidStudyCommentContentException(String message) {
        super(message);
    }

    public InvalidStudyCommentContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
