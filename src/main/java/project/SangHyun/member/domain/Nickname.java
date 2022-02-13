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

    @Column(nullable = false, unique = true, length = MAX_LENGTH)
    private String nickname;

    public Nickname(String nickname) {
        if (isNotValidNickname(nickname)) {
            throw new InvalidNicknameException();
        }
        this.nickname = nickname;
    }

    private boolean isNotValidNickname(String nickname) {
        return Objects.isNull(nickname) || nickname.isBlank() ||
            nickname.length() < MIN_LENGTH || nickname.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nickname)) return false;
        Nickname nickname1 = (Nickname) o;
        return getNickname().equals(nickname1.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickname());
    }
}
