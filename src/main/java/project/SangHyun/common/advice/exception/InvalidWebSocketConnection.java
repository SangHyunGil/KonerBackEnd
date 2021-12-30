package project.SangHyun.common.advice.exception;

public class InvalidWebSocketConnection extends RuntimeException {
    public InvalidWebSocketConnection() {
    }

    public InvalidWebSocketConnection(String message) {
        super(message);
    }

    public InvalidWebSocketConnection(String message, Throwable cause) {
        super(message, cause);
    }
}
