package project.SangHyun.study.videoroom.controller.Integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import project.SangHyun.controller.integration.ControllerIntegrationTest;
import project.SangHyun.factory.videoroom.VideoRoomFactory;
import project.SangHyun.study.videoroom.controller.dto.request.VideoRoomCreateRequestDto;
import project.SangHyun.study.videoroom.controller.dto.request.VideoRoomUpdateRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VideoRoomControllerIntegrationTest extends ControllerIntegrationTest {

    @Test
    @DisplayName("화상회의 방을 생성한다.")
    public void createRoom() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        VideoRoomCreateRequestDto createRequestDto = VideoRoomFactory.createRequestDto(studyMember.getId());

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/videorooms", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("백엔드 화상회의"));
    }

    @Test
    @DisplayName("스터디 멤버가 아닌 회원은 해당 스터디에 화상회의 방을 생성할 수 없다.")
    public void createRoom_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());
        VideoRoomCreateRequestDto createRequestDto = VideoRoomFactory.createRequestDto(notStudyMember.getId());

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/videorooms", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("화상회의 방을 수정한다.")
    public void editRoom() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("React Query 회의 방");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/videorooms/{roomId}", backendStudy.getId(), jpaVideoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 어떤 스터디 화상회의 방이든 수정할 수 있다.")
    public void editRoom2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyAdminMember.getEmail());
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("React Query 회의 방");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/videorooms/{roomId}", backendStudy.getId(), jpaVideoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 어떤 스터디 화상회의 방이든 수정할 수 있다.")
    public void editRoom3() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("React Query 회의");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/videorooms/{roomId}", backendStudy.getId(), jpaVideoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 화상회의 방 생성자가 아니라면 해당 방을 수정할 수 없다.")
    public void editRoom_fail2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("React Query 회의 방");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/videorooms/{roomId}", backendStudy.getId(), jpaVideoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("화상회의 방을 제거한다.")
    public void destroyRoom() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/videorooms/{roomId}", backendStudy.getId(), jpaVideoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 어떤 화상회의 방이든 제거할 수 있다.")
    public void destroyRoom2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/videorooms/{roomId}", backendStudy.getId(), jpaVideoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 어떤 화상회의 방이든 제거할 수 있다.")
    public void destroyRoom3() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/videorooms/{roomId}", backendStudy.getId(), jpaVideoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 화상회의 방 생성자가 아니라면 화상회의 방을 제거할 수 없다.")
    public void destroyRoom_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/videorooms/{roomId}", backendStudy.getId(), jpaVideoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("화상회의 방을 모두 조회한다.")
    public void findRooms() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/videorooms", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 멤버가 아닌 회원은 해당 스터디 화상회의 방을 조회할 수 없다.")
    public void findRooms_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/videorooms", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}
