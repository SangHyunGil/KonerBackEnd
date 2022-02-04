package project.SangHyun.study.study.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.*;
import project.SangHyun.study.study.domain.Tag.Tag;
import project.SangHyun.study.study.domain.Tag.Tags;
import project.SangHyun.study.study.domain.enums.RecruitState;
import project.SangHyun.study.study.domain.enums.StudyMethod;
import project.SangHyun.study.study.domain.enums.StudyRole;
import project.SangHyun.study.study.domain.enums.StudyState;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<String> tags;

    @ApiModelProperty(value = "스터디 내용", notes = "스터디 내용을 입력해주세요.", required = true, example = "내용")
    @NotBlank(message = "스터디 내용을 입력해주세요.")
    private String content;

    @ApiModelProperty(value = "스터디 학과", notes = "스터디 학과를 입력해주세요.", required = true, example = "CSE")
    @NotBlank(message = "스터디 학과를 입력해주세요.")
    private String department;

    @ApiModelProperty(value = "스터디 시작 일정", notes = "스터디 시작 일정을 입력해주세요.", required = true, example = "2021-12-25")
    private String startDate;

    @ApiModelProperty(value = "스터디 종료 일정", notes = "스터디 종료 일정을 입력해주세요.", required = true, example = "2021-12-25")
    private String endDate;

    @ApiModelProperty(value = "스터디 인원수", notes = "스터디 인원수를 입력해주세요.", required = true, example = "1")
    @Min(value = 1, message = "스터디 인원수는 1 이상이어야 합니다.")
    private Long headCount;

    @ApiModelProperty(value = "프로필 이미지", notes = "프로필 이미지를 업로드해주세요.", required = true, example = "")
    private MultipartFile profileImg;

    @ApiModelProperty(value = "스터디 방식", notes = "스터디 방식을 입력해주세요.", required = true, example = "대면")
    private StudyMethod studyMethod;

    @ApiModelProperty(value = "스터디 상태", notes = "스터디 상태를 입력해주세요.", required = true, example = "공부 중")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태", notes = "스터디 모집 상태를 입력해주세요.", required = true, example = "모집 중")
    private RecruitState recruitState;

    public Study toEntity(String profileImg) {
        Study study = Study.builder()
                .title(title)
                .content(content)
                .department(department)
                .tags(new Tags(tags.stream().map(tag -> new Tag(tag)).collect(Collectors.toList())))
                .schedule(new Schedule(startDate, endDate))
                .studyOptions(new StudyOptions(studyState, recruitState, studyMethod))
                .member(new Member(memberId))
                .studyJoins(new ArrayList<>())
                .studyBoards(new ArrayList<>())
                .profileImgUrl(profileImg)
                .headCount(headCount)
                .build();

        initStudyJoins(study);
        initStudyBoards(study);

        return study;
    }

    private void initStudyJoins(Study study) {
        study.join(new StudyJoin(new Member(memberId), null, study, StudyRole.CREATOR));
    }

    private void initStudyBoards(Study study) {
        study.addBoard(new StudyBoard("공지사항", study));
        study.addBoard(new StudyBoard("자유게시판", study));
    }
}
