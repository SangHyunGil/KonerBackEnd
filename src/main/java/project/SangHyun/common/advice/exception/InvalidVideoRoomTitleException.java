package project.SangHyun.common.advice.exception;

public class InvalidVideoRoomTitleException extends RuntimeException {
    public InvalidVideoRoomTitleException() {
    }

    public InvalidVideoRoomTitleException(String message) {
        super(message);
    }

    public InvalidVideoRoomTitleException(String message, Throwable cause) {
        super(message, cause);
    }
}
