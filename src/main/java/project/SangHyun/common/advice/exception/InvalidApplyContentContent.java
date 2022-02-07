package project.SangHyun.common.advice.exception;

public class InvalidApplyContentContent extends RuntimeException {
    public InvalidApplyContentContent() {
    }

    public InvalidApplyContentContent(String message) {
        super(message);
    }

    public InvalidApplyContentContent(String message, Throwable cause) {
        super(message, cause);
    }
}
