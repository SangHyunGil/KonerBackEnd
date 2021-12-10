package project.SangHyun.domain.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.advice.exception.StudyHasNoProperRoleException;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.Impl.StudyBoardServiceImpl;
import project.SangHyun.dto.request.study.StudyBoardCreateRequestDto;
import project.SangHyun.dto.request.study.StudyBoardUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.study.StudyBoardDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyBoardUpdateResponseDto;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyBoardServiceIntegrationImplTest {

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
    public void 스터디_게시판작성() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoardCreateRequestDto requestDto = new StudyBoardCreateRequestDto("테스트 게시판");

        //when
        StudyBoardCreateResponseDto ActualResult = studyBoardService.createBoard(study.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 게시판", ActualResult.getTitle());
    }

    @Test
    public void 스터디_게시판수정_권한O() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyBoardUpdateRequestDto requestDto = new StudyBoardUpdateRequestDto("테스트 게시판 수정");

        //when
        StudyBoardUpdateResponseDto ActualResult = studyBoardService.updateBoard(member.getId(), study.getId(), studyBoard.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 게시판 수정", ActualResult.getTitle());
    }

    @Test
    public void 스터디_게시판수정_권한X() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        studyJoinRepository.save(new StudyJoin(member, study, StudyRole.MEMBER));
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyBoardUpdateRequestDto requestDto = new StudyBoardUpdateRequestDto("테스트 게시판 수정");

        //when, then
        Assertions.assertThrows(StudyHasNoProperRoleException.class, ()->studyBoardService.updateBoard(member.getId(), study.getId(), studyBoard.getId(), requestDto));
    }

    @Test
    public void 스터디_게시판삭제_권한O() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        //when
        StudyBoardDeleteResponseDto ActualResult = studyBoardService.deleteBoard(member.getId(), study.getId(), studyBoard.getId());

        //then
        Assertions.assertEquals(studyBoard.getId(), ActualResult.getStudyBoardId());
        Assertions.assertEquals(studyBoard.getTitle(), ActualResult.getTitle());
    }

    @Test
    public void 스터디_게시판삭제_권한X() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        studyJoinRepository.save(new StudyJoin(member, study, StudyRole.MEMBER));
        StudyBoard studyBoard = study.getStudyBoards().get(0);

        //when, then
        Assertions.assertThrows(StudyHasNoProperRoleException.class, ()->studyBoardService.deleteBoard(member.getId(), study.getId(), studyBoard.getId()));
    }
}