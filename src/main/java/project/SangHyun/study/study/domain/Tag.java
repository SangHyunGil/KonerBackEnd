package project.SangHyun.study.study.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.SangHyun.common.advice.exception.InCorrectTagNameException;

import javax.persistence.*;

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
