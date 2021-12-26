package project.SangHyun.study.studycomment.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;

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
}
