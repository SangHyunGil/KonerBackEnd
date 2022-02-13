package project.SangHyun.study.studyjoin.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinCreateRequestDto;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;

public class StudyJoinFactory extends BasicFactory {
    // Request
    public static StudyJoinCreateRequestDto makeCreateRequestDto(String content) {
        return new StudyJoinCreateRequestDto(content);
    }

    public static StudyJoinCreateDto makeCreateDto(String content) {
        return new StudyJoinCreateDto(content);
    }

    // Response
    public static StudyMembersDto makeStudyMembersDto(StudyMembersInfoDto studyMembersInfoDto) {
        return StudyMembersDto.create(studyMembersInfoDto);
    }
}
