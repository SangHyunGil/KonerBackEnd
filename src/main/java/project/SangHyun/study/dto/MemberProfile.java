package project.SangHyun.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.videoroom.domain.VideoRoom;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfile {
    private String nickname;
    private String profileImgUrl;

    public static MemberProfile create(Study study) {
        return new MemberProfile(study.getCreatorNickname(), study.getCreatorProfileImgUrl());
    }

    public static MemberProfile create(StudyJoin studyJoin) {
        return new MemberProfile(studyJoin.getParticipantNickname(), studyJoin.getParticipantProfileImgUrl());
    }

    public static MemberProfile create(StudyArticle studyArticle) {
        return new MemberProfile(studyArticle.getCreatorNickname(), studyArticle.getCreatorProfileUrlImg());
    }

    public static MemberProfile create(StudyComment studyComment) {
        return new MemberProfile(studyComment.getCreatorNickname(), studyComment.getCreatorProfileImgUrl());
    }

    public static MemberProfile create(VideoRoom videoRoom) {
        return new MemberProfile(videoRoom.getCreatorNickname(), videoRoom.getCreatorProfileImgUrl());
    }
}
