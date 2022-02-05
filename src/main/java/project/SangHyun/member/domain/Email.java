package project.SangHyun.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidEmailException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final int MAX_LENGTH = 12;

    @Column(nullable = false, unique = true, length = MAX_LENGTH)
    private String email;

    public Email(String email) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }
}