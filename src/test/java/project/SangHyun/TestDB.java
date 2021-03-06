package project.SangHyun;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.List;

@Component
public class TestDB {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    StudyCommentRepository studyCommentRepository;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    StudyScheduleRepository studyScheduleRepository;
    @Autowired
    VideoRoomRepository videoRoomRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void init() {
        initMember();
        initStudy();
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
        return studyRepository.findStudyByTitle("????????? ??????").get(0);
    }

    @Transactional(readOnly = true)
    public Study findZeroHeadCountStudy() {
        return studyRepository.findStudyByTitle("?????????").get(0);
    }

    @Transactional(readOnly = true)
    public StudyBoard findAnnounceBoard() {
        return studyRepository.findStudyByTitle("????????? ??????").get(0).getStudyBoards().get(0);
    }

    @Transactional(readOnly = true)
    public StudyArticle findAnnounceArticle() {
        return studyArticleRepository.findArticleByTitle("????????????").get(0);
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

    private void initMember() {
        Member memberA = new Member("xptmxm1!", passwordEncoder.encode("xptmxm1!"), "??????", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "???????????????.");
        memberRepository.save(memberA);

        Member memberB = new Member("xptmxm2!", passwordEncoder.encode("xptmxm2!"), "??????", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_NOT_PERMITTED, "???????????????.");
        memberRepository.save(memberB);

        Member memberC = new Member("xptmxm4!", passwordEncoder.encode("xptmxm4!"), "??????", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_ADMIN, "???????????????.");
        memberRepository.save(memberC);
    }

    private void initStudy() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "??????", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "???????????????.");
        Member storedMemberA = memberRepository.save(memberA);

        Member memberB = new Member("xptmxm5!", passwordEncoder.encode("xptmxm5!"), "??????", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "???????????????.");
        Member storedMemberB = memberRepository.save(memberB);

        Member memberC = new Member("xptmxm0!", passwordEncoder.encode("xptmxm0!"), "??????", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "???????????????.");
        Member storedMemberC = memberRepository.save(memberC);

        Member memberD = new Member("xptmxm10!", passwordEncoder.encode("xptmxm10!"), "??????", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "???????????????.");
        Member storedMemberD = memberRepository.save(memberD);

        Member memberE = new Member("xptmxm11!", passwordEncoder.encode("xptmxm11!"), "??????", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "???????????????.");
        Member storedMemberE = memberRepository.save(memberE);

        Study emptyStudy = new Study("?????????", List.of("?????????"), "?????????", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", 1L, "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, storedMemberB, new ArrayList<>(), new ArrayList<>());

        Study study = new Study("????????? ??????", List.of("?????????", "JPA", "?????????"), "????????? ???????????????.", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", 5L, "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, storedMemberA, new ArrayList<>(), new ArrayList<>());

        studyRepository.save(study);
        studyRepository.save(emptyStudy);

        StudyJoin studyJoin = new StudyJoin(storedMemberA, "", study, StudyRole.CREATOR);
        studyJoinRepository.save(studyJoin);

        StudyJoin studyJoin2 = new StudyJoin(storedMemberB, "????????? ???????????????.", study, StudyRole.ADMIN);
        studyJoinRepository.save(studyJoin2);

        StudyJoin studyJoin3 = new StudyJoin(storedMemberC, "????????? ???????????????.", study, StudyRole.MEMBER);
        studyJoinRepository.save(studyJoin3);

        StudyJoin studyJoin4 = new StudyJoin(storedMemberD, "????????? ???????????????.", study, StudyRole.APPLY);
        studyJoinRepository.save(studyJoin4);

        StudyJoin studyJoin5 = new StudyJoin(storedMemberE, "????????? ???????????????.", study, StudyRole.MEMBER);
        studyJoinRepository.save(studyJoin5);

        StudyJoin studyJoin6 = new StudyJoin(storedMemberA, "", emptyStudy, StudyRole.CREATOR);
        studyJoinRepository.save(studyJoin6);

        StudyBoard studyBoard1 = new StudyBoard("????????????", study);
        StudyBoard studyBoard2 = new StudyBoard("???????????????", study);
        StudyBoard studyBoard3 = new StudyBoard("????????????", study);

        StudyArticle studyArticle1 = new StudyArticle("???????????? ????????? ???", "???????????? ????????? ????????????.", 0L, storedMemberA, studyBoard1);
        StudyArticle studyArticle2 = new StudyArticle("??????????????? ????????? ???", "??????????????? ????????? ????????????.", 0L, storedMemberA, studyBoard1);
        StudyArticle studyArticle3 = new StudyArticle("???????????? ????????? ???", "???????????? ????????? ????????????.", 0L, storedMemberA, studyBoard1);

        StudyComment studyComment1 = new StudyComment(storedMemberC, studyArticle1, null, "???????????? ??????1?????????.", false);
        StudyComment studyComment2 = new StudyComment(storedMemberA, studyArticle1, studyComment1, "???????????? ??????2?????????.", false);

        studyCommentRepository.save(studyComment1);
        studyCommentRepository.save(studyComment2);

        studyArticleRepository.save(studyArticle1);
        studyArticleRepository.save(studyArticle2);
        studyArticleRepository.save(studyArticle3);

        study.addBoard(studyBoard1);
        study.addBoard(studyBoard2);
        study.addBoard(studyBoard3);

        StudySchedule studySchedule = new StudySchedule("JPA ?????????", "2021-09-10", "2022-09-10", "18:00", "22:00", study);
        studyScheduleRepository.save(studySchedule);

        VideoRoom videoRoom = new VideoRoom(1234L, "JPA ?????????", null, memberC, study);
        videoRoomRepository.save(videoRoom);

        // findStudyGeneralMember, findStudyApplyMember, findStudyMemberNotResourceOwner
        initMessage(storedMemberC, storedMemberD, storedMemberE);

        for (int i = 0; i < 5; i++) {
            Notification notification = new Notification(storedMemberD, NotificationType.ACCEPT, "??????"+i, "study/51", false);
            notificationRepository.save(notification);
        }
    }

    private void initMessage(Member testMemberA, Member testMemberB, Member testMemberC) {
        Message messageA = new Message("??? ?????? ????????? ???????????????.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageA);
        Message messageB = new Message("??? ?????? ????????? ???????????????.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageB);
        Message messageC = new Message("??? ?????? ????????? ???????????????.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageC);
        Message messageD = new Message("??? ?????? ????????? ???????????????.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageD);
        Message messageE = new Message("?????? ?????? ????????? ???????????????.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageE);
        Message messageG = new Message("?????? ?????? ????????? ???????????????.", testMemberA, testMemberC, false, false, false);
        messageRepository.save(messageG);
    }
}
