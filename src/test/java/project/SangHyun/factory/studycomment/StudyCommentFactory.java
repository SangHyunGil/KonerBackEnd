package project.SangHyun.factory.studycomment;

import project.SangHyun.factory.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.controller.dto.response.StudyCommentResponseDto;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentCreateDto;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentUpdateDto;
import project.SangHyun.study.studycomment.service.dto.response.StudyCommentDto;

public class StudyCommentFactory extends BasicFactory {
    // Request
    public static StudyCommentCreateRequestDto makeCreateRequestDto(Member member, StudyComment parentComment) {
        return new StudyCommentCreateRequestDto(member.getId(), getParentCommentId(parentComment), "테스트 댓글입니다.");
    }

    public static StudyCommentCreateDto makeCreateDto(Member member, StudyComment parentComment) {
        return new StudyCommentCreateDto(member.getId(), getParentCommentId(parentComment), "테스트 댓글입니다.");
    }

    private static Long getParentCommentId(StudyComment parentComment) {
        if (parentComment == null)
            return null;
        return parentComment.getId();
    }

    public static StudyCommentUpdateRequestDto makeUpdateRequestDto(String content) {
        return new StudyCommentUpdateRequestDto(content);
    }

    public static StudyCommentUpdateDto makeUpdateDto(String content) {
        return new StudyCommentUpdateDto(content);
    }

    // Response
    public static StudyCommentDto makeDto(StudyComment studyComment) {
        return StudyCommentDto.create(studyComment);
    }

    public static StudyCommentResponseDto makeResponseDto(StudyCommentDto studyCommentDto) {
        return StudyCommentResponseDto.create(studyCommentDto);
    }
}
