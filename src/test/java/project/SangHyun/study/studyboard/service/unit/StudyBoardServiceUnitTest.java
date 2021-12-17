package project.SangHyun.study.studyboard.service.unit;

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
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.studyboard.repository.StudyBoardRepository;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyboard.service.impl.StudyBoardServiceImpl;
import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardDeleteResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardUpdateResponseDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class StudyBoardServiceUnitTest {

    @InjectMocks
    StudyBoardServiceImpl studyBoardService;
    @Mock
    StudyBoardRepository studyBoardRepository;
    @Mock
    StudyJoinRepository studyJoinRepository;

    @Test
    @DisplayName("스터디에 속한 게시판을 모두 로드한다.")
    public void loadBoard() throws Exception {
        //given
        Long studyId = 1L;

        List<StudyBoard> studyBoards = List.of(new StudyBoard(1L));

        //mocking
        given(studyBoardRepository.findBoards(studyId)).willReturn(studyBoards);

        //when
        List<StudyBoard> ActualResult = studyBoardRepository.findBoards(studyId);

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        StudyBoardCreateRequestDto requestDto = new StudyBoardCreateRequestDto("테스트 게시판");

        Long studyId = 1L;
        Long studyBoardId = 1L;
        StudyBoard studyBoard = requestDto.toEntity(studyId);
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);

        StudyBoardCreateResponseDto ExpectResult = StudyBoardCreateResponseDto.create(studyBoard);

        //mocking
        given(studyBoardRepository.save(any())).willReturn(studyBoard);

        //when
        StudyBoardCreateResponseDto ActualResult = studyBoardService.createBoard(studyId, requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getStudyBoardId(), ActualResult.getStudyBoardId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        Long memberId = 1L;
        Long studyId = 1L;
        Long studyBoardId = 1L;
        StudyJoin studyJoin = new StudyJoin(new Member(1L), new Study(1L), StudyRole.ADMIN);
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", new Study(studyId));
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);

        StudyBoardUpdateRequestDto updateRequestDto = new StudyBoardUpdateRequestDto("테스트 게시판 수정");

        //mocking
        given(studyBoardRepository.findById(studyId)).willReturn(Optional.ofNullable(studyBoard));

        //when
        StudyBoardUpdateResponseDto ActualResult = studyBoardService.updateBoard(studyBoardId, studyId, updateRequestDto);

        //then
        Assertions.assertEquals(studyBoardId, ActualResult.getStudyBoardId());
        Assertions.assertEquals("테스트 게시판 수정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 삭제한다.")
    public void deleteBoard() throws Exception {
        //given
        Long memberId = 1L;
        Long studyId = 1L;
        Long studyBoardId = 1L;
        StudyJoin studyJoin = new StudyJoin(new Member(1L), new Study(1L), StudyRole.ADMIN);
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", new Study(studyId));
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);

        //mocking
        given(studyBoardRepository.findById(studyId)).willReturn(Optional.ofNullable(studyBoard));
        willDoNothing().given(studyBoardRepository).delete(studyBoard);

        //when
        StudyBoardDeleteResponseDto ActualResult = studyBoardService.deleteBoard(studyBoardId, studyId);

        //then
        Assertions.assertEquals(studyBoardId, ActualResult.getStudyBoardId());
        Assertions.assertEquals("테스트 게시판", ActualResult.getTitle());
    }
}