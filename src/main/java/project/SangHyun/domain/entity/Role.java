package project.SangHyun.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_NOT_PERMITTED("ROLE_NOT_PERMITTED"),
    ROLE_MEMBER("ROLE_MEMBER"), ROLE_ADMIN("ROLE_ADMIN");

    private String desc;
}
