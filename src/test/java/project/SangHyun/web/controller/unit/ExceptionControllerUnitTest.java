package project.SangHyun.web.controller.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.SignService;
import project.SangHyun.web.controller.ExceptionController;
import project.SangHyun.web.controller.SignController;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
    public void 인증실패() throws Exception {
        //given

        //when, then
        mockMvc.perform(post("/exception/entry"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 인가거부() throws Exception {
        //given

        //when, then
        mockMvc.perform(post("/exception/denied"))
                .andExpect(status().is4xxClientError());
    }
}