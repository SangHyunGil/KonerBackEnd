package project.SangHyun.controller.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.TestDB;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.notification.controller.NotificationController;
import project.SangHyun.notification.service.NotificationService;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class NotificationControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JwtTokenHelper accessTokenHelper;
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationController notificationController;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    @DisplayName("SSE에 연결을 진행한다.")
    public void subscribe() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        //when, then
        mockMvc.perform(get("/subscribe")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모든 알림을 조회한다.")
    public void findAllNotifications() throws Exception {
        //given
        Member member = testDB.findStudyApplyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        //when, then
        mockMvc.perform(get("/notification")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(5));

    }

    @Test
    @DisplayName("안읽은 모든 알림 개수를 조회한다.")
    public void countUnReadNotifications() throws Exception {
        //given
        Member member = testDB.findStudyApplyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        //when, then
        mockMvc.perform(get("/notification/count")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(5L));
    }

    @Test
    @DisplayName("메세지를 조회하면 메세지를 읽게 된다.")
    public void countUnReadNotifications2() throws Exception {
        //given
        Member member = testDB.findStudyApplyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        notificationService.findAllNotifications(member.getId());

        //when, then
        mockMvc.perform(get("/notification/count")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(0L));
    }
}