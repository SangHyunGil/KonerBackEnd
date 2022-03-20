package project.SangHyun.service.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.member.service.SignService;
import project.SangHyun.member.service.VerifyService;
import project.SangHyun.message.repository.MessageRepository;
import project.SangHyun.message.service.MessageService;
import project.SangHyun.notification.service.NotificationService;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyarticle.service.StudyArticleService;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.service.StudyBoardService;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studycomment.service.StudyCommentService;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.studyschedule.repository.StudyScheduleRepository;
import project.SangHyun.study.studyschedule.service.StudyScheduleService;
import project.SangHyun.study.videoroom.domain.VideoRoom;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;
import project.SangHyun.study.videoroom.service.VideoRoomService;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class ServiceIntegrationTest {

    protected Member generalMember;
    protected Member studyMember;
    protected Member anotherStudyMember;
    protected Member notStudyMember;
    protected Member webAdminMember;
    protected Member studyAdminMember;
    protected Member hasNoResourceMember;
    protected Member studyApplyMember;
    protected Member studyCreator;
    protected Member notAuthMember;
    protected Study backendStudy;
    protected Study zeroHeadCountStudy;
    protected StudyBoard announceBoard;
    protected StudyArticle announceArticle;
    protected StudyComment parentComment;
    protected StudyComment childComment;
    protected StudySchedule studySchedule;
    protected VideoRoom jpaVideoRoom;

    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected StudyRepository studyRepository;
    @Autowired
    protected StudyArticleService studyArticleService;
    @Autowired
    protected StudyArticleRepository studyArticleRepository;
    @Autowired
    protected MessageRepository messageRepository;
    @Autowired
    protected StudyCommentService studyCommentService;
    @Autowired
    protected StudyScheduleService studyScheduleService;
    @Autowired
    protected StudyScheduleRepository studyScheduleRepository;
    @Autowired
    protected StudyService studyService;
    @Autowired
    protected StudyJoinService studyJoinService;
    @Autowired
    protected VerifyService verifyService;
    @Autowired
    protected StudyJoinRepository studyJoinRepository;
    @Autowired
    protected StudyCommentRepository studyCommentRepository;
    @Autowired
    protected MessageService messageService;
    @Autowired
    protected StudyBoardService studyBoardService;
    @Autowired
    protected NotificationService notificationService;
    @Autowired
    protected SignService signService;
    @Autowired
    protected VideoRoomService videoRoomService;
    @Autowired
    protected VideoRoomRepository videoRoomRepository;
    @Autowired
    protected JwtTokenHelper refreshTokenHelper;
    @Autowired
    protected RedisHelper redisHelper;
    @Autowired
    protected TestDB testDB;
    @Autowired
    protected EntityManager em;

    @BeforeEach
    void beforeEach() {
        generalMember = testDB.findGeneralMember();
        notAuthMember = testDB.findNotAuthMember();
        studyMember = testDB.findStudyGeneralMember();
        anotherStudyMember = testDB.findAnotherStudyGeneralMember();
        notStudyMember = testDB.findNotStudyMember();
        webAdminMember = testDB.findAdminMember();
        studyAdminMember = testDB.findStudyAdminMember();
        studyCreator = testDB.findStudyCreatorMember();
        hasNoResourceMember = testDB.findStudyMemberNotResourceOwner();

        backendStudy = testDB.findBackEndStudy();
        zeroHeadCountStudy = testDB.findZeroHeadCountStudy();
        studyApplyMember = testDB.findStudyApplyMember();
        announceBoard = testDB.findAnnounceBoard();
        announceArticle = testDB.findAnnounceArticle();
        parentComment = testDB.findParentComment();
        childComment = testDB.findChildComment();
        studySchedule = testDB.findSchedule();
        jpaVideoRoom = testDB.findJPAVideoRoom();
    }
}
