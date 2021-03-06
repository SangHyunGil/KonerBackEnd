package project.SangHyun.controller.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.common.advice.controller.ExceptionController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExceptionControllerUnitTest {
    MockMvc mockMvc;
    @InjectMocks
    ExceptionController exceptionController;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(exceptionController).build();
    }

    @Test
    @DisplayName("인증에 실패한다.")
    public void auth_fail() throws Exception {
        //given

        //when, then
        mockMvc.perform(post("/api/exception/entry"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("인가에 실패한다.")
    public void auth_fail2() throws Exception {
        //given

        //when, then
        mockMvc.perform(post("/api//exception/denied"))
                .andExpect(status().is4xxClientError());
    }
}