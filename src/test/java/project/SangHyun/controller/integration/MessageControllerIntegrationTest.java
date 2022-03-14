package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import project.SangHyun.factory.message.MessageFactory;
import project.SangHyun.message.controller.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.domain.Message;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerIntegrationTest extends ControllerIntegrationTest {

    @Test
    @DisplayName("쪽지를 전송한다.")
    public void createMessage() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(anotherStudyMember.getEmail());
        MessageCreateRequestDto requestDto = MessageFactory.makeCreateRequestDto(null, anotherStudyMember, studyMember);

        //when, then
        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("쪽지 상대를 전체 조회한다.")
    public void findCommunicators() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

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
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

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
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/messages/sender")
                        .param("senderId", String.valueOf(studyApplyMember.getId()))
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2L));
    }

    @Test
    @DisplayName("전송자가 쪽지를 삭제한다.")
    public void deleteBySender() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyApplyMember.getEmail());
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
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        Message message = messageRepository.findAllMessagesByContent("첫 번째").get(0);

        //when, then
        mockMvc.perform(delete("/messages/sender/" + message.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
