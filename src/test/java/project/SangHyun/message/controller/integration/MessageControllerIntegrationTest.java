package project.SangHyun.message.controller.integration;

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
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.repository.MessageRepository;
import project.SangHyun.message.tools.MessageFactory;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class MessageControllerIntegrationTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    JwtTokenHelper accessTokenHelper;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    @DisplayName("쪽지를 전송한다.")
    public void createMessage() throws Exception {
        //given
        Member memberA = testDB.findStudyGeneralMember();
        Member memberB = testDB.findStudyApplyMember();
        String accessToken = accessTokenHelper.createToken(memberA.getEmail());
        MessageCreateRequestDto requestDto = MessageFactory.makeCreateRequestDto(null, memberA, memberB);

        //when, then
        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("쪽지 상대를 전체 조회한다.")
    public void findCommunicators() throws Exception {
        //given
        Member memberA = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(memberA.getEmail());

        //when, then
        mockMvc.perform(get("/messages")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].unReadCount").value(3L))
                .andExpect(jsonPath("$.data[1].unReadCount").value(2L));
    }

    @Test
    @DisplayName("읽지 않은 쪽지의 개수를 전체 조회한다.")
    public void countUnReadMessages() throws Exception {
        //given
        Member memberA = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(memberA.getEmail());

        //when, then
        mockMvc.perform(get("/messages/count")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(5L));
    }

    @Test
    @DisplayName("쪽지 상대 대화 내용 조회한다.")
    public void findMessages() throws Exception {
        //given
        Member memberA = testDB.findStudyGeneralMember();
        Member memberB = testDB.findStudyApplyMember();
        String accessToken = accessTokenHelper.createToken(memberA.getEmail());

        //when, then
        mockMvc.perform(get("/messages/sender")
                        .param("senderId", String.valueOf(memberB.getId()))
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2L));
    }

    @Test
    @DisplayName("전송자가 쪽지를 삭제한다.")
    public void deleteBySender() throws Exception {
        //given
        Member member = testDB.findStudyApplyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Message message = messageRepository.findAllMessagesByContent("첫 번째").get(0);

        //when, then
        mockMvc.perform(delete("/messages/sender/" + message.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("수신자가 쪽지를 삭제한다.")
    public void deleteByReceiver() throws Exception {
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Message message = messageRepository.findAllMessagesByContent("첫 번째").get(0);

        //when, then
        mockMvc.perform(delete("/messages/sender/" + message.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
