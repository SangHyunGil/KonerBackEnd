package project.SangHyun.controller.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExceptionControllerIntegrationTest extends ControllerIntegrationTest {

    @Test
    @DisplayName("인증에 실패한다.")
    public void auth_fail() throws Exception {
        //given

        //when, then
        mockMvc.perform(post("/exception/entry"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("인가에 실패한다.")
    public void auth_fail2() throws Exception {
        //given

        //when, then
        mockMvc.perform(post("/exception/denied"))
                .andExpect(status().is4xxClientError());
    }
}