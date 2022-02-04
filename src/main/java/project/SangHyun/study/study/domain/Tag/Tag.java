package project.SangHyun.study.study.domain.Tag;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InCorrectTagNameException;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "name")
public class Tag {
    private String name;

    public Tag(String name) {
        validTagName(name);
        this.name = name;
    }

    private void validTagName(String name) {
        if (name.isBlank())
            throw new InCorrectTagNameException();
    }
}
