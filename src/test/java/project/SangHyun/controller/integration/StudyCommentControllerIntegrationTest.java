package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.TestDB;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.factory.studycomment.StudyCommentFactory;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class StudyCommentControllerIntegrationTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    JwtTokenHelper accessTokenHelper;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글에 모든 댓글을 로드한다.")
    public void findAllComments() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        StudyArticle studyArticle = testDB.findAnnounceArticle();

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글에 모든 댓글을 로드할 수 없다.")
    public void findAllComments_fail() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        StudyArticle studyArticle = testDB.findAnnounceArticle();

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 생성한다.")
    public void createComment() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(member, null);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
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
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment parentComment = testDB.findParentComment();
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(member, parentComment);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
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
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findNotStudyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(member, null);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article/{articleId}/comment", study.getId(), studyBoard.getId(), studyArticle.getId())
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
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
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
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
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
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findAdminMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
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
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findNotStudyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
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
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findStudyMemberNotResourceOwner();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto("테스트 댓글 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
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
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 있다.")
    public void deleteComment2() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 있다.")
    public void deleteComment3() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findAdminMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 회원이 아닌 경우 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 없다.")
    public void deleteComment_fail() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findNotStudyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("댓글 작성자가 아닌 경우 스터디의 한 카테고리에 해당하는 한 게시글에 댓글을 삭제할 수 없다.")
    public void deleteComment_fail2() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        Member member = testDB.findStudyMemberNotResourceOwner();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyComment studyComment = testDB.findParentComment();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}/comment/{commentId}", study.getId(), studyBoard.getId(), studyArticle.getId(), studyComment.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}