package project.SangHyun;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.awt.print.Book;
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

            StudyJoin studyJoin = new StudyJoin(member, study, StudyRole.ADMIN);
            study.join(studyJoin);

            StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
            StudyBoard studyBoard2 = new StudyBoard("자유게시판", study);
            StudyBoard studyBoard3 = new StudyBoard("알고리즘", study);
            study.addBoard(studyBoard1);
            study.addBoard(studyBoard2);
            study.addBoard(studyBoard3);

            studyRepository.save(study);
        }
    }
}
