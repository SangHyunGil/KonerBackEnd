package project.SangHyun.dto.response.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 찾기 요청 결과")
public class StudyFindResponseDto {
    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long studyId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private String topic;

    @ApiModelProperty(value = "스터디 내용")
    private String content;

    @ApiModelProperty(value = "스터디 참여수")
    private Long joinCount;

    @ApiModelProperty(value = "스터디 정원수")
    private Long headCount;

    @ApiModelProperty(value = "스터디 참여인원들")
    private List<String> studyMembers;

    @ApiModelProperty(value = "스터디 게시판들")
    private List<Long> studyBoards;

    @ApiModelProperty(value = "스터디 상태")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태")
    private RecruitState recruitState;

    public static StudyFindResponseDto createDto(Study study) {
        List<String> studyMembers = study.getStudyJoins().stream()
                .map(studyJoin -> studyJoin.getMember().getNickname())
                .collect(Collectors.toList());

        List<Long> studyBoards = study.getStudyBoards().stream()
                .map(studyBoard -> studyBoard.getId())
                .collect(Collectors.toList());

        return StudyFindResponseDto.builder()
                .studyId(study.getId())
                .memberId(study.getMember().getId())
                .title(study.getTitle())
                .topic(study.getTopic())
                .joinCount((long) studyMembers.size())
                .headCount(study.getHeadCount())
                .studyMembers(studyMembers)
                .content(study.getContent())
                .studyBoards(studyBoards)
                .studyState(study.getStudyState())
                .recruitState(study.getRecruitState())
                .build();
    }

    @Builder
    public StudyFindResponseDto(Long studyId, Long memberId, String title, String topic, String content, Long joinCount, Long headCount, List<String> studyMembers, List<Long> studyBoards, StudyState studyState, RecruitState recruitState) {
        this.studyId = studyId;
        this.memberId = memberId;
        this.title = title;
        this.topic = topic;
        this.content = content;
        this.joinCount = joinCount;
        this.headCount = headCount;
        this.studyMembers = studyMembers;
        this.studyBoards = studyBoards;
        this.studyState = studyState;
        this.recruitState = recruitState;
    }
}
