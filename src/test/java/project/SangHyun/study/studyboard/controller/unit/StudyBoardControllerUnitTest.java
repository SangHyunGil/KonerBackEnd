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
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyboard.controller.StudyBoardController;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.response.StudyBoardResponseDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.service.StudyBoardService;
import project.SangHyun.study.studyboard.service.dto.request.StudyBoardCreateDto;
import project.SangHyun.study.studyboard.service.dto.request.StudyBoardUpdateDto;
import project.SangHyun.study.studyboard.service.dto.response.StudyBoardDto;
import project.SangHyun.study.studyboard.tools.StudyBoardFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
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
    ResponseService responseService;

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
        List<StudyBoardDto> studyBoardDto = List.of(StudyBoardFactory.makeDto(studyBoard));
        List<StudyBoardResponseDto> responseDto = studyBoardDto.stream()
                                                            .map(StudyBoardFactory::makeResponseDto)
                                                            .collect(Collectors.toList());
        MultipleResult<StudyBoardResponseDto> ExpectResult = StudyBoardFactory.makeMultipleResult(responseDto);

        //mocking
        given(studyBoardService.findAllBoards(study.getId())).willReturn(studyBoardDto);
        given(responseService.convertToControllerDto(any(List.class), any(Function.class))).willReturn(responseDto);
        given(responseService.getMultipleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board", study.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        StudyBoardCreateRequestDto createRequestDto = StudyBoardFactory.makeCreateRequestDto();
        StudyBoardCreateDto createDto = StudyBoardFactory.makeCreateDto();
        StudyBoardDto studyBoardDto = StudyBoardFactory.makeDto(studyBoard);
        StudyBoardResponseDto responseDto = StudyBoardFactory.makeResponseDto(studyBoardDto);
        SingleResult<StudyBoardResponseDto> ExpectResult = StudyBoardFactory.makeSingleResult(responseDto);

        //mocking
        given(studyBoardService.createBoard(study.getId(), createDto)).willReturn(studyBoardDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/{studyId}/board", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ExpectResult.getData().getId()));
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        StudyBoardUpdateRequestDto updateRequestDto = StudyBoardFactory.makeUpdateRequestDto("테스트 게시판 수정");
        StudyBoardUpdateDto updateDto = StudyBoardFactory.makeUpdateDto("테스트 게시판 수정");
        StudyBoardDto studyBoardDto = StudyBoardFactory.makeDto(studyBoard);
        StudyBoardResponseDto responseDto = StudyBoardFactory.makeResponseDto(studyBoardDto);
        SingleResult<StudyBoardResponseDto> ExpectResult = StudyBoardFactory.makeSingleResult(responseDto);

        //mocking
        given(studyBoardService.updateBoard(studyBoard.getId(), updateDto)).willReturn(studyBoardDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()));
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 삭제한다.")
    public void deleteBoard() throws Exception {
        //given
        Result ExpectResult = StudyBoardFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyBoardService).deleteBoard(studyBoard.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}