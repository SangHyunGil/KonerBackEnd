package project.SangHyun.study.study.controller.integration;

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
import project.SangHyun.common.helper.RedisHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.tools.StudyFactory;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class StudyControllerIntegrationTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RedisHelper redisHelper;
    @Autowired
    JwtTokenHelper accessTokenHelper;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    @DisplayName("모든 스터디 정보를 로드한다.")
    public void loadStudyInfo() throws Exception {
        //given

        //when, then
        mockMvc.perform(get("/study")
                        .param("studyId", String.valueOf(Long.MAX_VALUE))
                        .param("department", "컴퓨터공학과")
                        .param("size", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.numberOfElements").value(2))
                .andExpect(jsonPath("$.data.hasNext").value(false));;
    }

    @Test
    @DisplayName("스터디에 대한 세부정보를 로드한다.")
    public void loadStudyDetail() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();

        //when, then
        mockMvc.perform(get("/study/{id}", study.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyId").value(study.getId()))
                .andExpect(jsonPath("$.data.title").value(study.getTitle()));
    }

    @Test
    @DisplayName("스터디를 생성한다.")
    public void createStudy() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyCreateRequestDto requestDto = StudyFactory.makeCreateRequestDto(member);

        //when, then
        mockMvc.perform(multipart("/study")
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("memberId", String.valueOf(requestDto.getMemberId()))
                        .param("title", requestDto.getTitle())
                        .param("content", requestDto.getContent())
                        .param("schedule", requestDto.getSchedule())
                        .param("tags", requestDto.getTags().toArray(new String[requestDto.getTags().size()]))
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("프론트엔드 모집"));
    }

    @Test
    @DisplayName("인증이 미완료된 회원은 스터디를 생성할 수 없다.")
    public void createStudy_fail() throws Exception {
        //given
        Member member = testDB.findNotAuthMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyCreateRequestDto requestDto = StudyFactory.makeCreateRequestDto(member);

        //when, then
        mockMvc.perform(post("/study")
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
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        //when, then
        mockMvc.perform(get("/study/{id}/board",study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(3));
    }

    @Test
    @DisplayName("스터디 생성자는 스터디의 정보를 업데이트할 수 있다.")
    public void updateStudy() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findStudyCreatorMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyUpdateRequestDto requestDto = StudyFactory.makeUpdateRequestDto("모바일 모집", List.of("모바일"));

        //when, then
        mockMvc.perform(multipart("/study/{studyId}", study.getId())
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("title", requestDto.getTitle())
                        .param("content", requestDto.getContent())
                        .param("schedule", requestDto.getSchedule())
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
    @DisplayName("관리자는 부적절한 스터디의 정보를 업데이트할 수 있다.")
    public void updateStudy_admin() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findAdminMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyUpdateRequestDto requestDto = StudyFactory.makeUpdateRequestDto("모바일 모집", List.of("모바일"));

        //when, then
        mockMvc.perform(multipart("/study/{studyId}", study.getId())
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("title", requestDto.getTitle())
                        .param("content", requestDto.getContent())
                        .param("schedule", requestDto.getSchedule())
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
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        StudyUpdateRequestDto requestDto = StudyFactory.makeUpdateRequestDto("모바일 모집", List.of("모바일"));

        //when, then
        mockMvc.perform(put("/study/{studyId}", study.getId())
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
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findStudyCreatorMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("관리자는 부적절한 스터디를 삭제할 수 있다.")
    public void deleteStudy_admin() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findAdminMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 권한이 없는 사람에 의한 스터디를 삭제는 실패한다.")
    public void deleteStudy_fail() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        Member member = testDB.findGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());

        //when, then
        mockMvc.perform(delete("/study/{studyId}", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}