package project.SangHyun.message.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountMessageDto {
    private Member sender;
    private Member receiver;
    private Long unReadCount;
}
