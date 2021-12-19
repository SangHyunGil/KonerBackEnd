package project.SangHyun.study.studyjoin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyFindMembersResponseDto {
    private Long id;
    private String name;
    private StudyRole studyRole;

    public static StudyFindMembersResponseDto create(StudyMembersInfoDto studyMembersInfoDto) {
        return new StudyFindMembersResponseDto(studyMembersInfoDto.getMemberId(), studyMembersInfoDto.getMemberName(), studyMembersInfoDto.getStudyRole());
    }
}
