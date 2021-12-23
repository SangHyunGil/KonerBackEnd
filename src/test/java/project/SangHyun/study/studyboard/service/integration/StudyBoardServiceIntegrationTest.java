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
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.advice.exception.StudyHasNoProperRoleException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyboard.service.impl.StudyBoardServiceImpl;
import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardDeleteResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardUpdateResponseDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyBoardServiceIntegrationTest {

    @Autowired
    StudyBoardServiceImpl studyBoardService;
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
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when
        List<StudyBoardFindResponseDto> ActualResult = studyBoardService.findAllBoards(study.getId());

        //then
        Assertions.assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoardCreateRequestDto requestDto = new StudyBoardCreateRequestDto("테스트 게시판");

        //when
        StudyBoardCreateResponseDto ActualResult = studyBoardService.createBoard(study.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 게시판", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyBoardUpdateRequestDto requestDto = new StudyBoardUpdateRequestDto("테스트 게시판 수정");

        //when
        StudyBoardUpdateResponseDto ActualResult = studyBoardService.updateBoard(study.getId(), studyBoard.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 게시판 수정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 삭제한다.")
    public void deleteBoard() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        //when
        StudyBoardDeleteResponseDto ActualResult = studyBoardService.deleteBoard(study.getId(), studyBoard.getId());

        //then
        Assertions.assertEquals(studyBoard.getId(), ActualResult.getStudyBoardId());
        Assertions.assertEquals(studyBoard.getTitle(), ActualResult.getTitle());
    }
}