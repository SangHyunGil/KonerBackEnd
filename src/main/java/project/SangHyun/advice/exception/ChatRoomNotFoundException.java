package project.SangHyun.advice.exception;

public class ChatRoomNotFoundException extends RuntimeException{
    public ChatRoomNotFoundException() {
    }

    public ChatRoomNotFoundException(String message) {
        super(message);
    }

    public ChatRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
