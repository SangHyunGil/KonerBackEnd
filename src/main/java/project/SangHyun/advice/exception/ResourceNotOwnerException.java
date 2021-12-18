package project.SangHyun.advice.exception;

public class ResourceNotOwnerException extends RuntimeException {
    public ResourceNotOwnerException() {
    }

    public ResourceNotOwnerException(String message) {
        super(message);
    }

    public ResourceNotOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
