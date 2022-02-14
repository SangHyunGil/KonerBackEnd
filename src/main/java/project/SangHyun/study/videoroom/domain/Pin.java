package project.SangHyun.study.videoroom.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidPinException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pin {

    private static final int MAX_LENGTH = 30;

    @Column(nullable = false, length = MAX_LENGTH)
    private String pin;

    public Pin(String pin) {
        if (isNotValidPin(pin)) {
            throw new InvalidPinException();
        }
    }

    private boolean isNotValidPin(String pin) {
        return pin.length() > MAX_LENGTH;
    }
}
