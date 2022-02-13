package project.SangHyun.common.advice.exception;

public class StudyScheduleNotFoundException extends RuntimeException {
    public StudyScheduleNotFoundException() {
    }

    public StudyScheduleNotFoundException(String message) {
        super(message);
    }

    public StudyScheduleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
