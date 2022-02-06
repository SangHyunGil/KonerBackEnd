package project.SangHyun.common.advice.exception;

public class InvalidStudyIntroductionException extends RuntimeException {
    public InvalidStudyIntroductionException() {
    }

    public InvalidStudyIntroductionException(String message) {
        super(message);
    }

    public InvalidStudyIntroductionException(String message, Throwable cause) {
        super(message, cause);
    }
}
