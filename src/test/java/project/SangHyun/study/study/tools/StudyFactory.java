package project.SangHyun.study.study.tools;

import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.BasicFactory;
import project.SangHyun.common.dto.SliceResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyDeleteResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.dto.response.StudyUpdateResponseDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class StudyFactory extends BasicFactory {
    private static InputStream fileInputStream;
    private static MultipartFile multipartFile;

    static {
        try {
            fileInputStream = new URL("https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg").openStream();
            multipartFile = new MockMultipartFile("git", "git.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Request
    public static StudyCreateRequestDto makeCreateRequestDto(Member member) {
        return new StudyCreateRequestDto(member.getId(), "프론트엔드 모집", List.of("프론트엔드"),
                "테스트", StudyCategory.CSE, "2021-10-01", "2021-12-25", 2L, multipartFile, StudyMethod.FACE,
                StudyState.STUDYING, RecruitState.PROCEED);
    }

    public static StudyUpdateRequestDto makeUpdateRequestDto(String title, List<String> tags) {
        return new StudyUpdateRequestDto(title, tags,
                "변경", "2021-10-01", "2021-12-25", StudyCategory.CSE, 2L, multipartFile, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED);
    }

    // Response
    public static StudyCreateResponseDto makeCreateResponseDto(Study study) {
        return StudyCreateResponseDto.create(study);
    }

    public static SliceResponseDto makeFindAllResponseDto(Slice<Study> study) {
        return SliceResponseDto.create(study, StudyFindResponseDto::create);
    }

    public static StudyUpdateResponseDto makeUpdateResponseDto(Study study, String title, String content) {
        StudyUpdateResponseDto responseDto = StudyUpdateResponseDto.create(study);
        responseDto.setTitle(title);
        responseDto.setContent(content);
        return responseDto;
    }

    public static StudyDeleteResponseDto makeDeleteResponseDto(Study study) {
        return StudyDeleteResponseDto.create(study);
    }

    public static StudyFindResponseDto makeFindResponseDto(Study study) {
        return StudyFindResponseDto.create(study);
    }
}
