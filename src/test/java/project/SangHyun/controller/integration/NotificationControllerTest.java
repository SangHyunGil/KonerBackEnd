package project.SangHyun.controller.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest extends ControllerIntegrationTest{

    @Test
    @DisplayName("SSE에 연결을 진행한다.")
    public void subscribe() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/subscribe")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모든 알림을 조회한다.")
    public void findAllNotifications() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyApplyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/notifications")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(5));

    }

    @Test
    @DisplayName("안읽은 모든 알림 개수를 조회한다.")
    public void countUnReadNotifications() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyApplyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/notifications/count")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(5L));
    }

    @Test
    @DisplayName("메세지를 조회하면 메세지를 읽게 된다.")
    public void countUnReadNotifications2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyApplyMember.getEmail());

        notificationService.findAllNotifications(studyApplyMember.getId());

        //when, then
        mockMvc.perform(get("/api/notifications/count")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(0L));
    }
}