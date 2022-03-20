package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import project.SangHyun.factory.studyjoin.StudyJoinFactory;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinCreateRequestDto;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinUpdateAuthorityRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudyJoinControllerIntegrationTest extends ControllerIntegrationTest{

    @Test
    @DisplayName("스터디에 참여한다.")
    public void applyJoin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());
        StudyJoinCreateRequestDto requestDto = StudyJoinFactory.makeCreateRequestDto("빠르게 지원합니다.");

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), notStudyMember.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("인증이 미완료된 회원은 스터디에 참여할 수 없다.")
    public void applyJoin_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notAuthMember.getEmail());

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), notAuthMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 생성자는 스터디 참가를 수락할 수 있다.")
    public void acceptJoin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), studyApplyMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디 참가를 수락할 수 있다.")
    public void acceptJoin2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), studyApplyMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 일반 회원은 스터디 참가에 실패한다.")
    public void acceptJoin_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), studyApplyMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디에 참여하지 않은 회원은 스터디 참가에 실패한다.")
    public void acceptJoin_fail2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), notStudyMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 생성자는 스터디 참가를 거절할 수 있다.")
    public void rejectJoin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), studyApplyMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디 참가를 거절할 수 있다.")
    public void rejectJoin2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), studyApplyMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 일반 회원은 스터디 거절에 실패한다.")
    public void rejectJoin_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), studyApplyMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디에 참여하지 않은 회원은 스터디 거절에 실패한다.")
    public void rejectJoin_fail2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/joins/{memberId}", backendStudy.getId(), studyApplyMember.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디에 참여한 스터디원들의 정보를 가져온다.")
    public void findStudyMember() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/members", backendStudy.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("참여한 모든 스터디를 조회한다.")
    public void findJoinedStudy() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/joins")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("참여한 모든 스터디를 조회한다.")
    public void updateAuthority() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(hasNoResourceMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/joins")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("스터디 생성자 권한 수정을 진행한다.")
    public void changeAuthority() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());
        StudyJoinUpdateAuthorityRequestDto requestDto = StudyJoinFactory.makeUpdateAuthorityRequestDto(StudyRole.ADMIN);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/authorities/{memberId}", backendStudy.getId(), studyMember.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 생성자로 승급하려 한다면 실패한다.")
    public void changeAuthority_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());
        StudyJoinUpdateAuthorityRequestDto requestDto = StudyJoinFactory.makeUpdateAuthorityRequestDto(StudyRole.CREATOR);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/authorities/{memberId}", backendStudy.getId(), studyMember.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("스터디 생성자의 권한 변경은 실패한다.")
    public void changeAuthority_fail2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());
        StudyJoinUpdateAuthorityRequestDto requestDto = StudyJoinFactory.makeUpdateAuthorityRequestDto(StudyRole.ADMIN);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/authorities/{memberId}", backendStudy.getId(), studyCreator.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("스터디 생성자가 아니라면 권한 변경은 실패한다.")
    public void changeAuthority_fail3() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyAdminMember.getEmail());
        StudyJoinUpdateAuthorityRequestDto requestDto = StudyJoinFactory.makeUpdateAuthorityRequestDto(StudyRole.ADMIN);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/authorities/{memberId}", backendStudy.getId(), studyMember.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}