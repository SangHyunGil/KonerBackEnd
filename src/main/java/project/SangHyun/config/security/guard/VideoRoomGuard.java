package project.SangHyun.config.security.guard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.videoroom.domain.VideoRoom;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;

@Component
@RequiredArgsConstructor
public class VideoRoomGuard {

    private final AuthHelper authHelper;
    private final StudyJoinRepository studyJoinRepository;
    private final VideoRoomRepository videoRoomRepository;

    public boolean checkJoin(Long studyId) {
        return authHelper.isAuthenticated() && isJoinMember(studyId);
    }

    public boolean isJoinMember(Long studyId) {
        return (isMember() && isStudyMember(studyId)) || isAdmin();
    }

    private boolean isStudyMember(Long studyId) {
        return studyJoinRepository.isStudyMember(studyId, authHelper.extractMemberId());
    }

    public boolean checkJoinAndAuth(Long studyId, Long videoRoomId) {
        return authHelper.isAuthenticated() && hasAuthority(studyId, videoRoomId);
    }

    private boolean hasAuthority(Long studyId, Long videoRoomId) {
        return (isMember() && isStudyMember(studyId) && hasResourceAuthority(studyId, videoRoomId)) || isAdmin();
    }

    private boolean hasResourceAuthority(Long studyId, Long roomId) {
        return isVideoRoomOwner(roomId) || isStudyAdminOrCreator(studyId);
    }

    private boolean isVideoRoomOwner(Long roomId) {
        VideoRoom videoRoom = videoRoomRepository.findByRoomId(roomId);
        return videoRoom.getCreatorId().equals(authHelper.extractMemberId());
    }

    private boolean isStudyAdminOrCreator(Long studyId) {
        StudyJoin studyJoin = studyJoinRepository.findStudyRole(authHelper.extractMemberId(), studyId).orElseThrow(() -> new AccessDeniedException(""));
        return studyJoin.getStudyRole().equals(StudyRole.ADMIN) || studyJoin.getStudyRole().equals(StudyRole.CREATOR);
    }

    private Boolean isMember() {
        return authHelper.extractMemberRole().equals("ROLE_MEMBER");
    }

    private Boolean isAdmin() {
        return authHelper.extractMemberRole().equals("ROLE_ADMIN");
    }
}
