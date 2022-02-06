package project.SangHyun.common.advice.exception;

public class InvalidStudyArticleContentException extends RuntimeException {
    public InvalidStudyArticleContentException() {
    }

    public InvalidStudyArticleContentException(String message) {
        super(message);
    }

    public InvalidStudyArticleContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
