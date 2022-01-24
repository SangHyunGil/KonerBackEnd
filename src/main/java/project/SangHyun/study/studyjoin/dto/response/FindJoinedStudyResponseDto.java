package project.SangHyun.study.studyjoin.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.enums.RecruitState;
import project.SangHyun.study.study.domain.enums.StudyMethod;
import project.SangHyun.study.study.domain.enums.StudyRole;
import project.SangHyun.study.study.domain.enums.StudyState;
import project.SangHyun.study.study.dto.response.StudyMemberProfile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "가입한 스터디 조회 결과")
public class FindJoinedStudyResponseDto {
    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long studyId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private StudyMemberProfile creator;

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private List<String> tags;

    @ApiModelProperty(value = "스터디 내용")
    private String content;

    @ApiModelProperty(value = "스터디 시작 일정", notes = "스터디 시작 일정을 입력해주세요.", required = true, example = "2021-12-25")
    private String startDate;

    @ApiModelProperty(value = "스터디 종료 일정", notes = "스터디 종료 일정을 입력해주세요.", required = true, example = "2021-12-25")
    private String endDate;

    @ApiModelProperty(value = "프로필 이미지", notes = "프로필 이미지를 업로드해주세요.", required = true, example = "")
    private String profileImg;

    @ApiModelProperty(value = "스터디 방법")
    private StudyMethod studyMethod;

    @ApiModelProperty(value = "스터디 상태")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태")
    private RecruitState recruitState;

    public static FindJoinedStudyResponseDto create(Study study) {
        return new FindJoinedStudyResponseDto(study.getId(),
                new StudyMemberProfile(study.getMember().getNickname(), StudyRole.CREATOR, study.getMember().getProfileImgUrl()),
                study.getTitle(), study.getTags().getTagNames(), study.getContent(), study.getSchedule().getStartDate(), study.getSchedule().getEndDate(),
                study.getProfileImgUrl(), study.getStudyOptions().getStudyMethod(), study.getStudyOptions().getStudyState(), study.getStudyOptions().getRecruitState());
    }
}
