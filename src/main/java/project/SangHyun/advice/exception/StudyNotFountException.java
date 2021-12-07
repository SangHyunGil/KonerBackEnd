package project.SangHyun.advice.exception;

public class StudyNotFountException extends RuntimeException{
    public StudyNotFountException() {
    }

    public StudyNotFountException(String message) {
        super(message);
    }

    public StudyNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
