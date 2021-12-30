package project.SangHyun.common.advice.exception;

public class RedisValueDifferentException extends RuntimeException {
    public RedisValueDifferentException() {
    }

    public RedisValueDifferentException(String message) {
        super(message);
    }

    public RedisValueDifferentException(String message, Throwable cause) {
        super(message, cause);
    }
}
