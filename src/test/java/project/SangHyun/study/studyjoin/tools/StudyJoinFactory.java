package project.SangHyun.study.studyjoin.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;

public class StudyJoinFactory extends BasicFactory {
    // Request
    public static StudyJoinRequestDto makeCreateDto(Study study, Member member) {
        return new StudyJoinRequestDto(study.getId(), member.getId());
    }

    // Response
    public static StudyJoinResponseDto makeCreateResponseDto(StudyJoin studyJoin) {
        return StudyJoinResponseDto.create(studyJoin);
    }
}
