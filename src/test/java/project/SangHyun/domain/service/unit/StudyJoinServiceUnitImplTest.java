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
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.Impl.StudyJoinServiceImpl;
import project.SangHyun.dto.request.study.StudyJoinRequestDto;
import project.SangHyun.dto.response.study.StudyJoinResponseDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudyJoinServiceUnitImplTest {

    @InjectMocks
    StudyJoinServiceImpl studyJoinService;
    @Mock
    StudyJoinRepository studyJoinRepository;
    @Mock
    StudyRepository studyRepository;

    @Test
    public void 스터디_참여() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;
        StudyJoinRequestDto requestDto = new StudyJoinRequestDto(studyId, memberId);

        Long studyJoinId = 1L;
        StudyJoin studyJoin = requestDto.toEntity();
        ReflectionTestUtils.setField(studyJoin, "id", studyJoinId);

        Study study = new Study("테스트", "테스트", "테스트", StudyState.STUDYING, RecruitState.PROCEED, 2L, new Member(1L), List.of(studyJoin), List.of());
        ReflectionTestUtils.setField(study, "id", studyId);

        StudyJoinResponseDto ExpectResult = StudyJoinResponseDto.createDto(studyJoin);

        //mocking
        given(studyJoinRepository.save(any())).willReturn(studyJoin);
        given(studyRepository.findById(studyId)).willReturn(Optional.ofNullable(study));

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.join(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getStudyJoinId(), ActualResult.getStudyJoinId());
    }

}