package project.SangHyun.study.studyjoin.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.common.dto.response.MemberProfile;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "가입한 스터디 조회 결과 서비스 계층 DTO")
public class FindJoinedStudyDto {

    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "회원 ID(PK)")
    private MemberProfile creator;

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private List<String> tags;

    @ApiModelProperty(value = "스터디 내용")
    private String description;

    @ApiModelProperty(value = "스터디 시작 일정")
    private String startDate;

    @ApiModelProperty(value = "스터디 종료 일정")
    private String endDate;

    @ApiModelProperty(value = "프로필 이미지")
    private String profileImg;

    @ApiModelProperty(value = "스터디 방법")
    private StudyMethod studyMethod;

    @ApiModelProperty(value = "스터디 상태")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태")
    private RecruitState recruitState;

    public static FindJoinedStudyDto create(Study study) {
        return new FindJoinedStudyDto(study.getId(),
                MemberProfile.create(study), study.getTitle(), study.getTagNames(),
                study.getDescription(), study.getStartDate(), study.getEndDate(),
                study.getProfileImgUrl(), study.getStudyMethod(), study.getStudyState(), study.getRecruitState());
    }
}
