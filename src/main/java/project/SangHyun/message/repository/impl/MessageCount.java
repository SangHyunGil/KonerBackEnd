package project.SangHyun.message.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCount {
    private Long id;
    private Member sender;
    private Member receiver;
    private String content;
    private Long unReadCount;

    public MessageCount(Long id) {
        this.id = id;
    }

    public boolean isMoreRecentlyThan(MessageCount message) {
        return this.id > message.getId();
    }
}
