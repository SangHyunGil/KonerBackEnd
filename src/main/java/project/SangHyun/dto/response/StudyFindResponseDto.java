package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class StudyFindResponseDto {
    private Long studyId;
    private Long memberId;
    private String title;
    private String topic;
    private String content;
    private Long joinCount;
    private Long headCount;
    private List<String> studyMembers;
    private List<Long> studyBoards;
    private StudyState studyState;
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
