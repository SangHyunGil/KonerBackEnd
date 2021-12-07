package project.SangHyun.advice.exception;

public class StudyBoardNotFoundException extends RuntimeException{
    public StudyBoardNotFoundException() {
    }

    public StudyBoardNotFoundException(String message) {
        super(message);
    }

    public StudyBoardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
