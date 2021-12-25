package project.SangHyun.advice.exception;

public class StudyJoinNotFoundException extends RuntimeException{
    public StudyJoinNotFoundException() {
    }

    public StudyJoinNotFoundException(String message) {
        super(message);
    }

    public StudyJoinNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
