package project.SangHyun.common.advice.exception;

public class DuplicateTagsException extends RuntimeException {
    public DuplicateTagsException() {
    }

    public DuplicateTagsException(String message) {
        super(message);
    }

    public DuplicateTagsException(String message, Throwable cause) {
        super(message, cause);
    }
}
