package project.SangHyun.advice.exception;

public class StudyArticleNotFoundException extends RuntimeException{
    public StudyArticleNotFoundException() {
    }

    public StudyArticleNotFoundException(String message) {
        super(message);
    }

    public StudyArticleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
