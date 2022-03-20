package project.SangHyun;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.MessageRepository;
import project.SangHyun.notification.domain.Notification;
import project.SangHyun.notification.domain.NotificationType;
import project.SangHyun.notification.repository.NotificationRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.studyschedule.repository.StudyScheduleRepository;
import project.SangHyun.study.videoroom.domain.VideoRoom;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDB {

    public final InitService initService;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyArticleRepository studyArticleRepository;
    private final StudyCommentRepository studyCommentRepository;
    private final StudyScheduleRepository studyScheduleRepository;
    private final VideoRoomRepository videoRoomRepository;

    @PostConstruct
    public void init() {
        initService.initMember();
        initService.initStudy();
    }

    @Transactional(readOnly = true)
    public Member findGeneralMember() {
        return memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Member findAnotherStudyGeneralMember() {
        return memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Member findNotAuthMember() {
        return memberRepository.findByEmail("xptmxm2!").orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Member findAdminMember() {
        return memberRepository.findByEmail("xptmxm4!").orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Member findNotStudyMember() {
        return memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
    }

    public Member findStudyApplyMember() {
        return memberRepository.findByEmail("xptmxm10!").orElseThrow(MemberNotFoundException::new);
    }

    public Member findStudyMemberNotResourceOwner() {
        return memberRepository.findByEmail("xptmxm11!").orElseThrow(MemberNotFoundException::new);
    }

    public Member findStudyGeneralMember() {
        return memberRepository.findByEmail("xptmxm0!").orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Member findStudyCreatorMember() {
        return memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Member findStudyAdminMember() {
        return memberRepository.findByEmail("xptmxm5!").orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Study findBackEndStudy() {
        return studyRepository.findStudyByTitle("백엔드 모집").get(0);
    }

    @Transactional(readOnly = true)
    public Study findZeroHeadCountStudy() {
        return studyRepository.findStudyByTitle("임시용").get(0);
    }

    @Transactional(readOnly = true)
    public StudyBoard findAnnounceBoard() {
        return studyRepository.findStudyByTitle("백엔드 모집").get(0).getStudyBoards().get(0);
    }

    @Transactional(readOnly = true)
    public StudyArticle findAnnounceArticle() {
        return studyArticleRepository.findArticleByTitle("공지사항").get(0);
    }

    @Transactional(readOnly = true)
    public StudyComment findParentComment() {
        return studyCommentRepository.findAllByMemberId(memberRepository.findByEmail("xptmxm0!").orElseThrow(MemberNotFoundException::new).getId()).get(0);
    }

    @Transactional(readOnly = true)
    public StudyComment findChildComment() {
        return studyCommentRepository.findAllByMemberId(memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new).getId()).get(0);
    }

    @Transactional(readOnly = true)
    public StudySchedule findSchedule() {
        return studyScheduleRepository.findAll().get(0);
    }

    @Transactional(readOnly = true)
    public VideoRoom findJPAVideoRoom() {
        return videoRoomRepository.findByRoomId(1234L);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {
        private final MemberRepository memberRepository;
        private final StudyRepository studyRepository;
        private final StudyArticleRepository studyArticleRepository;
        private final StudyCommentRepository studyCommentRepository;
        private final StudyJoinRepository studyJoinRepository;
        private final MessageRepository messageRepository;
        private final StudyScheduleRepository studyScheduleRepository;
        private final VideoRoomRepository videoRoomRepository;
        private final NotificationRepository notificationRepository;
        private final PasswordEncoder passwordEncoder;

        public void init() {
            initMember();
            initStudy();
        }

        public void initMember() {
            Member memberA = new Member("xptmxm1!", passwordEncoder.encode("xptmxm1!"), "승범", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "승범입니다.");
            memberRepository.save(memberA);

            Member memberB = new Member("xptmxm2!", passwordEncoder.encode("xptmxm2!"), "유나", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_NOT_PERMITTED, "유나입니다.");
            memberRepository.save(memberB);

            Member memberC = new Member("xptmxm4!", passwordEncoder.encode("xptmxm4!"), "윤정", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_ADMIN, "윤정입니다.");
            memberRepository.save(memberC);
        }

        public void initStudy() {
            Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "상현입니다.");
            Member storedMemberA = memberRepository.save(memberA);

            Member memberB = new Member("xptmxm5!", passwordEncoder.encode("xptmxm5!"), "진영", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "진영입니다.");
            Member storedMemberB = memberRepository.save(memberB);

            Member memberC = new Member("xptmxm0!", passwordEncoder.encode("xptmxm0!"), "예림", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "예림입니다.");
            Member storedMemberC = memberRepository.save(memberC);

            Member memberD = new Member("xptmxm10!", passwordEncoder.encode("xptmxm10!"), "동욱", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "동욱입니다.");
            Member storedMemberD = memberRepository.save(memberD);

            Member memberE = new Member("xptmxm11!", passwordEncoder.encode("xptmxm11!"), "영탁", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "영탁입니다.");
            Member storedMemberE = memberRepository.save(memberE);

            Study emptyStudy = new Study("임시용", List.of("임시용"), "임시용", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", 1L, "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, storedMemberB, new ArrayList<>(), new ArrayList<>());

            Study study = new Study("백엔드 모집", List.of("백엔드", "JPA", "스프링"), "백엔드 모집합니다.", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", 5L, "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, storedMemberA, new ArrayList<>(), new ArrayList<>());

            studyRepository.save(study);
            studyRepository.save(emptyStudy);

            StudyJoin studyJoin = new StudyJoin(storedMemberA, "", study, StudyRole.CREATOR);
            studyJoinRepository.save(studyJoin);

            StudyJoin studyJoin2 = new StudyJoin(storedMemberB, "빠르게 진행합니다.", study, StudyRole.ADMIN);
            studyJoinRepository.save(studyJoin2);

            StudyJoin studyJoin3 = new StudyJoin(storedMemberC, "빠르게 진행합니다.", study, StudyRole.MEMBER);
            studyJoinRepository.save(studyJoin3);

            StudyJoin studyJoin4 = new StudyJoin(storedMemberD, "빠르게 진행합니다.", study, StudyRole.APPLY);
            studyJoinRepository.save(studyJoin4);

            StudyJoin studyJoin5 = new StudyJoin(storedMemberE, "빠르게 진행합니다.", study, StudyRole.MEMBER);
            studyJoinRepository.save(studyJoin5);

            StudyJoin studyJoin6 = new StudyJoin(storedMemberA, "", emptyStudy, StudyRole.CREATOR);
            studyJoinRepository.save(studyJoin6);

            StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
            StudyBoard studyBoard2 = new StudyBoard("자유게시판", study);
            StudyBoard studyBoard3 = new StudyBoard("알고리즘", study);

            StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", 0L, storedMemberA, studyBoard1);
            StudyArticle studyArticle2 = new StudyArticle("자유게시판 테스트 글", "자유게시판 테스트 글입니다.", 0L, storedMemberA, studyBoard1);
            StudyArticle studyArticle3 = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", 0L, storedMemberA, studyBoard1);

            StudyComment studyComment1 = new StudyComment(storedMemberC, studyArticle1, null, "공지사항 댓글1입니다.", false);
            StudyComment studyComment2 = new StudyComment(storedMemberA, studyArticle1, studyComment1, "공지사항 댓글2입니다.", false);

            studyCommentRepository.save(studyComment1);
            studyCommentRepository.save(studyComment2);

            studyArticleRepository.save(studyArticle1);
            studyArticleRepository.save(studyArticle2);
            studyArticleRepository.save(studyArticle3);

            study.addBoard(studyBoard1);
            study.addBoard(studyBoard2);
            study.addBoard(studyBoard3);

            StudySchedule studySchedule = new StudySchedule("JPA 스터디", "2021-09-10", "2022-09-10", "18:00", "22:00", study);
            studyScheduleRepository.save(studySchedule);

            VideoRoom videoRoom = new VideoRoom(1234L, "JPA 스터디", null, memberC, study);
            videoRoomRepository.save(videoRoom);

            // findStudyGeneralMember, findStudyApplyMember, findStudyMemberNotResourceOwner
            initMessage(storedMemberC, storedMemberD, storedMemberE);

            for (int i = 0; i < 5; i++) {
                Notification notification = new Notification(storedMemberD, NotificationType.ACCEPT, "알림"+i, "study/51", false);
                notificationRepository.save(notification);
            }
        }

        public void initMessage(Member testMemberA, Member testMemberB, Member testMemberC) {
            Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
            messageRepository.save(messageA);
            Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
            messageRepository.save(messageB);
            Message messageC = new Message("세 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
            messageRepository.save(messageC);
            Message messageD = new Message("네 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
            messageRepository.save(messageD);
            Message messageE = new Message("다섯 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
            messageRepository.save(messageE);
            Message messageG = new Message("일곱 번째 메세지 전송입니다.", testMemberA, testMemberC, false, false, false);
            messageRepository.save(messageG);
        }
    }
}