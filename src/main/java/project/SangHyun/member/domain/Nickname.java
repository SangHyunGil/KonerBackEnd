package project.SangHyun.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidNicknameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 12;

    @Column(nullable = false, unique = true)
    private String nickname;

    public Nickname(String nickname) {
        if (Objects.isNull( nickname) || nickname.isBlank() ||
            nickname.length() < MIN_LENGTH || nickname.length() > MAX_LENGTH) {
            throw new InvalidNicknameException();
        }
        this.nickname = nickname;
    }
}
