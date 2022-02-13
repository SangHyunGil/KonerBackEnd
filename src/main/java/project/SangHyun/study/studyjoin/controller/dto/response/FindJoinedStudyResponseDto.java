package project.SangHyun.study.studyjoin.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.dto.StudyMemberProfile;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.studyjoin.service.dto.response.FindJoinedStudyDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "가입한 스터디 조회 결과")
public class FindJoinedStudyResponseDto {

    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "회원 ID(PK)")
    private StudyMemberProfile creator;

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private List<String> tags;

    @ApiModelProperty(value = "스터디 내용")
    private String description;

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

    public static FindJoinedStudyResponseDto create(FindJoinedStudyDto findJoinedStudyDto) {
        return new FindJoinedStudyResponseDto(findJoinedStudyDto.getId(),
                findJoinedStudyDto.getCreator(), findJoinedStudyDto.getTitle(), findJoinedStudyDto.getTags(),
                findJoinedStudyDto.getDescription(), findJoinedStudyDto.getStartDate(), findJoinedStudyDto.getEndDate(),
                findJoinedStudyDto.getProfileImg(), findJoinedStudyDto.getStudyMethod(), findJoinedStudyDto.getStudyState(),
                findJoinedStudyDto.getRecruitState());
    }
}
