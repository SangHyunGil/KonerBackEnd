package project.SangHyun.study.studyjoin.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;

public class StudyJoinFactory extends BasicFactory {
    // Request
    public static StudyJoinRequestDto makeRequestDto(String content) {
        return new StudyJoinRequestDto(content);
    }

    // Response
    public static StudyJoinResponseDto makeResponseDto(StudyJoin studyJoin) {
        return StudyJoinResponseDto.create(studyJoin);
    }
}
