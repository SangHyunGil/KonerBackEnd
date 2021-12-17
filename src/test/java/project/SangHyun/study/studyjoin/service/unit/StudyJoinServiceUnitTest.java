package project.SangHyun.study.studyjoin.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyjoin.service.impl.StudyJoinServiceImpl;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudyJoinServiceUnitTest {

    @InjectMocks
    StudyJoinServiceImpl studyJoinService;
    @Mock
    StudyJoinRepository studyJoinRepository;
    @Mock
    StudyRepository studyRepository;

    @Test
    @DisplayName("스터디에 참여한다.")
    public void join() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;
        StudyJoinRequestDto requestDto = new StudyJoinRequestDto(studyId, memberId);

        Long studyJoinId = 1L;
        StudyJoin studyJoin = requestDto.toEntity();
        ReflectionTestUtils.setField(studyJoin, "id", studyJoinId);

        Study study = new Study("테스트", "테스트", "테스트", StudyState.STUDYING, RecruitState.PROCEED, 2L, new Member(1L), List.of(studyJoin), List.of());
        ReflectionTestUtils.setField(study, "id", studyId);

        StudyJoinResponseDto ExpectResult = StudyJoinResponseDto.create(studyJoin);

        //mocking
        given(studyJoinRepository.save(any())).willReturn(studyJoin);
        given(studyRepository.findById(studyId)).willReturn(Optional.ofNullable(study));

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.joinStudy(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getStudyJoinId(), ActualResult.getStudyJoinId());
    }

}