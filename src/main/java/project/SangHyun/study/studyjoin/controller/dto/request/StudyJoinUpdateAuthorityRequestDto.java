package project.SangHyun.study.studyjoin.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinUpdateAuthorityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 권한 수정 요청")
public class StudyJoinUpdateAuthorityRequestDto {

    @ApiModelProperty(value = "스터디 권한")
    private StudyRole studyRole;

    public StudyJoinUpdateAuthorityDto toServiceDto() {
        return new StudyJoinUpdateAuthorityDto(studyRole);
    }
}
