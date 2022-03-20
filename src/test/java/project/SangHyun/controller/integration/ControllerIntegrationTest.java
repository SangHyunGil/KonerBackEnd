package project.SangHyun.controller.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.TestDB;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.message.repository.MessageRepository;
import project.SangHyun.notification.service.NotificationService;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.videoroom.domain.VideoRoom;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public abstract class ControllerIntegrationTest {

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
    protected StudyBoard announceBoard;
    protected StudyArticle announceArticle;
    protected StudyComment parentComment;
    protected StudySchedule studySchedule;
    protected VideoRoom jpaVideoRoom;

    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected StudyRepository studyRepository;
    @Autowired
    protected StudyJoinRepository studyJoinRepository;
    @Autowired
    protected StudyArticleRepository studyArticleRepository;
    @Autowired
    protected MessageRepository messageRepository;
    @Autowired
    protected NotificationService notificationService;
    @Autowired
    protected JwtTokenHelper refreshTokenHelper;
    @Autowired
    protected RedisHelper redisHelper;
    @Autowired
    protected JwtTokenHelper accessTokenHelper;
    @Autowired
    protected TestDB testDB;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        notAuthMember = testDB.findNotAuthMember();
        studyMember = testDB.findStudyGeneralMember();
        anotherStudyMember = testDB.findAnotherStudyGeneralMember();
        notStudyMember = testDB.findNotStudyMember();
        webAdminMember = testDB.findAdminMember();
        studyAdminMember = testDB.findStudyAdminMember();
        studyCreator = testDB.findStudyCreatorMember();
        hasNoResourceMember = testDB.findStudyMemberNotResourceOwner();

        backendStudy = testDB.findBackEndStudy();
        studyApplyMember = testDB.findStudyApplyMember();
        announceBoard = testDB.findAnnounceBoard();
        announceArticle = testDB.findAnnounceArticle();
        parentComment = testDB.findParentComment();
        studySchedule = testDB.findSchedule();
        jpaVideoRoom = testDB.findJPAVideoRoom();
    }
}
