package project.SangHyun.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;

import java.util.ArrayList;
import java.util.List;

class StudyRepositoryTest extends RepositoryTest {

    @BeforeEach
    void beforeEach() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "상현입니다.");
        memberRepository.save(memberA);

        for (int i = 0; i < 10; i++) {
            Study study = new Study("백엔드 모집"+i, List.of("백엔드", "스프링", "JPA"), "백엔드 모집합니다.", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png",
                    2L, "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, memberA, new ArrayList<>(), new ArrayList<>());
            studyRepository.save(study);
        }
        for (int i = 0; i < 4; i++) {
            Study study = new Study("역학 스터디 모집"+i, List.of("역학"), "역학 스터디 모집합니다.", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png",
                    2L, "2021-10-01", "2021-12-25", StudyCategory.ME, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, memberA, new ArrayList<>(), new ArrayList<>());
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
    @DisplayName("컴퓨터공학과 스터디를 6개씩 Slice로 조회하기에 뒤에 4개가 남아있다.")
    public void findAllStudyBySlice() throws Exception {
        //given
        Long lastStudyId = Long.MAX_VALUE;
        Pageable pageable = Pageable.ofSize(6);

        //when
        Slice<Study> studies = studyRepository.findAllOrderByStudyIdDesc(lastStudyId, StudyCategory.CSE, pageable);

        //then
        Assertions.assertEquals(6, studies.getNumberOfElements());
        Assertions.assertEquals(true, studies.hasNext());
    }

    @Test
    @DisplayName("컴퓨터공학과 스터디를 6개씩 Slice로 조회하지만 4개만 남아있기에 4개가 조회되고 뒤에 남아있지 않다.")
    public void findAllStudyBySlice2() throws Exception {
        //given
        Long lastStudyId = studyRepository.findStudyByTitle("백엔드 모집4").get(0).getId();
        Pageable pageable = Pageable.ofSize(6);

        //when
        Slice<Study> studies = studyRepository.findAllOrderByStudyIdDesc(lastStudyId,StudyCategory.CSE, pageable);

        //then
        Assertions.assertEquals(4, studies.getNumberOfElements());
        Assertions.assertEquals(false, studies.hasNext());
    }

    @Test
    @DisplayName("기계공학과 스터디를 6개씩 Slice로 조회하지만 4개만 남아있기에 4개가 조회되고 뒤에 남아있지 않다.")
    public void findAllStudyBySlice3() throws Exception {
        //given
        Long lastStudyId = Long.MAX_VALUE;
        Pageable pageable = Pageable.ofSize(6);

        //when
        Slice<Study> studies = studyRepository.findAllOrderByStudyIdDesc(lastStudyId, StudyCategory.ME, pageable);

        //then
        Assertions.assertEquals(4, studies.getNumberOfElements());
        Assertions.assertEquals(false, studies.hasNext());
    }

    @Test
    @DisplayName("태그 요소 중 하나라도 변경되면 전체 삭제 후 다시 입력된다.")
    public void tagTest() throws Exception {
        persistContextClear();


    }

    private void persistContextClear() {
        em.clear();
        em.flush();
    }
}