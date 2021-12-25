package project.SangHyun.study.study.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyMethod;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudyRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", "컴공", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberA);

        Study study = new Study("백엔드 모집", "백엔드", "백엔드 모집합니다.",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png",
                StudyState.STUDYING, RecruitState.PROCEED, 3L, "2021-12-25", StudyMethod.FACE, memberA, new ArrayList<>(), new ArrayList<>());
        studyRepository.save(study);
    }

    @Test
    @DisplayName("제목으로 스터디를 찾는다. (성공)")
    public void findStudyByTitle_Success() throws Exception {
        //given

        //when
        List<Study> study = studyRepository.findStudyByTitle("백엔드");

        //then
        Assertions.assertEquals(1, study.size());
    }

    @Test
    @DisplayName("제목으로 스터디를 찾는다. (실패)")
    public void findStudyByTitle_fail() throws Exception {
        //given

        //when
        List<Study> study = studyRepository.findStudyByTitle("프론트엔드");

        //then
        Assertions.assertEquals(0, study.size());
    }
}