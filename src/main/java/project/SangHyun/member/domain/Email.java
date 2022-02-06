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
        if (isNotValidEmail(email)) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }

    private boolean isNotValidEmail(String email) {
        return Objects.isNull(email) || email.isBlank();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email1 = (Email) o;
        return getEmail().equals(email1.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}