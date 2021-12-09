package project.SangHyun.web.controller.Integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
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
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.domain.service.StudyBoardService;
import project.SangHyun.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.web.controller.StudyBoardController;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when, then
        mockMvc.perform(get("/study/{studyId}/board", study.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시판생성() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("test");
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoardCreateRequestDto request = new StudyBoardCreateRequestDto("테스트 게시판");
        //when, then
        mockMvc.perform(post("/study/{studyId}/board/", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(request))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}