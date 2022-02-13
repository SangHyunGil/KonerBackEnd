package project.SangHyun.study.study.service.integration;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.common.dto.response.SliceResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.study.service.dto.request.StudyCreateDto;
import project.SangHyun.study.study.service.dto.response.StudyDto;
import project.SangHyun.study.study.service.dto.request.StudyUpdateDto;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.study.study.tools.StudyFactory;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

import javax.persistence.EntityManager;
import java.util.List;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyServiceIntegrationTest {
    @Autowired
    StudyService studyService;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    TestDB testDB;
    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() {
        testDB.init();
        persistContextClear();
    }

    @Test
    @DisplayName("스터디를 생성한다.")
    public void createStudy() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        StudyCreateDto requestDto = StudyFactory.makeCreateDto(member);

        //when
        StudyDto ActualResult = studyService.createStudy(requestDto);
        Study study = studyRepository.findStudyByTitle("프론트엔드 모집").get(0);

        //then
        Assertions.assertEquals(study.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("모든 스터디 정보를 로드한다.")
    public void loadStudyInfo() throws Exception {
        //given

        //when
        SliceResponseDto ActualResult = studyService.findAllStudiesByDepartment(Long.MAX_VALUE, StudyCategory.CSE, 2);

        //then
        Assertions.assertEquals(2, ActualResult.getNumberOfElements());
        Assertions.assertEquals(2, ActualResult.getData().size());
        Assertions.assertEquals(false, ActualResult.isHasNext());
    }

    @Test
    @DisplayName("스터디에 대한 세부정보를 로드한다.")
    public void loadStudyDetail() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();

        //when
        StudyDto ActualResult = studyService.findStudy(study.getId());

        //then
        Assertions.assertEquals(study.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("스터디의 정보를 업데이트한다.")
    public void updateStudy() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyUpdateDto updateRequestDto = StudyFactory.makeUpdateDto("프론트엔드 스터디", List.of("프론트엔드"));

        //when
        StudyDto ActualResult = studyService.updateStudy(study.getId(), updateRequestDto);

        //then
        Assertions.assertEquals("프론트엔드 스터디", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디의 정보를 삭제한다.")
    public void deleteStudy() {
        //given
        Study study = testDB.findBackEndStudy();

        //when
        studyService.deleteStudy(study.getId());

        //then
        Assertions.assertEquals(1, studyRepository.findAll().size());
    }

    private void persistContextClear() {
        em.flush();
        em.clear();
    }
}