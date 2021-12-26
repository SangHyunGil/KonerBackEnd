package project.SangHyun.study.study.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.enums.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
class MemberProfile {
    private String nickname;
    private StudyRole studyRole;
    private String profileImgUrl;
}
