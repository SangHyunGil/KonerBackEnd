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
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.repository.StudyBoardRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.service.Impl.StudyBoardServiceImpl;
import project.SangHyun.dto.request.study.StudyBoardCreateRequestDto;
import project.SangHyun.dto.request.study.StudyBoardUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.study.StudyBoardDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyBoardUpdateResponseDto;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class StudyBoardServiceUnitImplTest {

    @InjectMocks
    StudyBoardServiceImpl studyBoardService;
    @Mock
    StudyBoardRepository studyBoardRepository;
    @Mock
    StudyJoinRepository studyJoinRepository;

    @Test
    public void 스터디_글_작성() throws Exception {
        //given
        StudyBoardCreateRequestDto requestDto = new StudyBoardCreateRequestDto("테스트 게시판");

        Long studyId = 1L;
        Long studyBoardId = 1L;
        StudyBoard studyBoard = requestDto.toEntity(studyId);
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);

        StudyBoardCreateResponseDto ExpectResult = StudyBoardCreateResponseDto.createDto(studyBoard);

        //mocking
        given(studyBoardRepository.save(any())).willReturn(studyBoard);

        //when
        StudyBoardCreateResponseDto ActualResult = studyBoardService.createBoard(studyId, requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getStudyBoardId(), ActualResult.getStudyBoardId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
    }

    @Test
    public void 스터디_글_수정() throws Exception {
        //given
        Long memberId = 1L;
        Long studyId = 1L;
        Long studyBoardId = 1L;
        StudyJoin studyJoin = new StudyJoin(new Member(1L), new Study(1L), StudyRole.ADMIN);
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", new Study(studyId));
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);

        StudyBoardUpdateRequestDto updateRequestDto = new StudyBoardUpdateRequestDto("테스트 게시판 수정");

        //mocking
        given(studyJoinRepository.findByMemberIdAndStudyId(memberId, studyId)).willReturn(Optional.ofNullable(studyJoin));
        given(studyBoardRepository.findById(studyId)).willReturn(Optional.ofNullable(studyBoard));

        //when
        StudyBoardUpdateResponseDto ActualResult = studyBoardService.updateBoard(memberId, studyBoardId, studyId, updateRequestDto);

        //then
        Assertions.assertEquals(studyBoardId, ActualResult.getStudyBoardId());
        Assertions.assertEquals("테스트 게시판 수정", ActualResult.getTitle());
    }

    @Test
    public void 스터디_글_삭제() throws Exception {
        //given
        Long memberId = 1L;
        Long studyId = 1L;
        Long studyBoardId = 1L;
        StudyJoin studyJoin = new StudyJoin(new Member(1L), new Study(1L), StudyRole.ADMIN);
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", new Study(studyId));
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);

        //mocking
        given(studyJoinRepository.findByMemberIdAndStudyId(memberId, studyId)).willReturn(Optional.ofNullable(studyJoin));
        given(studyBoardRepository.findById(studyId)).willReturn(Optional.ofNullable(studyBoard));
        willDoNothing().given(studyBoardRepository).delete(studyBoard);

        //when
        StudyBoardDeleteResponseDto ActualResult = studyBoardService.deleteBoard(memberId, studyBoardId, studyId);

        //then
        Assertions.assertEquals(studyBoardId, ActualResult.getStudyBoardId());
        Assertions.assertEquals("테스트 게시판", ActualResult.getTitle());
    }
}