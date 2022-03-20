package project.SangHyun.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.message.repository.MessageRepository;
import project.SangHyun.notification.repository.NotificationRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.repository.StudyBoardRepository;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    StudyBoardRepository studyBoardRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    StudyCommentRepository studyCommentRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    VideoRoomRepository videoRoomRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager em;
}