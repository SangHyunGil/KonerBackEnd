package project.SangHyun.study.studyjoin.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 권한 수정 요청 서비스 계층 DTO")
public class StudyJoinUpdateAuthorityDto {

    @ApiModelProperty(value = "스터디 권한")
    private StudyRole studyRole;
}
