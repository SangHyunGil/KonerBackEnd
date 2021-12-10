package project.SangHyun.domain.service.unit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.Impl.StudyServiceImpl;
import project.SangHyun.dto.request.study.StudyCreateRequestDto;
import project.SangHyun.dto.request.study.StudyUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyCreateResponseDto;
import project.SangHyun.dto.response.study.StudyDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyFindResponseDto;
import project.SangHyun.dto.response.study.StudyUpdateResponseDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;


@ExtendWith(MockitoExtension.class)
class StudyServiceUnitImplTest {
    @InjectMocks
    StudyServiceImpl studyService;
    @Mock
    StudyRepository studyRepository;
    @Mock
    StudyJoinRepository studyJoinRepository;

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

        StudyJoin studyJoin = new StudyJoin(new Member(memberId), new Study(studyId), StudyRole.CREATOR);

        StudyUpdateRequestDto updateRequestDto = new StudyUpdateRequestDto("테스트 스터디 변경", "프론트엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);

        //mocking
        given(studyRepository.findById(studyId)).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.findByMemberIdAndStudyId(memberId, studyId)).willReturn(Optional.ofNullable(studyJoin));

        //when
        StudyUpdateResponseDto ActualResult = studyService.updateStudyInfo(memberId, studyId, updateRequestDto);

        //then
        Assertions.assertEquals("테스트 스터디 변경", ActualResult.getTitle());
        Assertions.assertEquals("프론트엔드", ActualResult.getTopic());
    }

    @Test
    public void 스터디_삭제() throws Exception {
        //given
        Long memberId = 1L;
        Long studyId = 1L;
        Study study = new Study(1L);
        ReflectionTestUtils.setField(study, "id", studyId);

        StudyJoin studyJoin = new StudyJoin(new Member(memberId), new Study(studyId), StudyRole.CREATOR);

        StudyDeleteResponseDto ExpectResult = StudyDeleteResponseDto.createDto(study);

        //mocking
        given(studyRepository.findById(studyId)).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.findByMemberIdAndStudyId(memberId, studyId)).willReturn(Optional.ofNullable(studyJoin));
        willDoNothing().given(studyRepository).delete(study);

        //when
        StudyDeleteResponseDto ActualResult = studyService.deleteStudy(memberId, studyId);

        //then
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(ExpectResult.getStudyId(), ActualResult.getStudyId());
    }
}