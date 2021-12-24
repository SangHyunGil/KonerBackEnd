package project.SangHyun.study.study.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class MemberProfile {
    private String nickname;
    private String profileImgUrl;
}
