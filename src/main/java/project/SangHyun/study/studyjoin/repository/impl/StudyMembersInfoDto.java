package project.SangHyun.study.studyjoin.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyMembersInfoDto {
    private String nickname;
    private String profileImgUrl;
    private StudyRole studyRole;
    private String applyContent;
}
