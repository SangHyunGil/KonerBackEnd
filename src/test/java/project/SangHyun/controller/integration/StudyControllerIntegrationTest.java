package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import project.SangHyun.factory.study.StudyFactory;
import project.SangHyun.study.study.controller.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.controller.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.domain.StudyCategory;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudyControllerIntegrationTest extends ControllerIntegrationTest{

    @Test
    @DisplayName("모든 스터디 정보를 로드한다.")
    public void loadStudyInfo() throws Exception {
        //given

        //when, then
        mockMvc.perform(get("/api/studies")
                        .param("studyId", String.valueOf(Long.MAX_VALUE))
                        .param("department", String.valueOf(StudyCategory.CSE))
                        .param("size", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.numberOfElements").value(2))
                .andExpect(jsonPath("$.data.hasNext").value(false));
    }

    @Test
    @DisplayName("스터디에 대한 세부정보를 로드한다.")
    public void loadStudyDetail() throws Exception {
        //given

        //when, then
        mockMvc.perform(get("/api/studies/{id}", backendStudy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(backendStudy.getId()))
                .andExpect(jsonPath("$.data.title").value(backendStudy.getTitle()));
    }

    @Test
    @DisplayName("스터디를 생성한다.")
    public void createStudy() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyCreateRequestDto requestDto = StudyFactory.makeCreateRequestDto(studyMember);

        //when, then
        mockMvc.perform(multipart("/api/studies")
                        .file((MockMultipartFile) requestDto.getProfileImg())
                        .param("memberId", String.valueOf(requestDto.getMemberId()))
                        .param("title", requestDto.getTitle())
                        .param("description", requestDto.getDescription())
                        .param("startDate", requestDto.getStartDate())
                        .param("endDate", requestDto.getEndDate())
                        .param("tags", requestDto.getTags().toArray(new String[requestDto.getTags().size()]))
                        .param("department", String.valueOf(requestDto.getDepartment()))
                        .param("headCount", String.valueOf(requestDto.getHeadCount()))
                        .param("studyMethod", String.valueOf(requestDto.getStudyMethod()))
                        .param("studyState", String.valueOf(requestDto.getStudyState()))
                        .param("recruitState", String.valueOf(requestDto.getRecruitState()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("프론트엔드 모집"));
    }

    @Test
    @DisplayName("인증이 미완료된 회원은 스터디를 생성할 수 없다.")
    public void createStudy_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notAuthMember.getEmail());
        StudyCreateRequestDto requestDto = StudyFactory.makeCreateRequestDto(notAuthMember);

        //when, then
        mockMvc.perform(post("/api/studies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 게시판을 로드한다.")
    public void loadStudyBoard() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{id}/boards", backendStudy.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(3));
    }

    @Test
    @DisplayName("스터디 생성자는 스터디의 정보를 업데이트할 수 있다.")
    public void updateStudy() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());
        StudyUpdateRequestDto requestDto = StudyFactory.makeUpdateRequestDto("모바일 모집", List.of("모바일"));

        //when, then
        mockMvc.perform(multipart("/api/studies/{studyId}", backendStudy.getId())
                        .file((MockMultipartFile) requestDto.getProfileImg())
                        .param("title", requestDto.getTitle())
                        .param("description", requestDto.getDescription())
                        .param("startDate", requestDto.getStartDate())
                        .param("endDate", requestDto.getEndDate())
                        .param("tags", requestDto.getTags().toArray(new String[requestDto.getTags().size()]))
                        .param("headCount", String.valueOf(requestDto.getHeadCount()))
                        .param("studyMethod", String.valueOf(requestDto.getStudyMethod()))
                        .param("studyState", String.valueOf(requestDto.getStudyState()))
                        .param("recruitState", String.valueOf(requestDto.getRecruitState()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("PUT");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("모바일 모집"));
    }

    @Test
    @DisplayName("웹 관리자는 부적절한 스터디의 정보를 업데이트할 수 있다.")
    public void updateStudy_admin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());
        StudyUpdateRequestDto requestDto = StudyFactory.makeUpdateRequestDto("모바일 모집", List.of("모바일"));

        //when, then
        mockMvc.perform(multipart("/api/studies/{studyId}", backendStudy.getId())
                        .file((MockMultipartFile) requestDto.getProfileImg())
                        .param("title", requestDto.getTitle())
                        .param("description", requestDto.getDescription())
                        .param("startDate", requestDto.getStartDate())
                        .param("endDate", requestDto.getEndDate())
                        .param("tags", requestDto.getTags().toArray(new String[requestDto.getTags().size()]))
                        .param("headCount", String.valueOf(requestDto.getHeadCount()))
                        .param("studyMethod", String.valueOf(requestDto.getStudyMethod()))
                        .param("studyState", String.valueOf(requestDto.getStudyState()))
                        .param("recruitState", String.valueOf(requestDto.getRecruitState()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("PUT");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("모바일 모집"));
    }

    @Test
    @DisplayName("스터디 권한이 없는 사람에 의한 스터디의 정보를 업데이트는 실패한다.")
    public void updateStudy_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyUpdateRequestDto requestDto = StudyFactory.makeUpdateRequestDto("모바일 모집", List.of("모바일"));

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 생성자는 스터디를 삭제할 수 있다.")
    public void deleteStudy() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyCreator.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}", backendStudy.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 부적절한 스터디를 삭제할 수 있다.")
    public void deleteStudy_admin() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}", backendStudy.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 권한이 없는 사람에 의한 스터디를 삭제는 실패한다.")
    public void deleteStudy_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}", backendStudy.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}