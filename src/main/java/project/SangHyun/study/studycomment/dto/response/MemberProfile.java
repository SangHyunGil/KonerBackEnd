package project.SangHyun.study.studycomment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfile {
    private String nickname;
    private String profileImgUrl;
}
