package project.SangHyun.study.studyjoin.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.enums.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyInfoDto {
    Long studyId;
    StudyRole studyRole;
}
