package project.SangHyun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyMethod;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.ArrayList;

@Component
@Profile("test")
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
    public Member findAnotherGeneralMember() {
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

    private void initMember() {
        Member memberA = new Member("xptmxm1!", passwordEncoder.encode("xptmxm1!"), "승범", "컴퓨터공학부", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberA);

        Member memberB = new Member("xptmxm2!", passwordEncoder.encode("xptmxm2!"), "유나", "컴퓨터공학부", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_NOT_PERMITTED);
        memberRepository.save(memberB);

        Member memberC = new Member("xptmxm4!", passwordEncoder.encode("xptmxm4!"), "윤정", "컴퓨터공학부", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_ADMIN);
        memberRepository.save(memberC);
    }

    private void initStudy() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", "컴퓨터공학부", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberA);

        Member memberB = new Member("xptmxm5!", passwordEncoder.encode("xptmxm5!"), "진영", "컴퓨터공학부", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberB);

        Member memberC = new Member("xptmxm0!", passwordEncoder.encode("xptmxm0!"), "예림", "컴퓨터공학부", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberC);

        Member memberD = new Member("xptmxm10!", passwordEncoder.encode("xptmxm10!"), "동욱", "컴퓨터공학부", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberD);

        Member memberE = new Member("xptmxm11!", passwordEncoder.encode("xptmxm11!"), "영탁", "컴퓨터공학부", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberE);

        Study emptyStudy = new Study("임시용", "임시용", "임시용", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", StudyState.STUDYING, RecruitState.PROCEED, 0L, "2021-12-25", StudyMethod.FACE, memberB, new ArrayList<>(), new ArrayList<>());

        Study study = new Study("백엔드 모집", "백엔드", "백엔드 모집합니다.", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", StudyState.STUDYING, RecruitState.PROCEED, 5L, "2021-12-25", StudyMethod.FACE, memberA, new ArrayList<>(), new ArrayList<>());

        StudyJoin studyJoin = new StudyJoin(memberA, study, StudyRole.CREATOR);
        study.join(studyJoin);

        StudyJoin studyJoin2 = new StudyJoin(memberB, study, StudyRole.ADMIN);
        study.join(studyJoin2);

        StudyJoin studyJoin3 = new StudyJoin(memberC, study, StudyRole.MEMBER);
        study.join(studyJoin3);

        StudyJoin studyJoin4 = new StudyJoin(memberD, study, StudyRole.APPLY);
        study.join(studyJoin4);

        StudyJoin studyJoin5 = new StudyJoin(memberE, study, StudyRole.APPLY);
        study.join(studyJoin5);

        StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
        StudyBoard studyBoard2 = new StudyBoard("자유게시판", study);
        StudyBoard studyBoard3 = new StudyBoard("알고리즘", study);

        StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", 0L, memberA, studyBoard1);
        StudyArticle studyArticle2 = new StudyArticle("자유게시판 테스트 글", "자유게시판 테스트 글입니다.", 0L, memberA, studyBoard1);
        StudyArticle studyArticle3 = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", 0L, memberA, studyBoard1);

        StudyComment studyComment1 = new StudyComment(memberC, studyArticle1, null, "공지사항 댓글1입니다.", false);
        StudyComment studyComment2 = new StudyComment(memberA, studyArticle1, studyComment1, "공지사항 댓글2입니다.", false);

        studyCommentRepository.save(studyComment1);
        studyCommentRepository.save(studyComment2);

        studyArticleRepository.save(studyArticle1);
        studyArticleRepository.save(studyArticle2);
        studyArticleRepository.save(studyArticle3);

        study.addBoard(studyBoard1);
        study.addBoard(studyBoard2);
        study.addBoard(studyBoard3);

        studyRepository.save(study);
        studyRepository.save(emptyStudy);
    }
}
