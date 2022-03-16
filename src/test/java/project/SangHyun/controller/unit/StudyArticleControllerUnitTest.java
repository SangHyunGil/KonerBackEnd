package project.SangHyun.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.dto.response.PageResponseDto;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.factory.study.StudyFactory;
import project.SangHyun.study.studyarticle.controller.StudyArticleController;
import project.SangHyun.study.studyarticle.controller.dto.response.StudyArticleResponseDto;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.service.StudyArticleService;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleCreateDto;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleDto;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleUpdateDto;
import project.SangHyun.factory.studyarticle.StudyArticleFactory;
import project.SangHyun.study.studyboard.domain.StudyBoard;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StudyArticleControllerUnitTest {

    String accessToken;
    MockMvc mockMvc;
    Member member;
    Study study;
    StudyBoard studyBoard;
    StudyArticle studyArticle;
    List<StudyArticle> studyArticles;

    @InjectMocks
    StudyArticleController studyArticleController;
    @Mock
    StudyArticleService studyArticleService;
    @Mock
    ResponseService responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyArticleController).build();

        accessToken = "accessToken";
        member = StudyFactory.makeTestAuthMember();
        study = StudyArticleFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyBoard = StudyArticleFactory.makeTestStudyBoard(study);
        study.addBoard(studyBoard);
        studyArticle = StudyArticleFactory.makeTestStudyArticle(member, studyBoard);
        studyArticles = List.of(this.studyArticle);
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 모든 게시글을 로드한다.")
    public void loadArticles() throws Exception {
        //given
        Page<StudyArticle> studyArticlesPage = new PageImpl<>(this.studyArticles, PageRequest.of(0, 10), studyArticles.size());
        PageResponseDto responseDto = StudyArticleFactory.makeFindAllResponseDto(studyArticlesPage);
        SingleResult<PageResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyArticleService.findAllArticles(studyBoard.getId(), 0, 10)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/boards/{boardId}/articles", study.getId(), studyBoard.getId())
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 조회한다.")
    public void loadArticle() throws Exception {
        //given
        StudyArticleDto studyArticleDto = StudyArticleFactory.makeDto(studyArticle);
        StudyArticleResponseDto responseDto = StudyArticleFactory.makeResponseDto(studyArticleDto);
        SingleResult<StudyArticleResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyArticleService.findArticle(studyBoard.getId())).willReturn(studyArticleDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/boards/{boardId}/articles/{articleId}", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 생성한다.")
    public void createArticle() throws Exception {
        //given
        StudyArticleCreateRequestDto createRequestDto = StudyArticleFactory.makeCreateRequestDto(member);
        StudyArticleCreateDto createDto = StudyArticleFactory.makeCreateDto(member);
        StudyArticleDto studyArticleDto = StudyArticleFactory.makeDto(studyArticle);
        StudyArticleResponseDto responseDto = StudyArticleFactory.makeResponseDto(studyArticleDto);
        SingleResult<StudyArticleResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyArticleService.createArticle(studyBoard.getId(), createDto)).willReturn(studyArticleDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/boards/{boardId}/articles", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        StudyArticleUpdateRequestDto updateRequestDto = StudyArticleFactory.makeUpdateRequestDto("테스트 글 수정", "테스트 글 수정 내용");
        StudyArticleUpdateDto updateDto = StudyArticleFactory.makeUpdateDto("테스트 글 수정", "테스트 글 수정 내용");
        StudyArticleDto studyArticleDto = StudyArticleFactory.makeDto(studyArticle);
        StudyArticleResponseDto responseDto = StudyArticleFactory.makeResponseDto(studyArticleDto);
        SingleResult<StudyArticleResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyArticleService.updateArticle(studyBoard.getId(), updateDto)).willReturn(studyArticleDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/boards/{boardId}/articles/{articleId}", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()))
                .andExpect(jsonPath("$.data.content").value(ExpectResult.getData().getContent()));
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 삭제한다.")
    public void deleteArticle() throws Exception {
        //given
        Result ExpectResult = StudyArticleFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyArticleService).deleteArticle(studyBoard.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/boards/{boardId}/articles/{articleId}", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}
