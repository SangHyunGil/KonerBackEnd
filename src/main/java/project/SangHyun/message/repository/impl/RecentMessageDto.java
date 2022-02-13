package project.SangHyun.message.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentMessageDto {
    private Long id;
    private Member sender;
    private Member receiver;
    private String content;
    private Long unReadCount;

    public RecentMessageDto(Long id) {
        this.id = id;
    }

    public boolean isMoreRecentlyThan(RecentMessageDto message) {
        return this.id > message.getId();
    }
}
