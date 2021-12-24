package project.SangHyun.study.studyjoin.controller.integration;

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
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.study.tools.StudyFactory;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyjoin.tools.StudyJoinFactory;
import project.SangHyun.utils.service.RedisService;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    JwtTokenHelper accessTokenHelper;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    @DisplayName("스터디에 참여한다.")
    public void join() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyJoinRequestDto requestDto = StudyJoinFactory.makeCreateDto(study, member);

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
    @DisplayName("인증이 미완료된 회원은 스터디에 참여할 수 없다.")
    public void join_fail() throws Exception {
        //given
        Member member = testDB.findNotAuthMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyJoinRequestDto requestDto = StudyJoinFactory.makeCreateDto(study, member);

        //when, then
        mockMvc.perform(post("/study/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디에 참여한 스터디원들의 정보를 가져온다.")
    public void findStudyMember() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        //when, then
        mockMvc.perform(get("/study/{studyId}/member", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}