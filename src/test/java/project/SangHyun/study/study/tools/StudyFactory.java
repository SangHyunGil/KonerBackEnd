package project.SangHyun.study.study.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudyFactory extends BasicFactory {
    // Request
    public static StudyCreateRequestDto makeCreateDto(Member member) {
        return new StudyCreateRequestDto(member.getId(), "프론트엔드 모집", "프론트엔드",
                "테스트", 2L, StudyState.STUDYING, RecruitState.PROCEED);
    }

    public static StudyUpdateRequestDto makeUpdateDto(String title, String topic) {
        return new StudyUpdateRequestDto(title, topic,
                "변경", 2L, StudyState.STUDYING, RecruitState.PROCEED);
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
