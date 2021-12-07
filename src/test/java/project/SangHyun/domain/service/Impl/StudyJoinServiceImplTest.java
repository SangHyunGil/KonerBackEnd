package project.SangHyun.domain.service.Impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.dto.request.StudyCreateRequestDto;
import project.SangHyun.dto.request.StudyJoinRequestDto;
import project.SangHyun.dto.response.StudyCreateResponseDto;
import project.SangHyun.dto.response.StudyJoinResponseDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudyJoinServiceImplTest {

    @InjectMocks
    StudyJoinServiceImpl studyJoinService;
    @Mock
    StudyJoinRepository studyJoinRepository;

    @Test
    public void 스터디_참여() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;
        Long studyJoinId = 1L;
        StudyJoinRequestDto requestDto = new StudyJoinRequestDto(studyId, memberId);

        StudyJoin studyJoin = requestDto.toEntity();
        ReflectionTestUtils.setField(studyJoin, "id", studyJoinId);

        StudyJoinResponseDto ExpectResult = StudyJoinResponseDto.createDto(studyJoin);

        //mocking
        given(studyJoinRepository.save(any())).willReturn(studyJoin);

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.join(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getStudyJoinId(), ActualResult.getStudyJoinId());
    }

}