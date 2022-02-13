package project.SangHyun.common.advice.exception;

public class InvalidProfileUrlImg extends RuntimeException {
    public InvalidProfileUrlImg() {
    }

    public InvalidProfileUrlImg(String message) {
        super(message);
    }

    public InvalidProfileUrlImg(String message, Throwable cause) {
        super(message, cause);
    }
}
