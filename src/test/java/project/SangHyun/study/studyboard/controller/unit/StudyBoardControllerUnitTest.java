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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.studyboard.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardDeleteResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardUpdateResponseDto;
import project.SangHyun.study.studyboard.service.StudyBoardService;
import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.study.studyboard.controller.StudyBoardController;
import project.SangHyun.study.studyboard.tools.StudyBoardFactory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyBoardControllerUnitTest {
    String accessToken;
    Member member;
    Study study;
    StudyBoard studyBoard;

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

        accessToken = "accessToken";
        member = StudyBoardFactory.makeTestAuthMember();
        study = StudyBoardFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyBoard = StudyBoardFactory.makeTestStudyBoard(study);
        study.addBoard(studyBoard);
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 모두 로드한다.")
    public void loadBoard() throws Exception {
        //given
        List<StudyBoardFindResponseDto> responseDtos = List.of(StudyBoardFactory.makeFindResponseDto(studyBoard));
        MultipleResult<StudyBoardFindResponseDto> ExpectResult = StudyBoardFactory.makeMultipleResult(responseDtos);

        //mocking
        given(studyBoardService.findAllBoards(study.getId())).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board", study.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();
        StudyBoard createdStudyBoard = requestDto.toEntity(study.getId());
        StudyBoardCreateResponseDto responseDto = StudyBoardFactory.makeCreateResponseDto(createdStudyBoard);
        SingleResult<StudyBoardCreateResponseDto> ExpectResult = StudyBoardFactory.makeSingleResult(responseDto);

        //mocking
        given(studyBoardService.createBoard(study.getId(), requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyBoardId").value(ExpectResult.getData().getStudyBoardId()))
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()));
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("테스트 게시판 수정");
        StudyBoardUpdateResponseDto responseDto = StudyBoardFactory.makeUpdateResponseDto(studyBoard, "테스트 게시판 수정");
        SingleResult<StudyBoardUpdateResponseDto> ExpectResult = StudyBoardFactory.makeSingleResult(responseDto);

        //mocking
        given(studyBoardService.updateBoard(study.getId(), studyBoard.getId(), requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()));
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 삭제한다.")
    public void deleteBoard() throws Exception {
        //given
        StudyBoardDeleteResponseDto responseDto = StudyBoardFactory.makeDeleteResponseDto(studyBoard);
        SingleResult<StudyBoardDeleteResponseDto> ExpectResult = StudyBoardFactory.makeSingleResult(responseDto);

        //mocking
        given(studyBoardService.deleteBoard(study.getId(), studyBoard.getId())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}