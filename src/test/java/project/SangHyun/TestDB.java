package project.SangHyun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.domain.entity.*;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyArticleRepository;
import project.SangHyun.domain.repository.StudyRepository;

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
    PasswordEncoder passwordEncoder;

    @Transactional
    public void init() {
        initMember();
        initStudy();
    }


    private void initMember() {
        Member memberA = new Member("xptmxm1!", passwordEncoder.encode("xptmxm1!"), "승범", "컴공", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberA);

        Member memberB = new Member("xptmxm2!", passwordEncoder.encode("xptmxm2!"), "유나", "컴공", MemberRole.ROLE_NOT_PERMITTED);
        memberRepository.save(memberB);
    }

    private void initStudy() {
        Member member = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", "컴공", MemberRole.ROLE_MEMBER);
        memberRepository.save(member);

        Study study = new Study("백엔드 모집", "백엔드", "백엔드 모집합니다.", StudyState.STUDYING, RecruitState.PROCEED, 3L, member, new ArrayList<>(), new ArrayList<>());

        StudyJoin studyJoin = new StudyJoin(member, study, StudyRole.CREATOR);
        study.join(studyJoin);

        StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
        StudyBoard studyBoard2 = new StudyBoard("자유게시판", study);
        StudyBoard studyBoard3 = new StudyBoard("알고리즘", study);
        study.addBoard(studyBoard1);
        study.addBoard(studyBoard2);
        study.addBoard(studyBoard3);

        studyRepository.save(study);

        StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", member, studyBoard1);
        StudyArticle studyArticle2 = new StudyArticle("자유게시판 테스트 글", "자유게시판 테스트 글입니다.", member, studyBoard1);
        StudyArticle studyArticle3 = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", member, studyBoard1);

        studyArticleRepository.save(studyArticle1);
        studyArticleRepository.save(studyArticle2);
        studyArticleRepository.save(studyArticle3);
    }
}
