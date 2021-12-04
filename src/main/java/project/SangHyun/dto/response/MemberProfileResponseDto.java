package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

import java.util.List;

@Data
@NoArgsConstructor
public class MemberProfileResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String department;
    private List<BoardFindResponseDto> boards;

    public static MemberProfileResponseDto createDto(Member member, List<BoardFindResponseDto> boards) {
        return MemberProfileResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .boards(boards)
                .build();
    }

    @Builder
    public MemberProfileResponseDto(Long memberId, String email, String nickname, String department, List<BoardFindResponseDto> boards) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
        this.boards = boards;
    }
}
