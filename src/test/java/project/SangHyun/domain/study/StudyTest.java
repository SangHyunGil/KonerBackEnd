package project.SangHyun.domain.study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.helper.AwsS3BucketHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.service.dto.request.StudyUpdateDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class StudyTest {
    @Autowired
    private AwsS3BucketHelper awsS3BucketHelper;

    @Test
    @DisplayName("스터디 정보를 업데이트한다.")
    public void updateStudyInfo() throws Exception {
        //given
        Member member = new Member(1L);
        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
        Study study = new Study("백엔드 스터디", List.of("백엔드"), "백엔드 스터디 모집합니다!",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", 2L,
                "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, member, studyJoins, studyBoards);
        InputStream fileInputStream = new URL("https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg").openStream();
        MultipartFile multipartFile = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);
        StudyUpdateDto requestDto = new StudyUpdateDto("프론트엔드 모집", List.of("프론트엔드"), "음..", "2021-10-01", "2021-12-25", StudyCategory.CSE, 2L, multipartFile, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED);

        //when
        Study ActualResult = study.update(requestDto.toEntity(), awsS3BucketHelper.store(multipartFile));

        //then
        Assertions.assertEquals("프론트엔드 모집", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 참여한다.")
    public void joinStudy() throws Exception {
        //given
        Member member = new Member(1L);
        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
        Study study = new Study("백엔드 스터디", List.of("백엔드"), "백엔드 스터디 모집합니다!",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", 2L,
                "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, member, studyJoins, studyBoards);
        StudyJoin studyJoin = new StudyJoin(1L);

        //when
        study.join(studyJoin);

        //then
        Assertions.assertEquals(2, study.getStudyJoins().size());
    }

    @Test
    @DisplayName("스터디 게시판을 추가한다.")
    public void addBoard() throws Exception {
        //given
        Member member = new Member(1L);
        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
        Study study = new Study("백엔드 스터디", List.of("백엔드"), "백엔드 스터디 모집합니다!",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", 2L,
                "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, member, studyJoins, studyBoards);
        StudyBoard studyBoard = new StudyBoard(1L);

        //when
        study.addBoard(studyBoard);

        //then
        Assertions.assertEquals(2, study.getStudyBoards().size());
    }
}