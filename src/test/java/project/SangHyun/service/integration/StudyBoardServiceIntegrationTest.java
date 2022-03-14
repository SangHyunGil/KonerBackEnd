package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.factory.studyboard.StudyBoardFactory;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.service.dto.response.StudyBoardDto;

import java.util.List;

class StudyBoardServiceIntegrationTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("스터디에 속한 게시판을 모두 로드한다.")
    public void loadBoard() throws Exception {
        //given

        //when
        List<StudyBoardDto> ActualResult = studyBoardService.findAllBoards(backendStudy.getId());

        //then
        Assertions.assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when
        StudyBoardDto ActualResult = studyBoardService.createBoard(backendStudy.getId(), requestDto.toServiceDto());

        //then
        Assertions.assertEquals("테스트 게시판", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("테스트 게시판 수정");

        //when
        StudyBoardDto ActualResult = studyBoardService.updateBoard(announceBoard.getId(), requestDto.toServiceDto());

        //then
        Assertions.assertEquals("테스트 게시판 수정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 삭제한다.")
    public void deleteBoard() throws Exception {
        //given

        //when
        studyBoardService.deleteBoard(announceBoard.getId());

        //then
        Assertions.assertEquals(2, studyBoardService.findAllBoards(backendStudy.getId()).size());
    }
}