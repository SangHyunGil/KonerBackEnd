package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import project.SangHyun.factory.studycomment.StudyCommentFactory;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentUpdateRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudyCommentControllerIntegrationTest extends ControllerIntegrationTest{

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글에 모든 댓글을 로드한다.")
    public void findAllComments() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article/{articleId}/comment", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 회원이 아니라면 스터디의 한 카테고리에 해당하는 한 게시글에 모든 댓글을 로드할 수 없다.")
    public void findAllComments_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article/{articleId}/comment", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 생성한다.")
    public void createComment() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(studyMember, null);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글에 대댓글을 생성한다.")
    public void createComment2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(studyMember, parentComment);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디에 참여하지 않은 회원은 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 생성할 수 없다.")
    public void createComment_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(notStudyMember, null);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("댓글 작성자는 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 수정할 수 있다.")
    public void updateComment() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("테스트 댓글 수정"));
    }

    @Test
    @DisplayName("스터디 관리자는 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 수정할 수 있다.")
    public void updateComment2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyAdminMember.getEmail());
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("테스트 댓글 수정"));
    }

    @Test
    @DisplayName("웹 관리자는 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 수정할 수 있다.")
    public void updateComment3() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("테스트 댓글 수정"));
    }

    @Test
    @DisplayName("스터디 회원이 아닌 경우 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 수정할 수 없다.")
    public void updateComment_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("댓글 작성자가 아닌 경우 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 수정할 수 없다.")
    public void updateComment_fail2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("댓글 작성자는 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 있다.")
    public void deleteComment() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 있다.")
    public void deleteComment2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 있다.")
    public void deleteComment3() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 회원이 아닌 경우 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 없다.")
    public void deleteComment_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("댓글 작성자가 아닌 경우 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 없다.")
    public void deleteComment_fail2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId(), parentComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}