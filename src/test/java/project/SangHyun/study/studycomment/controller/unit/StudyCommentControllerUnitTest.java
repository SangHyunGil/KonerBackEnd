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
import project.SangHyun.common.dto.response.MultipleResult;
import project.SangHyun.common.dto.response.Result;
import project.SangHyun.common.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.tools.StudyArticleFactory;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.controller.StudyCommentController;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.controller.dto.response.StudyCommentResponseDto;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.service.StudyCommentService;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentCreateDto;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentUpdateDto;
import project.SangHyun.study.studycomment.service.dto.response.StudyCommentDto;
import project.SangHyun.study.studycomment.tools.StudyCommentFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    ResponseService responseService;

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
        List<StudyCommentDto> studyCommentDto = List.of(studyComment, studyReplyComment).stream()
                .map(StudyCommentFactory::makeDto)
                .collect(Collectors.toList());
        List<StudyCommentResponseDto> responseDto = studyCommentDto.stream()
                .map(StudyCommentFactory::makeResponseDto)
                .collect(Collectors.toList());
        MultipleResult<StudyCommentResponseDto> ExpectResult = StudyArticleFactory.makeMultipleResult(responseDto);

        //mocking
        given(studyCommentService.findComments(studyArticle.getId())).willReturn(studyCommentDto);
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
        StudyCommentCreateRequestDto createRequestDto = StudyCommentFactory.makeCreateRequestDto(member, null);
        StudyCommentCreateDto createDto = StudyCommentFactory.makeCreateDto(member, null);
        StudyCommentDto studyCommentDto = StudyCommentFactory.makeDto(studyComment);
        StudyCommentResponseDto responseDto = StudyCommentFactory.makeResponseDto(studyCommentDto);
        SingleResult<StudyCommentResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyCommentService.createComment(studyArticle.getId(), createDto)).willReturn(studyCommentDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글의 대댓글을 생성한다.")
    public void createComment2() throws Exception {
        //given
        StudyCommentCreateRequestDto createRequestDto = StudyCommentFactory.makeCreateRequestDto(member, studyComment);
        StudyCommentCreateDto createDto = StudyCommentFactory.makeCreateDto(member, studyComment);
        StudyCommentDto studyCommentDto = StudyCommentFactory.makeDto(studyComment);
        StudyCommentResponseDto responseDto = StudyCommentFactory.makeResponseDto(studyCommentDto);
        SingleResult<StudyCommentResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyCommentService.createComment(studyArticle.getId(), createDto)).willReturn(studyCommentDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글의 댓글을 수정한다.")
    public void updateComment() throws Exception {
        //given
        StudyCommentUpdateRequestDto updateRequestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정입니다.");
        StudyCommentUpdateDto updateDto = StudyCommentFactory.makeUpdateDto("테스트 댓글 수정입니다.");
        studyComment.update("테스트 댓글 수정입니다.");
        StudyCommentDto studyCommentDto = StudyCommentFactory.makeDto(studyComment);
        StudyCommentResponseDto responseDto = StudyCommentFactory.makeResponseDto(studyCommentDto);
        SingleResult<StudyCommentResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyCommentService.updateComment(studyComment.getId(), updateDto)).willReturn(studyCommentDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value(ExpectResult.getData().getContent()));
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글의 대댓글을 삭제한다.")
    public void deleteComment() throws Exception {
        //given
        Result ExpectResult = StudyArticleFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyCommentService).deleteComment(studyReplyComment.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyReplyComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}
