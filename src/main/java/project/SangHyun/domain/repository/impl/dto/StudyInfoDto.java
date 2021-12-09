package project.SangHyun.domain.repository.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.enums.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyInfoDto {
    Long studyId;
    StudyRole studyRole;
}
