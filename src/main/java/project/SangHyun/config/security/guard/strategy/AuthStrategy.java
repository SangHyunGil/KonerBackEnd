package project.SangHyun.config.security.guard.strategy;

public interface AuthStrategy {
    boolean check(Long accessId, Long resourceId);
}
