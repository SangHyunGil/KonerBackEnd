package project.SangHyun.advice.exception;

public class BoardNotFountException extends RuntimeException{
    public BoardNotFountException() {
    }

    public BoardNotFountException(String message) {
        super(message);
    }

    public BoardNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
