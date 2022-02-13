package project.SangHyun.message.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidMessageContentException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageContent {

    private static final int MAX_LENGTH = 1000;

    @Column(nullable = false, length = MAX_LENGTH)
    private String content;

    public MessageContent(String content) {
        if (isNotValidMessageContent(content)) {
            throw new InvalidMessageContentException();
        }
        this.content = content;
    }

    private boolean isNotValidMessageContent(String content) {
        return Objects.isNull(content) || content.isBlank() ||
                content.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageContent)) return false;
        MessageContent that = (MessageContent) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
