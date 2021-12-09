package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.impl.dto.StudyInfoDto;

import java.util.List;

@Data
@NoArgsConstructor
public class StudyJoinResponseDto {
    private Long studyJoinId;
    private List<StudyInfoDto> studyInfos;
    private Long memberId;

    public static StudyJoinResponseDto createDto(StudyJoin studyJoin) {
        List<StudyInfoDto> studyInfoDtos = List.of(new StudyInfoDto(studyJoin.getId(), studyJoin.getStudyRole()));

        return StudyJoinResponseDto.builder()
                .studyJoinId(studyJoin.getId())
                .studyInfos(studyInfoDtos)
                .memberId(studyJoin.getMember().getId())
                .build();
    }

    @Builder
    public StudyJoinResponseDto(Long studyJoinId, List<StudyInfoDto> studyInfos, Long memberId) {
        this.studyJoinId = studyJoinId;
        this.studyInfos = studyInfos;
        this.memberId = memberId;
    }
}
