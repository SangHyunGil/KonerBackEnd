package project.SangHyun.study.studycomment.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.member.domain.Member;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.tools.StudyArticleFactory;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.controller.StudyCommentController;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentDeleteResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentFindResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentUpdateResponseDto;
import project.SangHyun.study.studycomment.service.StudyCommentService;
import project.SangHyun.study.studycomment.tools.StudyCommentFactory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StudyCommentControllerUnitTest {
    String accessToken;
    MockMvc mockMvc;
    Member member;
    Study study;
    StudyBoard studyBoard;
    StudyArticle studyArticle;
    StudyComment studyComment;
    StudyComment studyReplyComment;

    @InjectMocks
    StudyCommentController studyCommentController;
    @Mock
    StudyCommentService studyCommentService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyCommentController).build();

        accessToken = "accessToken";
        member = StudyCommentFactory.makeTestAuthMember();
        study = StudyCommentFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyBoard = StudyCommentFactory.makeTestStudyBoard(study);
        study.addBoard(studyBoard);
        studyArticle = StudyCommentFactory.makeTestStudyArticle(member, studyBoard);
        studyComment = StudyCommentFactory.makeTestStudyComment(member, studyArticle);
        studyReplyComment = StudyCommentFactory.makeTestStudyReplyComment(member, studyArticle, this.studyComment);
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글의 댓글을 모두 조회한다.")
    public void findComments() throws Exception {
        //given
        List<StudyCommentFindResponseDto> responseDto = StudyCommentFactory.makeFindAllResponseDto(List.of(studyComment, studyReplyComment));
        MultipleResult<StudyCommentFindResponseDto> ExpectResult = StudyArticleFactory.makeMultipleResult(responseDto);

        //mocking
        given(studyCommentService.findComments(studyArticle.getId())).willReturn(responseDto);
        given(responseService.getMultipleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글의 댓글을 생성한다.")
    public void createComment() throws Exception {
        //given
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(member, null);
        StudyComment createdComment = requestDto.toEntity(studyArticle.getId());
        StudyCommentCreateResponseDto responseDto = StudyCommentFactory.makeCreateResponseDto(createdComment);
        SingleResult<StudyCommentCreateResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyCommentService.createComment(studyArticle.getId(), requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글의 대댓글을 생성한다.")
    public void createComment2() throws Exception {
        //given
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(member, studyComment);
        StudyComment createdComment = requestDto.toEntity(studyArticle.getId());
        StudyCommentCreateResponseDto responseDto = StudyCommentFactory.makeCreateResponseDto(createdComment);
        SingleResult<StudyCommentCreateResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyCommentService.createComment(studyArticle.getId(), requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글의 댓글을 수정한다.")
    public void updateComment() throws Exception {
        //given
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정입니다.");
        StudyCommentUpdateResponseDto responseDto = StudyCommentFactory.makeUpdateResponseDto(studyComment, "테스트 댓글 수정입니다.");
        SingleResult<StudyCommentUpdateResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyCommentService.updateComment(studyComment.getId(), requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value(ExpectResult.getData().getContent()));
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글의 대댓글을 삭제한다.")
    public void deleteComment() throws Exception {
        //given
        StudyCommentDeleteResponseDto responseDto = StudyCommentFactory.makeDeleteResponseDto(studyReplyComment);
        SingleResult<StudyCommentDeleteResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyCommentService.deleteComment(studyReplyComment.getId())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyReplyComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value(ExpectResult.getData().getContent()));
    }
}
