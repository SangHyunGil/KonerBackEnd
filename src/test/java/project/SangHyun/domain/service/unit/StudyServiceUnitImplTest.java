package project.SangHyun.domain.service.unit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;
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


@ExtendWith(MockitoExtension.class)
class StudyServiceUnitImplTest {
    @InjectMocks
    StudyServiceImpl studyService;
    @Mock
    StudyRepository studyRepository;

    @Test
    public void 스터디_생성() throws Exception {
        //given
        Long memberId = 1L;
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(memberId, "테스트 스터디", "백엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        Long studyId = 1L;
        Study study = requestDto.toEntity();
        ReflectionTestUtils.setField(study, "id", studyId);

        StudyCreateResponseDto ExpectResult = StudyCreateResponseDto.createDto(study);

        //mocking
        given(studyRepository.save(any())).willReturn(study);

        //when
        StudyCreateResponseDto ActualResult = studyService.createStudy(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getStudyId(), ActualResult.getStudyId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(ExpectResult.getHeadCount(), 2L);
    }

    @Test
    public void 스터디_모두_찾기() throws Exception {
        //given
        Long memberId = 1L;
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(memberId, "테스트 스터디", "백엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        Long studyId = 1L;
        Study study = requestDto.toEntity();
        ReflectionTestUtils.setField(study, "id", studyId);

        List<StudyFindResponseDto> ExpectResult = Arrays.asList(StudyFindResponseDto.createDto(study));

        //mocking
        given(studyRepository.findAll()).willReturn(new ArrayList<>(Arrays.asList(study)));

        //when
        List<StudyFindResponseDto> ActualResult = studyService.findAllStudies();

        //then
        Assertions.assertEquals(ExpectResult.size(), ActualResult.size());
        Assertions.assertEquals(ExpectResult.get(0).getStudyId(), ExpectResult.get(0).getStudyId());
    }

    @Test
    public void 스터디_찾기() throws Exception {
        //given
        Long memberId = 1L;
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(memberId, "테스트 스터디", "백엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        Long studyId = 1L;
        Study study = requestDto.toEntity();
        ReflectionTestUtils.setField(study, "id", studyId);

        StudyFindResponseDto ExpectResult = StudyFindResponseDto.createDto(study);

        //mocking
        given(studyRepository.findStudyById(studyId)).willReturn(study);

        //when
        StudyFindResponseDto ActualResult = studyService.findStudy(studyId);

        //then
        Assertions.assertEquals(ExpectResult.getStudyId(), ActualResult.getStudyId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(ExpectResult.getHeadCount(), 2L);
    }

    @Test
    public void 스터디_업데이트() throws Exception {
        //given
        Long memberId = 1L;
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(memberId, "테스트 스터디", "백엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        Long studyId = 1L;
        Study study = requestDto.toEntity();
        ReflectionTestUtils.setField(study, "id", studyId);

        StudyUpdateRequestDto updateRequestDto = new StudyUpdateRequestDto(studyId, "테스트 스터디 변경", "프론트엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        //mocking
        given(studyRepository.findById(studyId)).willReturn(Optional.ofNullable(study));

        //when
        StudyUpdateResponseDto ActualResult = studyService.updateStudyInfo(updateRequestDto);

        //then
        Assertions.assertEquals("테스트 스터디 변경", ActualResult.getTitle());
        Assertions.assertEquals("프론트엔드", ActualResult.getTopic());
    }
}