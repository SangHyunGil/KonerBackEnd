package project.SangHyun.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberProfile {
    private String nickname;
    private String profileImgUrl;

    public static StudyMemberProfile create(Study study) {
        return new StudyMemberProfile(study.getCreatorNickname(), study.getCreatorProfileImgUrl());
    }

    public static StudyMemberProfile create(StudyJoin studyJoin) {
        return new StudyMemberProfile(studyJoin.getParticipantNickname(), studyJoin.getParticipantProfileImgUrl());
    }

    public static StudyMemberProfile create(StudyComment studyComment) {
        return new StudyMemberProfile(studyComment.getCreatorNickname(), studyComment.getCreatorProfileImgUrl());
    }
}
