package project.SangHyun.study.studycomment.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentDeleteResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentFindResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentUpdateResponseDto;

import java.util.List;

public class StudyCommentFactory extends BasicFactory {
    // Request
    public static StudyCommentCreateRequestDto makeCreateRequestDto(Member member, StudyComment parentComment) {
        return new StudyCommentCreateRequestDto(member.getId(), getParentCommentId(parentComment), "테스트 댓글입니다.");
    }

    private static Long getParentCommentId(StudyComment parentComment) {
        if (parentComment == null)
            return null;
        return parentComment.getId();
    }

    public static StudyCommentUpdateRequestDto makeUpdateRequestDto(String content) {
        return new StudyCommentUpdateRequestDto(content);
    }

    // Response
    public static List<StudyCommentFindResponseDto> makeFindAllResponseDto(List<StudyComment> studyComments) {
        return StudyCommentFindResponseDto.create(studyComments);
    }

    public static StudyCommentCreateResponseDto makeCreateResponseDto(StudyComment studyComment) {
        return StudyCommentCreateResponseDto.create(studyComment);
    }

    public static StudyCommentUpdateResponseDto makeUpdateResponseDto(StudyComment studyComment, String content) {
        StudyCommentUpdateResponseDto responseDto = StudyCommentUpdateResponseDto.create(studyComment);
        responseDto.setContent(content);
        return responseDto;
    }

    public static StudyCommentDeleteResponseDto makeDeleteResponseDto(StudyComment studyComment) {
        return StudyCommentDeleteResponseDto.create(studyComment);
    }
}
