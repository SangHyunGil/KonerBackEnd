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
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.advice.exception.StudyHasNoProperRoleException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.response.StudyDeleteResponseDto;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.study.service.impl.StudyServiceImpl;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.dto.response.StudyUpdateResponseDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyServiceIntegrationTest {
    @Autowired
    StudyServiceImpl studyService;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("스터디를 생성한다.")
    public void createStudy() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(member.getId(), "테스트 스터디", "백엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        //when
        StudyCreateResponseDto ActualResult = studyService.createStudy(requestDto);
        Study study = studyRepository.findStudyByTitle("테스트").get(0);

        //then
        Assertions.assertEquals(study.getId(), ActualResult.getStudyId());
    }

    @Test
    @DisplayName("모든 스터디 정보를 로드한다.")
    public void loadStudyInfo() throws Exception {
        //given

        //when
        List<StudyFindResponseDto> ActualResult = studyService.findAllStudies();

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 대한 세부정보를 로드한다.")
    public void loadStudyDetail() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when
        StudyFindResponseDto ActualResult = studyService.findStudy(study.getId());

        //then
        Assertions.assertEquals(study.getId(), ActualResult.getStudyId());
    }

    @Test
    @DisplayName("스터디의 정보를 업데이트한다.")
    public void updateStudy() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyUpdateRequestDto updateRequestDto = new StudyUpdateRequestDto("프론트엔드 스터디", "프론트엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        //when
        StudyUpdateResponseDto ActualResult = studyService.updateStudy(study.getId(), updateRequestDto);

        //then
        Assertions.assertEquals("프론트엔드 스터디", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디의 정보를 삭제한다.")
    public void deleteStudy() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when
        StudyDeleteResponseDto ActualResult = studyService.deleteStudy(study.getId());

        //then
        Assertions.assertEquals("백엔드 모집", ActualResult.getTitle());
    }
}