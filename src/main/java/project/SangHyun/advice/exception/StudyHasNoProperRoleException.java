package project.SangHyun.advice.exception;

public class StudyHasNoProperRoleException extends RuntimeException {
    public StudyHasNoProperRoleException() {
    }

    public StudyHasNoProperRoleException(String message) {
        super(message);
    }

    public StudyHasNoProperRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
