package project.SangHyun;

import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.List;

public class BasicFactory {
    public static <T> SingleResult<T> makeSingleResult(T responseDto) {
        SingleResult<T> singleResult = new SingleResult<>();
        singleResult.setCode(0); singleResult.setSuccess(true); singleResult.setMsg("성공");
        singleResult.setData(responseDto);
        return singleResult;
    }

    public static <T> MultipleResult<T> makeMultipleResult(List<T> responseDtos) {
        MultipleResult<T> multipleResult = new MultipleResult<>();
        multipleResult.setCode(0); multipleResult.setSuccess(true); multipleResult.setMsg("성공");
        multipleResult.setData(responseDtos);
        return multipleResult;
    }

    public static Member makeTestAuthMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "상현", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    public static Member makeTestNotAuthMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm2!", "encodedPW", "유나", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    public static Study makeTestStudy(Member member, List<StudyJoin> studyJoins, List<StudyBoard> studyBoards) {
        Long studyId = 1L;
        Study study = new Study("프론트엔드 스터디", "프론트엔드",
                null, StudyState.STUDYING, RecruitState.PROCEED, 2L, member, studyJoins, studyBoards);
        ReflectionTestUtils.setField(study, "id", studyId);
        return study;
    }

    public static StudyJoin makeTestStudyJoin(Member member, Study study) {
        Long studyJoinId = 1L;
        StudyJoin studyJoin = new StudyJoin(member, study, StudyRole.CREATOR);
        ReflectionTestUtils.setField(studyJoin, "id", studyJoinId);
        return studyJoin;
    }

    public static StudyBoard makeTestStudyBoard(Study study) {
        Long studyBoardId = 1L;
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", study);
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);
        return studyBoard;
    }

    public static StudyArticle makeTestStudyArticle(Member member, StudyBoard studyBoard) {
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", 0L, member, studyBoard);
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        return studyArticle;
    }
}
