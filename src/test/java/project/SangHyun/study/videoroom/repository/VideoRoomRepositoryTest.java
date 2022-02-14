package project.SangHyun.study.videoroom.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.videoroom.domain.VideoRoom;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class VideoRoomRepositoryTest {

    Member member;
    VideoRoom videoRoom;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VideoRoomRepository videoRoomRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() {
        Member createMember = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "상현입니다.");
        member = memberRepository.save(createMember);

        VideoRoom createVideoRoom = new VideoRoom(1234L, "백엔드 스터디", null, member);
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

}