package project.SangHyun;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
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
        private final MemberRepository memberRepository;
        private final StudyRepository studyRepository;
        private final StudyArticleRepository studyArticleRepository;
        private final StudyJoinRepository studyJoinRepository;
        private final PasswordEncoder passwordEncoder;

        private void initMember() {
            Member memberA = new Member("xptmxm1!", passwordEncoder.encode("xptmxm1!"), "??????", Department.CSE, "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong1.jpg", MemberRole.ROLE_MEMBER, "???????????????.");
            memberRepository.save(memberA);

            Member memberB = new Member("xptmxm2!", passwordEncoder.encode("xptmxm2!"), "??????", Department.CSE, "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong2.jpg", MemberRole.ROLE_NOT_PERMITTED, "???????????????.");
            memberRepository.save(memberB);
        }

        private void initStudy() {
            Member member = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "??????", Department.CSE, "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong3.jpg", MemberRole.ROLE_MEMBER, "???????????????.");
            memberRepository.save(member);

            Member memberB = new Member("xptmxm4!", passwordEncoder.encode("xptmxm5!"), "??????", Department.CSE, "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong4.jpg", MemberRole.ROLE_MEMBER, "?????? ?????? ????????????.");
            memberRepository.save(memberB);

            Study study = makeStudy(member, "????????? ??????", "????????? ???????????????.", List.of("?????????", "JPA", "?????????"));
            makeTestDummyStudy(member);

            StudyJoin studyJoin2 = new StudyJoin(memberB, "????????? ???????????????.", study, StudyRole.APPLY);
            studyJoinRepository.save(studyJoin2);
        }

        private Study makeStudy(Member member, String title, String description, List<String> tags) {
            Study study = new Study(title, tags, description, "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/logo/KakaoTalk_20220128_143615435.png", 3L, "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, member, new ArrayList<>(), new ArrayList<>());

            StudyBoard studyBoard1 = new StudyBoard("????????????", study);
            StudyBoard studyBoard2 = new StudyBoard("???????????????", study);
            StudyBoard studyBoard3 = new StudyBoard("????????????", study);

            study.addBoard(studyBoard1);
            study.addBoard(studyBoard2);
            study.addBoard(studyBoard3);

            studyRepository.save(study);

            StudyArticle studyArticle1 = new StudyArticle("???????????? ????????? ???", "???????????? ????????? ????????????.", 0L, member, studyBoard1);
            StudyArticle studyArticle2 = new StudyArticle("??????????????? ????????? ???", "??????????????? ????????? ????????????.", 0L, member, studyBoard1);
            StudyArticle studyArticle3 = new StudyArticle("???????????? ????????? ???", "???????????? ????????? ????????????.", 0L, member, studyBoard1);

            studyArticleRepository.save(studyArticle1);
            studyArticleRepository.save(studyArticle2);
            studyArticleRepository.save(studyArticle3);

            StudyJoin studyJoin = new StudyJoin(member, "?????????", study, StudyRole.CREATOR);
            studyJoinRepository.save(studyJoin);

            return study;
        }

        private void makeTestDummyStudy(Member member) {
            for (int i = 0; i < 50; i++) {
                if (i % 2 == 0)
                    makeStudy(member, "????????? ??????"+i, "????????? ???????????????.", List.of("?????????", "JPA", "?????????"));
                else
                    makeStudy(member, "??????????????? ??????"+i, "??????????????? ???????????????.", List.of("???????????????", "REACT", "JS"));
            }
        }
    }
}
