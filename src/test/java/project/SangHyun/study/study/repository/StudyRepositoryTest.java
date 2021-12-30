package project.SangHyun.study.study.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

import javax.persistence.EntityManager;
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
    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", "컴공", "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberA);

        for (int i = 0; i < 10; i++) {
            Study study = new Study("백엔드 모집"+i, List.of("백엔드", "스프링", "JPA"), "백엔드 모집합니다.", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", "컴퓨터공학과",
                    StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE, 2L, "2021-12-25", memberA, new ArrayList<>(), new ArrayList<>());
            studyRepository.save(study);
        }
    }

    @Test
    @DisplayName("제목으로 스터디를 찾는다. (성공)")
    public void findStudyByTitle_Success() throws Exception {
        //given

        //when
        List<Study> study = studyRepository.findStudyByTitle("백엔드");

        //then
        Assertions.assertEquals(10, study.size());
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

    @Test
    @DisplayName("6개씩 Slice로 조회하기에 뒤에 4개가 남아있다.")
    public void findAllStudyBySlice() throws Exception {
        //given
        Long lastStudyId = Long.MAX_VALUE;
        Pageable pageable = Pageable.ofSize(6);

        //when
        Slice<Study> studies = studyRepository.findAllOrderByStudyIdDesc(lastStudyId, pageable);

        //then
        Assertions.assertEquals(6, studies.getNumberOfElements());
        Assertions.assertEquals(true, studies.hasNext());
    }

    @Test
    @DisplayName("6개씩 Slice로 조회하지만 4개만 남아있기에 4개가 조회되고 뒤에 남아있지 않다.")
    public void findAllStudyBySlice2() throws Exception {
        //given
        Long lastStudyId = studyRepository.findStudyByTitle("백엔드 모집4").get(0).getId();
        Pageable pageable = Pageable.ofSize(6);
        persistContextClear();

        //when
        Slice<Study> studies = studyRepository.findAllOrderByStudyIdDesc(lastStudyId, pageable);

        //then
        Assertions.assertEquals(4, studies.getNumberOfElements());
        Assertions.assertEquals(false, studies.hasNext());
    }

    private void persistContextClear() {
        em.clear();
        em.flush();
    }
}