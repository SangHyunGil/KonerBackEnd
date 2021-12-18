package project.SangHyun.study.studyboard.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.studyboard.service.StudyBoardService;
import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.study.studyboard.controller.StudyBoardController;

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
    @DisplayName("스터디에 속한 게시판을 모두 로드한다.")
    public void loadBoard() throws Exception {
        //given
        Long studyId = 1L;

        Long studyBoardId = 1L;
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", new Study(studyId));
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);
        List<StudyBoardFindResponseDto> responseDtos = List.of(StudyBoardFindResponseDto.create(studyBoard));

        MultipleResult<StudyBoardFindResponseDto> ExpectResult = new MultipleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDtos);

        //mocking
        given(studyBoardService.findAllBoards(studyId)).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        Long studyId = 1L;
        String accessToken = "accessToken";
        StudyBoardCreateRequestDto requestDto = new StudyBoardCreateRequestDto("테스트 게시판");

        Long studyBoardId = 1L;
        StudyBoard studyBoard = new StudyBoard("테스트 게시판", new Study(studyId));
        ReflectionTestUtils.setField(studyBoard, "id", studyBoardId);
        StudyBoardCreateResponseDto responseDto = StudyBoardCreateResponseDto.create(studyBoard);

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
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()));
    }
}