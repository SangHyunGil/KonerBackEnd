package project.SangHyun.common.advice.exception;

public class StudyArticleNotWriterException extends RuntimeException {
    public StudyArticleNotWriterException() {
    }

    public StudyArticleNotWriterException(String message) {
        super(message);
    }

    public StudyArticleNotWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
