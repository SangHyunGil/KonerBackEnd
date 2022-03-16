package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import project.SangHyun.factory.studyboard.StudyBoardFactory;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardUpdateRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudyBoardControllerIntegrationTest extends ControllerIntegrationTest{

    @Test
    @DisplayName("스터디에 속한 게시판을 모두 로드한다.")
    public void loadBoard() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyMember.getEmail());
        Study study = testDB.findBackEndStudy();

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/boards", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디에 속하지 않은 회원은 해당 스터디에 속한 게시판을 로드할 수 없다.")
    public void loadBoard_fail() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyMember.getEmail());
        Study study = testDB.findBackEndStudy();

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/boards", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("관리자가 아닌 스터디원은 게시판을 생성할 수 없다.")
    public void createBoard() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyMember.getEmail());
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/boards/", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디에 속하지 않은 회원은 게시판을 생성할 수 없다.")
    public void createBoard_fail() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyMember.getEmail());
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/boards/", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("웹 관리자는 스터디에 속한 게시판을 생성할 수 있다.")
    public void createBoard_webAdmin() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(webAdminMember.getEmail());
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/boards/", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디 관리자는 스터디에 속한 게시판을 생성할 수 있다.")
    public void createBoard_studyAdmin() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyAdminMember.getEmail());
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/boards/", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 수정한다.")
    public void updateBoard() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyAdminMember.getEmail());
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/boards/{boardId}", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디에 속한 게시판을 수정할 수 있다.")
    public void updateBoard_webAdmin() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(webAdminMember.getEmail());
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/boards/{boardId}", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 스터디에 속한 게시판을 수정할 수 있다.")
    public void updateBoard_studyAdmin() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyAdminMember.getEmail());
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/boards/{boardId}", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("권한이 없는 사람에 의해 스터디에 속한 게시판 수정은 실패한다.")
    public void updateBoard_fail() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyMember.getEmail());
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/boards/{boardId}", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 삭제한다.")
    public void deleteBoard() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyCreator.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/boards/{boardId}", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 부적절한 스터디 게시판을 삭제할 수 있다.")
    public void deleteBoard_webAdmin() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/boards/{boardId}", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 부적절한 스터디 게시판을 삭제할 수 있다.")
    public void deleteBoard_studyAdmin() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/boards/{boardId}", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("권한이 없는 사람에 의해 스터디에 속한 게시판 삭제는 실패한다.")
    public void deleteBoard_fail() throws Exception {
        //given
        String accessToken = refreshTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/boards/{boardId}", backendStudy.getId(), announceBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}