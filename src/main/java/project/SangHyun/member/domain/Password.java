package project.SangHyun.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidPasswordException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Column(nullable = false)
    private String password;

    public Password(String password) {
        if (Objects.isNull(password) || password.isBlank()) {
            throw new InvalidPasswordException();
        }
        this.password = password;
    }
}
