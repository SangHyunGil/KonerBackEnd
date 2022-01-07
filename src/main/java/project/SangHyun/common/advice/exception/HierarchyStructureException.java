package project.SangHyun.common.advice.exception;

public class HierarchyStructureException extends RuntimeException {
    public HierarchyStructureException() {
    }

    public HierarchyStructureException(String message) {
        super(message);
    }

    public HierarchyStructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
