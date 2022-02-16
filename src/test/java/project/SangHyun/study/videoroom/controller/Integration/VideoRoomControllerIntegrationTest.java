package project.SangHyun.study.videoroom.controller.Integration;

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
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.videoroom.controller.dto.request.VideoRoomCreateRequestDto;
import project.SangHyun.study.videoroom.controller.dto.request.VideoRoomUpdateRequestDto;
import project.SangHyun.study.videoroom.domain.VideoRoom;
import project.SangHyun.study.videoroom.tools.VideoRoomFactory;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class VideoRoomControllerIntegrationTest {
    Member studyMember;
    Member notStudyMember;
    Study study;
    Member webAdminMember;
    Member studyAdminMember;
    Member hasNoResourceMember;

    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    JwtTokenHelper accessTokenHelper;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();

        studyMember = testDB.findStudyGeneralMember();
        notStudyMember = testDB.findNotStudyMember();
        study = testDB.findBackEndStudy();
        webAdminMember = testDB.findAdminMember();
        studyAdminMember = testDB.findStudyAdminMember();
        hasNoResourceMember = testDB.findStudyMemberNotResourceOwner();
    }

    @Test
    @DisplayName("화상회의 방을 생성한다.")
    public void createRoom() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        VideoRoomCreateRequestDto createRequestDto = VideoRoomFactory.createRequestDto(studyMember.getId());

        //when, then
        mockMvc.perform(post("/study/{studyId}/videoroom", study.getId())
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
        mockMvc.perform(post("/study/{studyId}/videoroom", study.getId())
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
        VideoRoom videoRoom = testDB.findJPAVideoRoom();
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("React Query 회의 방");

        //when, then
        mockMvc.perform(put("/study/{studyId}/videoroom/{roomId}", study.getId(), videoRoom.getRoomId())
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
        VideoRoom videoRoom = testDB.findJPAVideoRoom();
        String accessToken = accessTokenHelper.createToken(studyAdminMember.getEmail());
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("React Query 회의 방");

        //when, then
        mockMvc.perform(put("/study/{studyId}/videoroom/{roomId}", study.getId(), videoRoom.getRoomId())
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
        VideoRoom videoRoom = testDB.findJPAVideoRoom();
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("React Query 회의");

        //when, then
        mockMvc.perform(put("/study/{studyId}/videoroom/{roomId}", study.getId(), videoRoom.getRoomId())
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
        VideoRoom videoRoom = testDB.findJPAVideoRoom();
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("React Query 회의 방");

        //when, then
        mockMvc.perform(put("/study/{studyId}/videoroom/{roomId}", study.getId(), videoRoom.getRoomId())
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
        VideoRoom videoRoom = testDB.findJPAVideoRoom();
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/videoroom/{roomId}", study.getId(), videoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 관리자는 어떤 화상회의 방이든 제거할 수 있다.")
    public void destroyRoom2() throws Exception {
        //given
        VideoRoom videoRoom = testDB.findJPAVideoRoom();
        String accessToken = accessTokenHelper.createToken(studyAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/videoroom/{roomId}", study.getId(), videoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 어떤 화상회의 방이든 제거할 수 있다.")
    public void destroyRoom3() throws Exception {
        //given
        VideoRoom videoRoom = testDB.findJPAVideoRoom();
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/videoroom/{roomId}", study.getId(), videoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 화상회의 방 생성자가 아니라면 화상회의 방을 제거할 수 없다.")
    public void destroyRoom_fail() throws Exception {
        //given
        VideoRoom videoRoom = testDB.findJPAVideoRoom();
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}/videoroom/{roomId}", study.getId(), videoRoom.getRoomId())
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
        mockMvc.perform(get("/study/{studyId}/videoroom", study.getId())
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
        mockMvc.perform(get("/study/{studyId}/videoroom", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}
