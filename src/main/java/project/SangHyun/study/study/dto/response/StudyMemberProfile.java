package project.SangHyun.study.study.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.enums.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberProfile {
    private String nickname;
    private StudyRole studyRole;
    private String profileImgUrl;
}
