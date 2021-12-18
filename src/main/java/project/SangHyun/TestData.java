package project.SangHyun;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TestData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initMember();
        initService.initStudy();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final StudyArticleRepository studyArticleRepository;
        private final MemberRepository memberRepository;
        private final StudyRepository studyRepository;
        private final PasswordEncoder passwordEncoder;

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
}
