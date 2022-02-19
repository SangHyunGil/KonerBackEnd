package project.SangHyun.config.security.guard.strategy;

import org.springframework.stereotype.Component;

@Component
public class MemberOwnerStrategy implements AuthStrategy {

    public boolean check(Long accessMemberId, Long ownerId) {
        return accessMemberId.equals(ownerId);
    }
}
