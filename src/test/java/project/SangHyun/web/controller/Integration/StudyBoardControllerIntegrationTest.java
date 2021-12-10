package project.SangHyun.web.controller.Integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
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
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.dto.request.study.StudyBoardCreateRequestDto;
import project.SangHyun.dto.request.study.StudyBoardUpdateRequestDto;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    RedisService redisService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }
    @Test
    public void 게시판모두찾기() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm3!");
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시판생성() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm1!");
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoardCreateRequestDto requestDto = new StudyBoardCreateRequestDto("테스트 게시판");

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시판수정_권한O() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm3!");
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyBoardUpdateRequestDto requestDto = new StudyBoardUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시판수정_권한X() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm1!");
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        studyJoinRepository.save(new StudyJoin(member, study, StudyRole.MEMBER));
        StudyBoard studyBoard = study.getStudyBoards().get(0);

        StudyBoardUpdateRequestDto requestDto = new StudyBoardUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void 게시판삭제_권한O() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm3!");
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시판삭제_권한X() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm1!");
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        studyJoinRepository.save(new StudyJoin(member, study, StudyRole.MEMBER));
        StudyBoard studyBoard = study.getStudyBoards().get(0);

        StudyBoardUpdateRequestDto requestDto = new StudyBoardUpdateRequestDto("알고리즘 게시판");

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is5xxServerError());
    }
}