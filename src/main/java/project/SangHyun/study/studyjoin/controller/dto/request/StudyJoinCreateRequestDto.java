package project.SangHyun.study.studyjoin.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 신청")
public class StudyJoinCreateRequestDto {

    @ApiModelProperty(value = "스터디 지원 내용", notes = "스터디 지원 내용을 입력해주세요.", required = true, example = "반갑습니다.")
    @NotBlank(message = "스터디 지원 내용을 입력해주세요.")
    private String applyContent;

    public StudyJoinCreateDto toServiceDto() {
        return new StudyJoinCreateDto(applyContent);
    }
}
