package project.SangHyun.factory;

import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.message.domain.Message;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.videoroom.domain.VideoRoom;

import java.util.List;

public class BasicFactory {

    public static Result makeDefaultSuccessResult() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg("성공");

        return result;
    }

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

    public static Member makeTestAdminMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "상현", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_ADMIN, "상현입니다.");
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    public static Member makeTestAuthMember() {
        Long memberId = 2L;
        Member member = new Member("xptmxm2!", "encodedPW", "유나", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "유나입니다.");
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    public static Member makeTestNotAuthMember() {
        Long memberId = 3L;
        Member member = new Member("xptmxm3!", "encodedPW", "동욱", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_NOT_PERMITTED, "동욱입니다.");
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    public static Member makeTestAuthMember2() {
        Long memberId = 4L;
        Member member = new Member("xptmxm4!", "encodedPW", "영탁", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "영탁입니다.");
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    public static Study makeTestStudy(Member member, List<StudyJoin> studyJoins, List<StudyBoard> studyBoards) {
        Long studyId = 1L;
        Study study = new Study("프론트엔드 스터디", List.of("프론트엔드"),"프론트엔드모집입니다.",
                "C:\\Users\\Family\\Pictures\\Screenshots\\2.png",  2L, "2021-10-01", "2021-12-25", StudyCategory.CSE,  StudyMethod.FACE, StudyState.STUDYING,
                RecruitState.PROCEED, member, studyJoins, studyBoards);
        ReflectionTestUtils.setField(study, "id", studyId);
        return study;
    }

    public static StudyJoin makeTestStudyJoinCreator(Member member, Study study) {
        Long studyJoinId = 1L;
        StudyJoin studyJoin = new StudyJoin(member, "빠르게 진행합니다.", study, StudyRole.CREATOR);
        ReflectionTestUtils.setField(studyJoin, "id", studyJoinId);
        return studyJoin;
    }

    public static StudyJoin makeTestStudyJoinApply(Member member, Study study) {
        Long studyJoinId = 1L;
        StudyJoin studyJoin = new StudyJoin(member, "빠르게 진행합니다.", study, StudyRole.APPLY);
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

    public static StudyComment makeTestStudyComment(Member member, StudyArticle studyArticle) {
        Long studyCommentId = 1L;
        StudyComment studyComment = new StudyComment(member, studyArticle, null, "테스트 댓글입니다.", false);
        ReflectionTestUtils.setField(studyComment, "id", studyCommentId);
        return studyComment;
    }

    public static StudyComment makeTestStudyReplyComment(Member member, StudyArticle studyArticle, StudyComment studyComment) {
        Long studyReplyCommentId = 2L;
        StudyComment studyReplyComment = new StudyComment(member, studyArticle, studyComment, "테스트 댓글입니다.", false);
        ReflectionTestUtils.setField(studyReplyComment, "id", studyReplyCommentId);
        return studyReplyComment;
    }

    public static Message makeTestMessage(Long messageId, Member sender, Member receiver) {
        Message message = new Message("테스트 메세지", sender, receiver, false, false, false);
        ReflectionTestUtils.setField(message, "id", messageId);
        return message;
    }

    public static StudySchedule makeTestSchedule(Long studyScheduleId, String title) {
        StudySchedule schedule = new StudySchedule(title, "2021-12-15", "2022-03-01", "18:00", "22:00", new Study(1L));
        ReflectionTestUtils.setField(schedule, "id", studyScheduleId);
        return schedule;
    }

    public static VideoRoom makeTestVideoRoom(Long videoRoomId, String title, Member member, Study study) {
        VideoRoom schedule = new VideoRoom(1234L, title, null, member, study);
        ReflectionTestUtils.setField(schedule, "id", videoRoomId);
        return schedule;
    }
}
