package project.SangHyun.study.studyarticle.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.member.domain.Member;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.study.controller.StudyController;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyDeleteResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.study.studyarticle.controller.StudyArticleController;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;
import project.SangHyun.study.studyarticle.service.StudyArticleService;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.service.StudyJoinService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StudyArticleControllerUnitTest {
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
    @DisplayName("스터디의 한 카테고리에 해당하는 모든 게시글을 로드한다.")
    public void loadArticles() throws Exception {
        //given
        String accessToken = "accessToken";
        Long memberId = 1L;
        Long studyId = 1L;
        Long boardId = 1L;

        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, new Member(memberId), new StudyBoard(boardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        List<StudyArticleFindResponseDto> responseDtos = List.of(StudyArticleFindResponseDto.create(studyArticle));

        MultipleResult<StudyArticleFindResponseDto> ExpectResult = new MultipleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDtos);

        //mocking
        given(studyArticleService.findAllArticles(studyId, boardId)).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);
        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article", studyId, boardId)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 조회한다.")
    public void loadArticle() throws Exception {
        //given
        String accessToken = "accessToken";
        Long memberId = 1L;
        Long studyId = 1L;
        Long boardId = 1L;

        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, new Member(memberId), new StudyBoard(boardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        StudyArticleFindResponseDto responseDto = StudyArticleFindResponseDto.create(studyArticle);

        SingleResult<StudyArticleFindResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(studyArticleService.findArticle(studyId, boardId)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);
        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article/{articleId}", studyId, boardId, studyArticleId)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 생성한다.")
    public void createArticle() throws Exception {
        //given
        String accessToken = "accessToken";
        Long memberId = 1L;
        Long studyId = 1L;
        Long boardId = 1L;

        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, new Member(memberId), new StudyBoard(boardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        StudyArticleCreateResponseDto responseDto = StudyArticleCreateResponseDto.create(studyArticle);

        SingleResult<StudyArticleCreateResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(memberId, "테스트 글", "테스트 글입니다.");

        //mocking
        given(studyArticleService.createArticle(studyId, boardId, requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article", studyId, boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        String accessToken = "accessToken";
        Long memberId = 1L;
        Long studyId = 1L;
        Long boardId = 1L;

        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, new Member(memberId), new StudyBoard(boardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        StudyArticleUpdateResponseDto responseDto = StudyArticleUpdateResponseDto.create(studyArticle);

        SingleResult<StudyArticleUpdateResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("테스트 수정 글", "테스트 수정 글입니다.");

        //mocking
        given(studyArticleService.updateArticle(studyId, boardId, requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}", studyId, boardId, studyArticleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 삭제한다.")
    public void deleteArticle() throws Exception {
        //given
        String accessToken = "accessToken";
        Long memberId = 1L;
        Long studyId = 1L;
        Long boardId = 1L;

        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, new Member(memberId), new StudyBoard(boardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        StudyArticleDeleteResponseDto responseDto = StudyArticleDeleteResponseDto.create(studyArticle);

        SingleResult<StudyArticleDeleteResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(studyArticleService.deleteArticle(studyId, boardId)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}", studyId, boardId, studyArticleId)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}
