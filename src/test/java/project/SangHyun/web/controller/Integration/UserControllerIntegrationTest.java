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
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.dto.request.MemberUpdateInfoRequestDto;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;
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
    public void 유저정보로드_성공() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createAccessToken("test");
        Member member = memberRepository.findByEmail("test").orElseThrow(MemberNotFoundException::new);

        //when, then
        mockMvc.perform(post("/users/info")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(member.getId()))
                .andExpect(jsonPath("$.data.email").value("test"))
                .andExpect(jsonPath("$.data.nickname").value("승범"))
                .andExpect(jsonPath("$.data.department").value("컴공"));
    }

    @Test
    public void 유저정보로드_실패() throws Exception {
        //given
        String accessToken = "Wrong Token";

        //when, then
        mockMvc.perform(post("/users/info")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void 유저프로필로드() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createAccessToken("test");
        Member member = memberRepository.findByEmail("test").orElseThrow(MemberNotFoundException::new);

        //when, then
        mockMvc.perform(get("/users/{id}", member.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(member.getId()))
                .andExpect(jsonPath("$.data.email").value(member.getEmail()))
                .andExpect(jsonPath("$.data.nickname").value(member.getNickname()))
                .andExpect(jsonPath("$.data.department").value(member.getDepartment()));
    }

    @Test
    public void 유저프로필수정() throws Exception {
        //given
        String accessToken = jwtTokenProvider.createAccessToken("test");
        MemberUpdateInfoRequestDto requestDto = new MemberUpdateInfoRequestDto("test", "철수", "기공");
        Member member = memberRepository.findByEmail("test").orElseThrow(MemberNotFoundException::new);

        //when, then
        mockMvc.perform(put("/users/{id}", member.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(member.getId()))
                .andExpect(jsonPath("$.data.email").value("test"))
                .andExpect(jsonPath("$.data.nickname").value("철수"))
                .andExpect(jsonPath("$.data.department").value("기공"));
    }
}