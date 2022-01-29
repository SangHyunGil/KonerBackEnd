package project.SangHyun.study.study.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import project.SangHyun.common.advice.exception.DuplicateTagsException;
import project.SangHyun.common.advice.exception.InCorrectTagNameException;
import project.SangHyun.study.study.domain.enums.RecruitState;
import project.SangHyun.study.study.domain.enums.StudyMethod;
import project.SangHyun.study.study.domain.enums.StudyState;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

class TagsTest {
    @Test
    @DisplayName("공백의 태그를 가진 Tag 컬렉션은 예외를 반환한다.")
    public void blank() throws Exception {
        //given
        InputStream fileInputStream = new URL("https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg").openStream();
        MockMultipartFile profileImg = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);

        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(1L, "백엔드 스터디", List.of(" "),
                "백엔드 스터디입니다.", "CSE", "2021-12-01", "2021-01-01", 2L, profileImg,
                StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED);

        Assertions.assertThrows(InCorrectTagNameException.class, () -> requestDto.toEntity("convertedProfileImg"));

    }

    @Test
    @DisplayName("중복된 태그를 가지지 않은 Tag 컬렉션은 성공적으로 저장된다.")
    public void success() throws Exception {
        //given
        InputStream fileInputStream = new URL("https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg").openStream();
        MockMultipartFile profileImg = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);

        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(1L, "백엔드 스터디", List.of("백엔드", "JPA"),
                "백엔드 스터디입니다.", "CSE", "2021-12-01", "2021-01-01", 2L, profileImg,
                StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED);

        Study study = requestDto.toEntity("convertedProfileImg");

        Assertions.assertEquals(2, study.getTags().getTagNames().size());
    }

    @Test
    @DisplayName("중복된 태그를 가진 Tag 컬렉션은 예외를 반환한다.")
    public void duplicateTest() throws Exception {
        InputStream fileInputStream = new URL("https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg").openStream();
        MockMultipartFile profileImg = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);

        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(1L, "백엔드 스터디", List.of("백엔드", "백엔드"),
                "백엔드 스터디입니다.", "CSE", "2021-12-01", "2021-01-01", 2L, profileImg,
                StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED);

        Assertions.assertThrows(DuplicateTagsException.class, () -> requestDto.toEntity("convertedProfileImg"));
    }
}