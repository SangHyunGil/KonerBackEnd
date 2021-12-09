package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.impl.dto.StudyInfoDto;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 참여 요청 결과")
public class StudyJoinResponseDto {
    @ApiModelProperty(value = "스터디 참여 ID(PK)")
    private Long studyJoinId;

    @ApiModelProperty(value = "스터디 참여 정보")
    private List<StudyInfoDto> studyInfos;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    public static StudyJoinResponseDto createDto(StudyJoin studyJoin) {
        List<StudyInfoDto> studyInfoDtos = List.of(new StudyInfoDto(studyJoin.getStudy().getId(), studyJoin.getStudyRole()));

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
