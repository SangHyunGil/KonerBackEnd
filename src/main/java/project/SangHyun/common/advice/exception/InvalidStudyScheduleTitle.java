package project.SangHyun.common.advice.exception;

public class InvalidStudyScheduleTitle extends RuntimeException {
    public InvalidStudyScheduleTitle() {
    }

    public InvalidStudyScheduleTitle(String message) {
        super(message);
    }

    public InvalidStudyScheduleTitle(String message, Throwable cause) {
        super(message, cause);
    }
}
