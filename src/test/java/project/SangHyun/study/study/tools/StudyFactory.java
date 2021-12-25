package project.SangHyun.study.study.tools;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyMethod;
import project.SangHyun.study.study.enums.StudyState;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudyFactory extends BasicFactory {
    private static FileInputStream fileInputStream;
    private static MultipartFile multipartFile;

    static {
        try {
            fileInputStream = new FileInputStream("C:\\Users\\Family\\Pictures\\Screenshots\\git.png");
            multipartFile = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Request
    public static StudyCreateRequestDto makeCreateDto(Member member) {
        return new StudyCreateRequestDto(member.getId(), "프론트엔드 모집", "프론트엔드",
                "테스트", "2021-12-25", 2L, multipartFile, StudyMethod.FACE,
                StudyState.STUDYING, RecruitState.PROCEED);
    }

    public static StudyUpdateRequestDto makeUpdateDto(String title, String topic) {
        return new StudyUpdateRequestDto(title, topic,
                "변경", "2021-12-25", 2L, multipartFile, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED);
    }

    // Response
    public static StudyCreateResponseDto makeCreateResponseDto(Study study) {
        return StudyCreateResponseDto.create(study);
    }

    public static List<StudyFindResponseDto> makeFindAllResponseDto(Study ... studies) {
        return Arrays.stream(studies)
                    .map(study -> StudyFindResponseDto.create(study))
                    .collect(Collectors.toList());
    }

    public static StudyFindResponseDto makeFindResponseDto(Study study) {
        return StudyFindResponseDto.create(study);
    }
}
