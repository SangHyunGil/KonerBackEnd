package project.SangHyun.message.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.member.domain.Member;
import project.SangHyun.message.controller.MessageController;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.dto.response.MessageCreateResponseDto;
import project.SangHyun.message.dto.response.MessageDeleteResponseDto;
import project.SangHyun.message.service.MessageService;
import project.SangHyun.message.tools.MessageFactory;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MessageControllerUnitTest {
    String accessToken;
    Member memberA;
    Member memberB;

    MockMvc mockMvc;
    @InjectMocks
    MessageController messageController;
    @Mock
    MessageService messageService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();

        accessToken = "accessToken";
        memberA = MessageFactory.makeTestAuthMember();
        memberB = MessageFactory.makeTestAuthMember2();
    }

    @Test
    @DisplayName("쪽지를 전송한다.")
    public void createMessage() throws Exception {
        //given
        Long messageId = 1L;
        Message message = MessageFactory.makeTestMessage(messageId, memberA, memberB);
        MessageCreateRequestDto requestDto = MessageFactory.makeCreateRequestDto(messageId, memberA, memberB);
        MessageCreateResponseDto responseDto = MessageFactory.makeCreateResponseDto(message);
        SingleResult<MessageCreateResponseDto> ExpectResult = MessageFactory.makeSingleResult(responseDto);

        //mocking
        given(messageService.createMessage(requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("전송자가 쪽지를 삭제한다.")
    public void deleteBySender() throws Exception {
        //given
        Long messageId = 1L;
        Message message = MessageFactory.makeTestMessage(1L, memberA, memberB);
        MessageDeleteResponseDto responseDto = MessageFactory.makeDeleteResponseDto(message);
        SingleResult<MessageDeleteResponseDto> ExpectResult = MessageFactory.makeSingleResult(responseDto);

        //mocking
        given(messageService.deleteBySender(messageId)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/messages/sender/"+messageId)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("수신자가 쪽지를 삭제한다.")
    public void deleteByReceiver() throws Exception {
        //given
        Long messageId = 1L;
        Message message = MessageFactory.makeTestMessage(1L, memberA, memberB);
        MessageDeleteResponseDto responseDto = MessageFactory.makeDeleteResponseDto(message);
        SingleResult<MessageDeleteResponseDto> ExpectResult = MessageFactory.makeSingleResult(responseDto);

        //mocking
        given(messageService.deleteByReceiver(messageId)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/messages/receiver/"+messageId)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}
