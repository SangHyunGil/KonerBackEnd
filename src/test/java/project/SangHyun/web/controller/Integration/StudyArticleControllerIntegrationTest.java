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
import project.SangHyun.domain.entity.*;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.repository.*;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.dto.request.study.StudyArticleCreateRequestDto;
import project.SangHyun.dto.request.study.StudyArticleUpdateRequestDto;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class StudyArticleControllerIntegrationTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
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
    public void 게시글모두찾기() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm3!");
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board/{boardId}/article", study.getId(), studyBoard.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 게시글생성() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm3!");
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 글");

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시글_수정_권한O() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm3!");
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticle studyArticle = studyArticleRepository.findAllArticles(studyBoard.getId()).get(0);
        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("공지사항 수정 글", "수정 글");

        //when, then
        mockMvc.perform(put("/study/{studyId}/board/{boardId}/article/{articleId}", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시글_수정_권한X() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm1!");
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        studyJoinRepository.save(new StudyJoin(member, study, StudyRole.MEMBER));
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 글");

        //when, then
        mockMvc.perform(post("/study/{studyId}/board/{boardId}/article", study.getId(), studyBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void 게시글_삭제_권한O() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm3!");
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticle studyArticle = studyArticleRepository.findAllArticles(studyBoard.getId()).get(0);

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시글_삭제_권한X() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm1!");
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        studyJoinRepository.save(new StudyJoin(member, study, StudyRole.MEMBER));
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticle studyArticle = studyArticleRepository.findAllArticles(studyBoard.getId()).get(0);

        //when, then
        mockMvc.perform(delete("/study/{studyId}/board/{boardId}/article/{articleId}", study.getId(), studyBoard.getId(), studyArticle.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is5xxServerError());
    }
}