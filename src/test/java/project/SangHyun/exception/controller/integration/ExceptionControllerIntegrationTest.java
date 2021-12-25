package project.SangHyun.exception.controller.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ExceptionControllerIntegrationTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

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