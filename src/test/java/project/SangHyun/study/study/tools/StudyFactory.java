package project.SangHyun.study.study.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyState;

public class StudyFactory extends BasicFactory {
    public static StudyCreateRequestDto makeCreateDto(Member member) {
        return new StudyCreateRequestDto(member.getId(), "테스트 스터디", "백엔드",
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);
    }

    public static StudyUpdateRequestDto makeUpdateDto(String title, String topic) {
        return new StudyUpdateRequestDto(title, topic,
                null, 2L, StudyState.STUDYING, RecruitState.PROCEED);
    }
}
