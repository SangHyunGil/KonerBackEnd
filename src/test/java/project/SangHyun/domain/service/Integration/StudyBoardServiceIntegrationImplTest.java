package project.SangHyun.domain.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.repository.StudyBoardRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.Impl.StudyBoardServiceImpl;
import project.SangHyun.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.StudyBoardUpdateResponseDto;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyBoardServiceIntegrationImplTest {

    @Autowired
    StudyBoardServiceImpl studyBoardService;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    public void 스터디_게시판_작성() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoardCreateRequestDto requestDto = new StudyBoardCreateRequestDto("테스트 게시판");

        //when
        StudyBoardCreateResponseDto ActualResult = studyBoardService.createBoard(study.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 게시판", ActualResult.getTitle());
    }

    @Test
    public void 스터디_게시판_수정() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyBoardUpdateRequestDto requestDto = new StudyBoardUpdateRequestDto("테스트 게시판 수정");

        //when
        StudyBoardUpdateResponseDto ActualResult = studyBoardService.updateBoard(studyBoard.getId(), study.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 게시판 수정", ActualResult.getTitle());

    }

}