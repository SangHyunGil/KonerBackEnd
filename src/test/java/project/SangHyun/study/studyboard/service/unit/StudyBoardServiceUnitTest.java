package project.SangHyun.study.studyboard.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import project.SangHyun.study.studyboard.tools.StudyBoardFactory;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class StudyBoardServiceUnitTest {
    Member member;
    Study study;
    StudyBoard studyBoard;

    @InjectMocks
    StudyBoardServiceImpl studyBoardService;
    @Mock
    StudyBoardRepository studyBoardRepository;

    @BeforeEach
    public void init() {
        member = StudyBoardFactory.makeTestAuthMember();
        study = StudyBoardFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyBoard = StudyBoardFactory.makeTestStudyBoard(study);
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 모두 로드한다.")
    public void loadBoard() throws Exception {
        //given

        //mocking
        given(studyBoardRepository.findBoards(study.getId())).willReturn(List.of(studyBoard));

        //when
        List<StudyBoard> ActualResult = studyBoardRepository.findBoards(study.getId());

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateDto();
        StudyBoard createdStudyBoard = requestDto.toEntity(study.getId());
        StudyBoardCreateResponseDto ExpectResult = StudyBoardCreateResponseDto.create(createdStudyBoard);

        //mocking
        given(studyBoardRepository.save(any())).willReturn(createdStudyBoard);

        //when
        StudyBoardCreateResponseDto ActualResult = studyBoardService.createBoard(study.getId(), requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getStudyBoardId(), ActualResult.getStudyBoardId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        StudyBoardUpdateRequestDto updateRequestDto = StudyBoardFactory.makeUpdateDto("테스트 게시판 수정");

        //mocking
        given(studyBoardRepository.findById(study.getId())).willReturn(Optional.ofNullable(studyBoard));

        //when
        StudyBoardUpdateResponseDto ActualResult = studyBoardService.updateBoard(studyBoard.getId(), study.getId(), updateRequestDto);

        //then
        Assertions.assertEquals(studyBoard.getId(), ActualResult.getStudyBoardId());
        Assertions.assertEquals("테스트 게시판 수정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 삭제한다.")
    public void deleteBoard() throws Exception {
        //given

        //mocking
        given(studyBoardRepository.findById(study.getId())).willReturn(Optional.ofNullable(studyBoard));
        willDoNothing().given(studyBoardRepository).delete(studyBoard);

        //when
        StudyBoardDeleteResponseDto ActualResult = studyBoardService.deleteBoard(studyBoard.getId(), study.getId());

        //then
        Assertions.assertEquals(studyBoard.getId(), ActualResult.getStudyBoardId());
        Assertions.assertEquals("테스트 게시판", ActualResult.getTitle());
    }
}