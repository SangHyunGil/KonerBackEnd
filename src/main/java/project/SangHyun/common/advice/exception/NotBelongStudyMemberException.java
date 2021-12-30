package project.SangHyun.common.advice.exception;

public class NotBelongStudyMemberException extends RuntimeException{
    public NotBelongStudyMemberException() {
    }

    public NotBelongStudyMemberException(String message) {
        super(message);
    }

    public NotBelongStudyMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
