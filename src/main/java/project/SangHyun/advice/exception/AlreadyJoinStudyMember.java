package project.SangHyun.advice.exception;

public class AlreadyJoinStudyMember extends RuntimeException {
    public AlreadyJoinStudyMember() {
    }

    public AlreadyJoinStudyMember(String message) {
        super(message);
    }

    public AlreadyJoinStudyMember(String message, Throwable cause) {
        super(message, cause);
    }
}
