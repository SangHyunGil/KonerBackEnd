package project.SangHyun.factory.studyjoin;

import project.SangHyun.factory.BasicFactory;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinCreateRequestDto;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinUpdateAuthorityRequestDto;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinUpdateAuthorityDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;

public class StudyJoinFactory extends BasicFactory {
    // Request
    public static StudyJoinCreateRequestDto makeCreateRequestDto(String content) {
        return new StudyJoinCreateRequestDto(content);
    }

    public static StudyJoinCreateDto makeCreateDto(String content) {
        return new StudyJoinCreateDto(content);
    }

    public static StudyJoinUpdateAuthorityRequestDto makeUpdateAuthorityRequestDto(StudyRole studyRole) {
        return new StudyJoinUpdateAuthorityRequestDto(studyRole);
    }

    public static StudyJoinUpdateAuthorityDto makeUpdateAuthorityDto(StudyRole studyRole) {
        return new StudyJoinUpdateAuthorityDto(studyRole);
    }

    // Response
    public static StudyMembersDto makeStudyMembersDto(StudyMembersInfoDto studyMembersInfoDto) {
        return StudyMembersDto.create(studyMembersInfoDto);
    }
}
