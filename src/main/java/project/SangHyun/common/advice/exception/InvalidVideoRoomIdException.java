package project.SangHyun.common.advice.exception;

public class InvalidVideoRoomIdException extends RuntimeException {
    public InvalidVideoRoomIdException() {
    }

    public InvalidVideoRoomIdException(String message) {
        super(message);
    }

    public InvalidVideoRoomIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
