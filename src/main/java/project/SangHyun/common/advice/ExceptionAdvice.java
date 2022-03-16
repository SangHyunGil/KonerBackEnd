package project.SangHyun.common.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.SangHyun.common.advice.exception.*;
import project.SangHyun.dto.response.Result;
import project.SangHyun.common.response.ResponseService;


@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(MemberNicknameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result memberNicknameAlreadyExistsException() {
        return responseService.getFailureResult(-100, "이미 존재하는 닉네임입니다.");
    }

    @ExceptionHandler(MemberEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result memberEmailAlreadyExistsException() {
        return responseService.getFailureResult(-101, "이미 존재하는 이메일입니다.");
    }

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result loginFailureException() {
        return responseService.getFailureResult(-102, "아이디 혹은 비밀번호가 틀립니다.");
    }

    @ExceptionHandler(AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result authenticationEntryPointException() {
        return responseService.getFailureResult(-102, "인증이 필요합니다.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result accessDeniedException() {
        return responseService.getFailureResult(-103, "권한이 필요합니다.");
    }

    @ExceptionHandler(ExceedMaximumStudyMember.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result ExceedMaximumStudyMember() {
        return responseService.getFailureResult(-104, "스터디 정원을 초과할 수 없습니다.");
    }

    @ExceptionHandler(EmailNotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result emailAuthenticationException() {
        return responseService.getFailureResult(-105, "이메일 인증이 필요합니다.");
    }

    @ExceptionHandler(EmailAuthTokenNotFountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result emailAuthTokenNotFountException() {
        return responseService.getFailureResult(-106, "유효하지 않은 인증요청입니다.");
    }

    @ExceptionHandler(InvalidAuthCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result invalidAuthCodeException() {
        return responseService.getFailureResult(-107, "유효하지 않은 인증코드입니다.");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result memberNotFoundException() {
        return responseService.getFailureResult(-108, "존재하지 않는 회원입니다.");
    }

    @ExceptionHandler(RedisValueDifferentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result invalidTokenException() {
        return responseService.getFailureResult(-109, "Token이 유효하지 않습니다.");
    }

    @ExceptionHandler(NotResourceOwnerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result notResourceOwnerException() {
        return responseService.getFailureResult(-110, "자원에 대한 접근 권한이 없습니다.");
    }

    @ExceptionHandler(MemberSameNickNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result memberSameNickNameException() {
        return responseService.getFailureResult(-111, "중복된 닉네임이 존재합니다.");
    }

    @ExceptionHandler(ChatRoomNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result chatRoomNotFoundException() {
        return responseService.getFailureResult(-112, "이미 존재하는 이메일입니다.");
    }

    @ExceptionHandler(InvalidWebSocketConnection.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result invalidWebSocketConnection() {
        return responseService.getFailureResult(-113, "인증 후 웹소켓 연결을 진행해야합니다.");
    }

    @ExceptionHandler(StudyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result studyNotFountException() {
        return responseService.getFailureResult(-114, "존재하지 않는 스터디입니다.");
    }

    @ExceptionHandler(StudyBoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result studyBoardNotFoundException() {
        return responseService.getFailureResult(-115, "존재하지 않는 게시판입니다.");
    }

    @ExceptionHandler(AlreadyJoinStudyMember.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result alreadyJoinStudyMember() {
        return responseService.getFailureResult(-116, "이미 가입한 스터디입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return responseService.getFailureResult(-117, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(NotBelongStudyMemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notBelongStudyMemberException() {
        return responseService.getFailureResult(-118, "해당 스터디에 소속되어 있지 않습니다.");
    }

    @ExceptionHandler(StudyHasNoProperRoleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result studyHasNoProperRoleException() {
        return responseService.getFailureResult(-119, "해당 스터디의 행동에 대한 권한이 존재하지 않습니다.");
    }

    @ExceptionHandler(StudyArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result studyArticleNotFoundException() {
        return responseService.getFailureResult(-120, "해당 스터디 게시글이 존재하지 않습니다.");
    }

    @ExceptionHandler(StudyArticleNotWriterException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result studyArticleNotOwnerException() {
        return responseService.getFailureResult(-121, "해당 스터디 게시글 작성자가 아닙니다.");
    }

    @ExceptionHandler(ResourceNotOwnerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result resourceNotOwnerException() {
        return responseService.getFailureResult(-122, "해당 자원에 대한 권한이 없습니다.");
    }

    @ExceptionHandler(StudyJoinNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result studyJoinNotFoundException() {
        return responseService.getFailureResult(-123, "스터디 참여 정보가 존재하지 않습니다.");
    }

    @ExceptionHandler(StudyCommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result studyCommentNotFoundException() {
        return responseService.getFailureResult(-124, "스터디 게시글의 댓글 정보가 존재하지 않습니다.");
    }

    @ExceptionHandler(HierarchyStructureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result hierarchyStructureException() {
        return responseService.getFailureResult(-125, "댓글 계층 구조 변환에 실패했습니다.");
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result handleBindException(BindException e) {
        return responseService.getFailureResult(-126, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(InCorrectTagNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result inCorrectTagNameException() {
        return responseService.getFailureResult(-127, "잘못된 태그 이름입니다.");
    }

    @ExceptionHandler(DuplicateTagsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected Result duplicateTagsException() {
        return responseService.getFailureResult(-128, "중복된 태그가 존재합니다.");
    }

    @ExceptionHandler(MessageNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected Result messageNotFountException() {
        return responseService.getFailureResult(-129, "존재하지 않는 쪽지입니다.");
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidEmailException() {
        return responseService.getFailureResult(-130, "적절하지 않은 이메일입니다.");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidPasswordException() {
        return responseService.getFailureResult(-131, "적절하지 않은 비밀번호입니다.");
    }

    @ExceptionHandler(InvalidNicknameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidNicknameException() {
        return responseService.getFailureResult(-132, "적절하지 않은 닉네임입니다.");
    }

    @ExceptionHandler(InvalidStudyTitleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidStudyTitleException() {
        return responseService.getFailureResult(-133, "적절하지 않은 스터디 제목입니다.");
    }

    @ExceptionHandler(InvalidStudyDescriptionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidStudyIntroductionException() {
        return responseService.getFailureResult(-134, "적절하지 않은 스터디 소개글입니다.");
    }

    @ExceptionHandler(InvalidHeadCountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidHeadCountException() {
        return responseService.getFailureResult(-135, "적절하지 않은 인원 수입니다.");
    }

    @ExceptionHandler(InvalidProfileUrlImg.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidProfileUrlImg() {
        return responseService.getFailureResult(-136, "적절하지 않은 프로필 이미지입니다.");
    }

    @ExceptionHandler(InvalidStudyArticleTitleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidStudyArticleTitleException() {
        return responseService.getFailureResult(-137, "적절하지 않은 스터디 게시글 제목입니다.");
    }

    @ExceptionHandler(InvalidStudyArticleContentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidStudyArticleContentException() {
        return responseService.getFailureResult(-138, "적절하지 않은 스터디 게시글 내용입니다.");
    }

    @ExceptionHandler(InvalidViewsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidViewsException() {
        return responseService.getFailureResult(-139, "적절하지 않은 스터디 게시글 조회수입니다.");
    }

    @ExceptionHandler(InvalidStudyBoardTitleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidStudyBoardTitleException() {
        return responseService.getFailureResult(-140, "적절하지 않은 스터디 게시판 제목입니다.");
    }

    @ExceptionHandler(InvalidStudyCommentContentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidStudyCommentContentException() {
        return responseService.getFailureResult(-141, "적절하지 않은 스터디 댓글 내용입니다.");
    }

    @ExceptionHandler(InvalidApplyContentContent.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidApplyContentContent() {
        return responseService.getFailureResult(-142, "적절하지 않은 스터디 지원 내용입니다.");
    }

    @ExceptionHandler(InvalidMessageContentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidMessageContentException() {
        return responseService.getFailureResult(-143, "적절하지 않은 쪽지 내용입니다.");
    }

    @ExceptionHandler(InvalidNotificationContentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidNotificationContentException() {
        return responseService.getFailureResult(-144, "적절하지 않은 알림 내용입니다.");
    }

    @ExceptionHandler(InvalidRelatedURLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidRelatedURLException() {
        return responseService.getFailureResult(-145, "적절하지 않은 알림 URL입니다.");
    }

    @ExceptionHandler(InvalidIntroductionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidIntroductionException() {
        return responseService.getFailureResult(-146, "적절하지 않은 회원 소개글입니다.");
    }

    @ExceptionHandler(InvalidStudyScheduleTitle.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidStudyScheduleTitle() {
        return responseService.getFailureResult(-147, "적절하지 않은 스케줄 제목입니다.");
    }

    @ExceptionHandler(StudyScheduleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected Result studyScheduleNotFoundException() {
        return responseService.getFailureResult(-148, "존재하지 않는 스케줄입니다.");
    }

    @ExceptionHandler(InvalidVideoRoomIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidVideoRoomIdException() {
        return responseService.getFailureResult(-149, "적절하지 않은 방 번호입니다.");
    }

    @ExceptionHandler(InvalidVideoRoomTitleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidVideoRoomTitleException() {
        return responseService.getFailureResult(-150, "적절하지 않은 방 제목입니다.");
    }

    @ExceptionHandler(InvalidPinException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidPinException() {
        return responseService.getFailureResult(-151, "적절하지 않은 방 비밀번호입니다.");
    }

    @ExceptionHandler(JanusRequestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Result janusRequestException() {
        return responseService.getFailureResult(-151, "Janus 요청에 실패했습니다.");
    }

    @ExceptionHandler(InvalidAuthorityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidAuthorityException() {
        return responseService.getFailureResult(-152, "잘못된 권한 요청입니다.");
    }

    @ExceptionHandler(StudyCreatorChangeAuthorityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result studyCreatorChangeAuthorityException() {
        return responseService.getFailureResult(-153, "스터디 생성자의 권한 변경은 불가능합니다.");
    }
}
