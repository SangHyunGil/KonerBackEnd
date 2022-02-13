package project.SangHyun.study.studyboard.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.service.StudyBoardService;
import project.SangHyun.study.studyboard.service.dto.response.StudyBoardDto;
import project.SangHyun.study.studyboard.tools.StudyBoardFactory;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyBoardServiceIntegrationTest {
    @Autowired
    StudyBoardService studyBoardService;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 모두 로드한다.")
    public void loadBoard() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();

        //when
        List<StudyBoardDto> ActualResult = studyBoardService.findAllBoards(study.getId());

        //then
        Assertions.assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when
        StudyBoardDto ActualResult = studyBoardService.createBoard(study.getId(), requestDto.toServiceDto());

        //then
        Assertions.assertEquals("테스트 게시판", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("테스트 게시판 수정");

        //when
        StudyBoardDto ActualResult = studyBoardService.updateBoard(studyBoard.getId(), requestDto.toServiceDto());

        //then
        Assertions.assertEquals("테스트 게시판 수정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 삭제한다.")
    public void deleteBoard() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();

        //when
        studyBoardService.deleteBoard(studyBoard.getId());

        //then
        Assertions.assertEquals(2, studyBoardService.findAllBoards(study.getId()).size());
    }
}