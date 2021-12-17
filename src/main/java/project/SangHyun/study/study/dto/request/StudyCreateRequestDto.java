package project.SangHyun.study.study.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 생성 요청")
public class StudyCreateRequestDto {
    @ApiModelProperty(value = "스터디 생성자", notes = "스터디 생성자를 입력해주세요.", required = true, example = "1L")
    private Long memberId;

    @ApiModelProperty(value = "스터디 제목", notes = "스터디 제목을 입력해주세요.", required = true, example = "백엔드 스터디")
    @NotBlank(message = "스터디 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "스터디 주제", notes = "스터디 주제를 입력해주세요.", required = true, example = "백엔드")
    @NotBlank(message = "스터디 주제를 입력해주세요.")
    private String topic;

    @ApiModelProperty(value = "스터디 내용", notes = "스터디 내용을 입력해주세요.", required = true, example = "내용")
    @NotBlank(message = "스터디 내용을 입력해주세요.")
    private String content;

    @ApiModelProperty(value = "스터디 인원수", notes = "스터디 인원수를 입력해주세요.", required = true, example = "1")
    @Min(value = 1, message = "스터디 인원수는 1 이상이어야 합니다.")
    private Long headCount;

    @ApiModelProperty(value = "스터디 상태", notes = "스터디 상태를 입력해주세요.", required = true, example = "공부 중")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태", notes = "스터디 모집 상태를 입력해주세요.", required = true, example = "모집 중")
    private RecruitState recruitState;

    public Study toEntity() {
        Study study = Study.builder()
                .title(title)
                .topic(topic)
                .content(content)
                .studyState(studyState)
                .member(new Member(memberId))
                .studyJoins(new ArrayList<>())
                .studyBoards(new ArrayList<>())
                .recruitState(recruitState)
                .headCount(headCount)
                .build();

        initStudyJoins(study);
        initStudyBoards(study);

        return study;
    }

    private void initStudyBoards(Study study) {
        study.join(new StudyJoin(new Member(memberId), study, StudyRole.CREATOR));
    }

    private void initStudyJoins(Study study) {
        study.addBoard(new StudyBoard("공지사항", study));
        study.addBoard(new StudyBoard("자유게시판", study));
    }
}
