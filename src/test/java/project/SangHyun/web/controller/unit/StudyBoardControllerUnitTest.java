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
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.StudyBoardService;
import project.SangHyun.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.web.controller.StudyBoardController;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyBoardControllerUnitTest {
    MockMvc mockMvc;
    @InjectMocks
    StudyBoardController studyBoardController;
    @Mock
    StudyBoardService studyBoardService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyBoardController).build();
    }

    @Test
    public void 게시판모두찾기() throws Exception {
        //given
        Long studyId = 1L;

        Long studyBoardId = 1L;
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", new Study(studyId));
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);
        List<StudyBoardFindResponseDto> responseDtos = List.of(StudyBoardFindResponseDto.createDto(studyBoard));

        MultipleResult<StudyBoardFindResponseDto> ExpectResult = new MultipleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDtos);

        //mocking
        given(studyBoardService.findBoards(studyId)).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].studyBoardId").value(ExpectResult.getData().get(0).getStudyBoardId()))
                .andExpect(jsonPath("$.data[0].title").value(ExpectResult.getData().get(0).getTitle()));
    }

    @Test
    public void 게시판생성() throws Exception {
        //given
        Long studyId = 1L;
        String accessToken = "accessToken";
        StudyBoardCreateRequestDto requestDto = new StudyBoardCreateRequestDto("테스트 게시판");

        Long studyBoardId = 1L;
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", new Study(studyId));
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);
        StudyBoardCreateResponseDto responseDto = StudyBoardCreateResponseDto.createDto(studyBoard);

        SingleResult<StudyBoardCreateResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(studyBoardService.createBoard(1L, requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyBoardId").value(ExpectResult.getData().getStudyBoardId()))
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()))
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()));
    }
}