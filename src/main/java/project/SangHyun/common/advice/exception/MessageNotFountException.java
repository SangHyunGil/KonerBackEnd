package project.SangHyun.common.advice.exception;

public class MessageNotFountException extends RuntimeException{
    public MessageNotFountException() {
    }

    public MessageNotFountException(String message) {
        super(message);
    }

    public MessageNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
