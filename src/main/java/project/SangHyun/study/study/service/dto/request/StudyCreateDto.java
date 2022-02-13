package project.SangHyun.study.study.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 생성 요청 서비스 계층 DTO")
public class StudyCreateDto {

    @ApiModelProperty(value = "스터디 생성자")
    private Long memberId;

    @ApiModelProperty(value = "스터디 제목")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private List<String> tags;

    @ApiModelProperty(value = "스터디 설명")
    @NotBlank(message = "스터디 설명을 입력해주세요.")
    private String description;

    @ApiModelProperty(value = "스터디 학과")
    private StudyCategory department;

    @ApiModelProperty(value = "스터디 시작 일정")
    private String startDate;

    @ApiModelProperty(value = "스터디 종료 일정")
    private String endDate;

    @ApiModelProperty(value = "스터디 인원수")
    private Long headCount;

    @ApiModelProperty(value = "프로필 이미지")
    private MultipartFile profileImg;

    @ApiModelProperty(value = "스터디 방식")
    private StudyMethod studyMethod;

    @ApiModelProperty(value = "스터디 상태")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태")
    private RecruitState recruitState;

    public Study toEntity(Member member, String profileImgUrl) {
        Study study = Study.builder()
                .title(title)
                .description(description)
                .category(department)
                .tags(tags)
                .startDate(startDate)
                .endDate(endDate)
                .studyState(studyState)
                .studyMethod(studyMethod)
                .recruitState(recruitState)
                .profileImgUrl(profileImgUrl)
                .headCount(headCount)
                .member(member)
                .studyJoins(new ArrayList<>())
                .studyBoards(new ArrayList<>())
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
