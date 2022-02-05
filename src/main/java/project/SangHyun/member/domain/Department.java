package project.SangHyun.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum Department {
    ME("기계공학부"), ECE("전기전자통신공학부"), DEA("디자인, 건축공학부"),
    MCE("메카트로닉스공학부"), IM("산업경영학부"), EMCE("에너지신소재 화학공학부"),
    CSE("컴퓨터공학부"), ESP("고용서비스정책학부");

    private String desc;
}
