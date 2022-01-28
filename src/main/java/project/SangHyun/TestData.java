package project.SangHyun;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.*;
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
import java.util.Arrays;
import java.util.stream.Collectors;

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
        private final MemberRepository memberRepository;
        private final StudyRepository studyRepository;
        private final StudyArticleRepository studyArticleRepository;
        private final StudyJoinRepository studyJoinRepository;
        private final PasswordEncoder passwordEncoder;

        private void initMember() {
            Member memberA = new Member("xptmxm1!", passwordEncoder.encode("xptmxm1!"), "승범", "컴퓨터공학부", "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong1.jpg", MemberRole.ROLE_MEMBER);
            memberRepository.save(memberA);

            Member memberB = new Member("xptmxm2!", passwordEncoder.encode("xptmxm2!"), "유나", "컴퓨터공학부", "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong2.jpg", MemberRole.ROLE_NOT_PERMITTED);
            memberRepository.save(memberB);
        }

        private void initStudy() {
            Member member = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", "컴퓨터공학부", "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong3.jpg", MemberRole.ROLE_MEMBER);
            memberRepository.save(member);

            Member memberB = new Member("xptmxm4!", passwordEncoder.encode("xptmxm5!"), "은둔", "컴퓨터공학부", "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong4.jpg", MemberRole.ROLE_MEMBER);
            memberRepository.save(memberB);

            Study study = makeStudy(member, "백엔드 모집", "백엔드 모집합니다.", makeTags("백엔드", "JPA", "스프링"));
            makeTestDummyStudy(member);

            StudyJoin studyJoin2 = new StudyJoin(memberB, "빠르게 지원합니다.", study, StudyRole.APPLY);
            studyJoinRepository.save(studyJoin2);
        }

        private Study makeStudy(Member member, String title, String content, Tags tags) {
            Study study = new Study(title, tags, content, "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/StudyDefaultImg.png", "cse", new StudyOptions(StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE), 3L, new Schedule("2021-10-01", "2021-12-25"), member, new ArrayList<>(), new ArrayList<>());

            StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
            StudyBoard studyBoard2 = new StudyBoard("자유게시판", study);
            StudyBoard studyBoard3 = new StudyBoard("알고리즘", study);

            study.addBoard(studyBoard1);
            study.addBoard(studyBoard2);
            study.addBoard(studyBoard3);

            studyRepository.save(study);

            StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", 0L, member, studyBoard1);
            StudyArticle studyArticle2 = new StudyArticle("자유게시판 테스트 글", "자유게시판 테스트 글입니다.", 0L, member, studyBoard1);
            StudyArticle studyArticle3 = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", 0L, member, studyBoard1);

            studyArticleRepository.save(studyArticle1);
            studyArticleRepository.save(studyArticle2);
            studyArticleRepository.save(studyArticle3);

            StudyJoin studyJoin = new StudyJoin(member, null, study, StudyRole.CREATOR);
            studyJoinRepository.save(studyJoin);

            return study;
        }

        private Tags makeTags(String ... tagNames) {
            return new Tags(Arrays.stream(tagNames)
                    .map(tagName -> new Tag(tagName))
                    .collect(Collectors.toList()));
        }

        private void makeTestDummyStudy(Member member) {
            for (int i = 0; i < 50; i++) {
                if (i % 2 == 0)
                    makeStudy(member, "백엔드 모집"+i, "백엔드 모집합니다.", makeTags("백엔드", "JPA", "스프링"));
                else
                    makeStudy(member, "프론트엔드 모집"+i, "프론트엔드 모집합니다.", makeTags("프론트엔드", "REACT", "JS"));
            }
        }
    }
}
