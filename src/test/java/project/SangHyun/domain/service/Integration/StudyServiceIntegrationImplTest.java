package project.SangHyun.domain.service.Integration;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.Impl.StudyServiceImpl;
import project.SangHyun.dto.request.StudyCreateRequestDto;
import project.SangHyun.dto.request.StudyUpdateRequestDto;
import project.SangHyun.dto.response.StudyCreateResponseDto;
import project.SangHyun.dto.response.StudyFindResponseDto;
import project.SangHyun.dto.response.StudyUpdateResponseDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyServiceIntegrationImplTest {
    @Autowired
    StudyServiceImpl studyService;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    public void 스터디_생성() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test").orElseThrow(MemberNotFoundException::new);
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(member.getId(), "테스트 스터디", "백엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        //when
        StudyCreateResponseDto ActualResult = studyService.createStudy(requestDto);
        Study study = studyRepository.findStudyByTitle("테스트").get(0);

        //then
        Assertions.assertEquals(study.getId(), ActualResult.getStudyId());
    }

    @Test
    public void 스터디_모두_찾기() throws Exception {
        //given

        //when
        List<StudyFindResponseDto> ActualResult = studyService.findAllStudies();

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }

    @Test
    public void 스터디_찾기() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when
        StudyFindResponseDto ActualResult = studyService.findStudy(study.getId());

        //then
        Assertions.assertEquals(study.getId(), ActualResult.getStudyId());
        Assertions.assertEquals(study.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(study.getTopic(), ActualResult.getTopic());
        Assertions.assertEquals(study.getContent(), ActualResult.getContent());
    }

    @Test
    public void 스터디_업데이트() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyUpdateRequestDto updateRequestDto = new StudyUpdateRequestDto(study.getId(), "프론트엔드 스터디", "프론트엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        //when
        StudyUpdateResponseDto ActualResult = studyService.updateStudyInfo(updateRequestDto);

        //then
        Assertions.assertEquals("프론트엔드 스터디", ActualResult.getTitle());
        Assertions.assertEquals("프론트엔드", ActualResult.getTopic());
    }
}