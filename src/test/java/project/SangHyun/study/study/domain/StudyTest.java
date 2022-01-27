//package project.SangHyun.study.study.domain;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//import project.SangHyun.member.domain.Member;
//import project.SangHyun.study.study.domain.enums.RecruitState;
//import project.SangHyun.study.study.domain.enums.StudyMethod;
//import project.SangHyun.study.study.domain.enums.StudyState;
//import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
//import project.SangHyun.study.studyboard.domain.StudyBoard;
//import project.SangHyun.study.studyjoin.domain.StudyJoin;
//import project.SangHyun.common.helper.FileStoreHelper;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//class StudyTest {
//    String filePathDir = new File("C:/Users/Family/Desktop/SH/spring/Study").getAbsolutePath() + "/";
//    FileStoreHelper fileStoreHelper = new FileStoreHelper(filePathDir);
//
//    @Test
//    @DisplayName("스터디 정보를 업데이트한다.")
//    public void updateStudyInfo() throws Exception {
//        //given
//        Member member = new Member(1L);
//        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
//        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
//        Study study = new Study("백엔드 스터디", new Tags(List.of(new Tag("백엔드"))), "백엔드 스터디 모집합니다!",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", "컴퓨터공학과",
//                new StudyOptions(StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE), 2L, new Schedule("2021-10-01", "2021-12-25"), member, studyJoins, studyBoards);
//        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Family\\Pictures\\Screenshots\\git.png");
//        MultipartFile multipartFile = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);
//        StudyUpdateRequestDto requestDto = new StudyUpdateRequestDto("프론트엔드 모집", List.of("프론트엔드"), "음..", "2021-10-01", "2021-12-25", "컴퓨터공학과", 2L, multipartFile, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED);
//
//        //when
//        Study ActualResult = study.update(requestDto, fileStoreHelper.storeFile(multipartFile));
//
//        //then
//        Assertions.assertEquals("프론트엔드 모집", ActualResult.getTitle());
//    }
//
//    @Test
//    @DisplayName("스터디에 참여한다.")
//    public void joinStudy() throws Exception {
//        //given
//        Member member = new Member(1L);
//        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
//        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
//        Study study = new Study("백엔드 스터디", new Tags(List.of(new Tag("백엔드"))), "백엔드 스터디 모집합니다!",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", "컴퓨터공학과",
//                new StudyOptions(StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE), 2L, new Schedule("2021-10-01", "2021-12-25"), member, studyJoins, studyBoards);
//        StudyJoin studyJoin = new StudyJoin(1L);
//
//        //when
//        study.join(studyJoin);
//
//        //then
//        Assertions.assertEquals(2, study.getStudyJoins().size());
//    }
//
//    @Test
//    @DisplayName("스터디 게시판을 추가한다.")
//    public void addBoard() throws Exception {
//        //given
//        Member member = new Member(1L);
//        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
//        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
//        Study study = new Study("백엔드 스터디", new Tags(List.of(new Tag("백엔드"))), "백엔드 스터디 모집합니다!",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", "컴퓨터공학과",
//                new StudyOptions(StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE), 2L, new Schedule("2021-10-01", "2021-12-25"), member, studyJoins, studyBoards);
//        StudyBoard studyBoard = new StudyBoard(1L);
//
//        //when
//        study.addBoard(studyBoard);
//
//        //then
//        Assertions.assertEquals(2, study.getStudyBoards().size());
//    }
//}