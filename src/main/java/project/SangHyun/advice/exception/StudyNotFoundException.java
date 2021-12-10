package project.SangHyun.advice.exception;

public class StudyNotFoundException extends RuntimeException{
    public StudyNotFoundException() {
    }

    public StudyNotFoundException(String message) {
        super(message);
    }

    public StudyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
