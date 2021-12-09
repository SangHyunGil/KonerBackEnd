package project.SangHyun.advice.exception;

public class ExceedMaximumStudyMember extends RuntimeException {
    public ExceedMaximumStudyMember() {
    }

    public ExceedMaximumStudyMember(String message) {
        super(message);
    }

    public ExceedMaximumStudyMember(String message, Throwable cause) {
        super(message, cause);
    }
}
