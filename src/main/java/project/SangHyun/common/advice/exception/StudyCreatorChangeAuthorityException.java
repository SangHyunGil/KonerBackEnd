package project.SangHyun.common.advice.exception;

public class StudyCreatorChangeAuthorityException extends RuntimeException {
    public StudyCreatorChangeAuthorityException() {
    }

    public StudyCreatorChangeAuthorityException(String message) {
        super(message);
    }

    public StudyCreatorChangeAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }
}
