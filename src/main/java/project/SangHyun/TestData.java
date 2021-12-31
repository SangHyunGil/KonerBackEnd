package project.SangHyun;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Schedule;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyOptions;
import project.SangHyun.study.study.domain.enums.RecruitState;
import project.SangHyun.study.study.domain.enums.StudyMethod;
import project.SangHyun.study.study.domain.enums.StudyRole;
import project.SangHyun.study.study.domain.enums.StudyState;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
        @Value("${spring.file.dir}/")
        private String filePath;
        private final MemberRepository memberRepository;
        private final StudyRepository studyRepository;
        private final StudyArticleRepository studyArticleRepository;
        private final StudyJoinRepository studyJoinRepository;
        private final PasswordEncoder passwordEncoder;

        private void initMember() {
            Member memberA = new Member("xptmxm1!", passwordEncoder.encode("xptmxm1!"), "승범", "컴공", "/defaultImg2.png", MemberRole.ROLE_MEMBER);
            memberRepository.save(memberA);

            Member memberB = new Member("xptmxm2!", passwordEncoder.encode("xptmxm2!"), "유나", "컴공", "/defaultImg.png", MemberRole.ROLE_NOT_PERMITTED);
            memberRepository.save(memberB);
        }

        private void initStudy() {
            Member member = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", "컴공", "/defaultImg.png", MemberRole.ROLE_MEMBER);
            memberRepository.save(member);

            Member memberB = new Member("xptmxm4!", passwordEncoder.encode("xptmxm5!"), "은둔", "컴공", "/defaultImg.png", MemberRole.ROLE_MEMBER);
            memberRepository.save(memberB);

            Study study = new Study("백엔드 모집", List.of("백엔드"), "백엔드 모집합니다.", filePath+"\\defaultImg2.png", "컴퓨터공학과", new StudyOptions(StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE), 3L, new Schedule("2021-10-01", "2021-12-25"), member, new ArrayList<>(), new ArrayList<>());

            StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
            StudyBoard studyBoard2 = new StudyBoard("자유게시판", study);
            StudyBoard studyBoard3 = new StudyBoard("알고리즘", study);

            study.addBoard(studyBoard1);
            study.addBoard(studyBoard2);
            study.addBoard(studyBoard3);

            studyRepository.save(study);

            StudyJoin studyJoin = new StudyJoin(member, null, study, StudyRole.CREATOR);
            studyJoinRepository.save(studyJoin);

            StudyJoin studyJoin2 = new StudyJoin(memberB, "빠르게 지원합니다.", study, StudyRole.APPLY);
            studyJoinRepository.save(studyJoin);

            StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", 0L, member, studyBoard1);
            StudyArticle studyArticle2 = new StudyArticle("자유게시판 테스트 글", "자유게시판 테스트 글입니다.", 0L, member, studyBoard1);
            StudyArticle studyArticle3 = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", 0L, member, studyBoard1);

            studyArticleRepository.save(studyArticle1);
            studyArticleRepository.save(studyArticle2);
            studyArticleRepository.save(studyArticle3);
        }
    }
}
