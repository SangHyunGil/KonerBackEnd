package project.SangHyun.advice.exception;

public class StudyCommentNotFoundException extends RuntimeException{
    public StudyCommentNotFoundException() {
    }

    public StudyCommentNotFoundException(String message) {
        super(message);
    }

    public StudyCommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
