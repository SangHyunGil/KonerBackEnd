package project.SangHyun.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    ROLE_NOT_PERMITTED("ROLE_NOT_PERMITTED"),
    ROLE_MEMBER("ROLE_MEMBER"), ROLE_ADMIN("ROLE_ADMIN");

    private String desc;
}
