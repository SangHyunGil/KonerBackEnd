package project.SangHyun.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.videoroom.domain.VideoRoom;

import java.util.ArrayList;
import java.util.List;

class VideoRoomRepositoryTest extends RepositoryTest{

    Member member;
    Study study;
    VideoRoom videoRoom;

    @BeforeEach
    void beforeEach() {
        Member createMember = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "상현입니다.");
        member = memberRepository.save(createMember);

        Study createStudy = new Study("백엔드 모집", List.of("백엔드", "JPA", "스프링"), "백엔드 모집합니다.", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", 5L, "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, member, new ArrayList<>(), new ArrayList<>());
        study = studyRepository.save(createStudy);

        VideoRoom createVideoRoom = new VideoRoom(1234L, "백엔드 스터디", null, member, study);
        videoRoom = videoRoomRepository.save(createVideoRoom);
    }

    @Test
    @DisplayName("화상회의 방 번호로 방을 찾는다.")
    public void test1() throws Exception {
        //given
        Long roomId = 1234L;

        //when
        VideoRoom videoRoom = videoRoomRepository.findByRoomId(roomId);

        //then
        Assertions.assertEquals("백엔드 스터디", videoRoom.getTitleName());
    }


    @Test
    @DisplayName("해당 스터디에 포함된 모든 화상회의를 조회한다.")
    public void test2() throws Exception {
        //given
        Long studyId = study.getId();

        //when
        List<VideoRoom> videoRooms = videoRoomRepository.findAllByStudyId(studyId);

        //then
        Assertions.assertEquals(1, videoRooms.size());
    }
}