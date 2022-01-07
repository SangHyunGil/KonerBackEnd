package project.SangHyun.common.advice.exception;

public class MemberSameNickNameException extends RuntimeException {
    public MemberSameNickNameException() {
    }

    public MemberSameNickNameException(String message) {
        super(message);
    }

    public MemberSameNickNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
