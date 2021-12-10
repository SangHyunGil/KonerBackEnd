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
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.dto.request.MemberRegisterRequestDto;
import project.SangHyun.dto.request.StudyCreateRequestDto;
import project.SangHyun.dto.request.StudyJoinRequestDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class StudyControllerIntegrationTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    MemberRepository memberRepository;
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
    public void 스터디정보로드() throws Exception {
        //given

        //when, then
        mockMvc.perform(get("/study"))
                .andExpect(status().isOk());
    }

    @Test
    public void 스터디세부정보로드() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when, then
        mockMvc.perform(get("/study/{id}", study.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyId").value(study.getId()))
                .andExpect(jsonPath("$.data.title").value(study.getTitle()))
                .andExpect(jsonPath("$.data.topic").value(study.getTopic()));
    }

    @Test
    public void 스터디생성() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm1!");
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(member.getId(), "프론트엔드 모집", "프론트엔드", "프론트엔드 모집합니다.", 2L, StudyState.STUDYING, RecruitState.PROCEED);

        //when, then
        mockMvc.perform(post("/study")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("프론트엔드 모집"))
                .andExpect(jsonPath("$.data.topic").value("프론트엔드"));
    }

    @Test
    public void 스터디참여() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createRefreshToken("xptmxm1!");
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyJoinRequestDto requestDto = new StudyJoinRequestDto(study.getId(), member.getId());

        //when, then
        mockMvc.perform(post("/study/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(member.getId()));
    }

    @Test
    public void 스터디게시판로드() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when, then
        mockMvc.perform(get("/study/{id}/board",study.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(3));
    }


}