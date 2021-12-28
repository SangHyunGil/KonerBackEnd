package project.SangHyun.study.studycomment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.enums.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfile {
    private String nickname;
    private String profileImgUrl;
}
