package project.SangHyun.web.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyArticle;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.response.StudyArticleCreateResponseDto;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.SignService;
import project.SangHyun.domain.service.StudyArticleService;
import project.SangHyun.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.dto.request.StudyCreateRequestDto;
import project.SangHyun.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.StudyFindResponseDto;
import project.SangHyun.web.controller.SignController;
import project.SangHyun.web.controller.StudyArticleController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyArticleControllerUnitTest {
    MockMvc mockMvc;
    @InjectMocks
    StudyArticleController studyArticleController;
    @Mock
    StudyArticleService studyArticleService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyArticleController).build();
    }

    @Test
    public void 게시글모두찾기() throws Exception {
        //given
        Long boardId = 1L;

        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글", new Member(1L), new StudyBoard(1L));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        List<StudyArticleFindResponseDto> responseDtos = List.of(StudyArticleFindResponseDto.createDto(studyArticle));

        MultipleResult<StudyArticleFindResponseDto> ExpectResult = new MultipleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDtos);

        //mocking
        given(studyArticleService.findAllArticles(boardId)).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].articleId").value(ExpectResult.getData().get(0).getArticleId()))
                .andExpect(jsonPath("$.data[0].boardId").value(ExpectResult.getData().get(0).getBoardId()))
                .andExpect(jsonPath("$.data[0].title").value(ExpectResult.getData().get(0).getTitle()))
                .andExpect(jsonPath("$.data[0].content").value(ExpectResult.getData().get(0).getContent()));
    }

    @Test
    public void 게시글생성() throws Exception {
        //given
        String accessToken = "accessToken";
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(1L, "테스트 글", "테스트 글");

        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글", new Member(1L), new StudyBoard(1L));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        StudyArticleCreateResponseDto responseDto = StudyArticleCreateResponseDto.createDto(studyArticle);

        SingleResult<StudyArticleCreateResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(studyArticleService.createArticle(1L, requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.articleId").value(ExpectResult.getData().getArticleId()))
                .andExpect(jsonPath("$.data.boardId").value(ExpectResult.getData().getBoardId()))
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()))
                .andExpect(jsonPath("$.data.content").value(ExpectResult.getData().getContent()));
    }
}