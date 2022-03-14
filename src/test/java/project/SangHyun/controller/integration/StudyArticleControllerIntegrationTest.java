package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import project.SangHyun.factory.studyarticle.StudyArticleFactory;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleUpdateRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudyArticleControllerIntegrationTest extends ControllerIntegrationTest{

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 모든 게시글을 로드한다.")
    public void loadArticles() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article", backendStudy.getId(), announceBoard.getId())
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디에 참여하지 않은 회원은 해당 스터디의 한 카테고리에 해당하는 모든 게시글을 로드할 수 없다.")
    public void loadArticles_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article", backendStudy.getId(), announceBoard.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 조회한다.")
    public void loadArticle() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디에 속하지 않은 회원은 해당 스터디의 한 카테고리에 해당하는 게시글을 조회할 수 없다.")
    public void loadArticle_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 생성한다.")
    public void createArticle() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyArticleCreateRequestDto requestDto = StudyArticleFactory.makeCreateRequestDto(studyMember);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디에 참여하지 않은 회원은 해당 스터디의 한 카테고리에 해당하는 게시글을 생성할 수 없다.")
    public void createArticle_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(studyMember.getId(), "테스트 글", "테스트 글");

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 수정한다.")
    public void updateArticle() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());
        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("공지사항 수정 글", "수정 글");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디의 한 카테고리에 해당하는 게시글이 부적절하다면 수정할 수 있다.")
    public void updateArticle_webAdmin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());
        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("올바른 글", "수정 글");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 스터디의 한 카테고리에 해당하는 게시글이 부적절하다면 수정할 수 있다.")
    public void updateArticle_studyAdmin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyAdminMember.getEmail());
        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("올바른 글", "수정 글");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 회원이 아닌 사람에 의한 게시글 수정은 실패한다.")
    public void updateArticle_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("공지사항 수정 글", "수정 글");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시글 작성자가 아닌 회원에 의한 게시글 수정은 실패한다.")
    public void updateArticle_fail2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());
        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("공지사항 수정 글", "수정 글");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 삭제한다.")
    public void deleteArticle() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 스터디의 한 카테고리에 해당하는 게시글이 부적절하다면 삭제할 수 있다.")
    public void deleteArticle_studyAdmin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디의 한 카테고리에 해당하는 게시글이 부적절하다면 삭제할 수 있다.")
    public void deleteArticle_webAdmin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 회원이 아닌 사람에 의한 게시글 삭제는 실패한다.")
    public void deleteArticle_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시글 작성자가 아닌 회원에 의한 게시글 삭제는 실패한다.")
    public void deleteArticle_fail2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}", backendStudy.getId(), announceBoard.getId(), announceArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}