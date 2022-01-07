package project.SangHyun.study.studyboard.controller.integration;

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
import project.SangHyun.common.helper.RedisHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.tools.StudyBoardFactory;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.request.StudyBoardUpdateRequestDto;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class StudyBoardControllerIntegrationTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    RedisHelper redisHelper;
    @Autowired
    JwtTokenHelper refreshTokenHelper;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 모두 로드한다.")
    public void loadBoard() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();

        //when, then
        mockMvc.perform(get("/study/{studyId}/board", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디에 속하지 않은 회원은 해당 스터디에 속한 게시판을 로드할 수 없다.")
    public void loadBoard_fail() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();

        //when, then
        mockMvc.perform(get("/study/{studyId}/board", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디에 속한 게시판을 생성한다.")
    public void createBoard() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디에 속하지 않은 회원은 게시판을 생성할 수 없다.")
    public void createBoard_fail() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/", study.getId())
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
        Member member = testDB.findAdminMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 스터디에 속한 게시판을 생성할 수 있다.")
    public void createBoard_studyAdmin() throws Exception {
        //given
        Member member = testDB.findStudyAdminMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoardCreateRequestDto requestDto = StudyBoardFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/", study.getId())
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
        Member member = testDB.findStudyAdminMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
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
        Member member = testDB.findAdminMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
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
        Member member = testDB.findStudyAdminMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
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
        Member member = testDB.findGeneralMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        StudyBoardUpdateRequestDto requestDto = StudyBoardFactory.makeUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
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
        Member member = testDB.findStudyCreatorMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 부적절한 스터디 게시판을 삭제할 수 있다.")
    public void deleteBoard_webAdmin() throws Exception {
        //given
        Member member = testDB.findAdminMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 부적절한 스터디 게시판을 삭제할 수 있다.")
    public void deleteBoard_studyAdmin() throws Exception {
        //given
        Member member = testDB.findStudyAdminMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("권한이 없는 사람에 의해 스터디에 속한 게시판 삭제는 실패한다.")
    public void deleteBoard_fail() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String accessToken = refreshTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyBoard studyBoard = testDB.findAnnounceBoard();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}