package project.SangHyun.member.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("회원 정보를 로드한다.")
    public void loadUserInfo() throws Exception {
        //given
        MemberDetails memberDetails = new MemberDetails(1L,"xptmxm1!", "encodedPW",
                Collections.singletonList(new SimpleGrantedAuthority(MemberRole.ROLE_MEMBER.toString())));

        //when
        MemberInfoResponseDto ActualResult = memberService.getMemberInfo(memberDetails);


        //then
        Assertions.assertEquals("xptmxm1!", ActualResult.getEmail());
        Assertions.assertEquals("승범", ActualResult.getNickname());
        Assertions.assertEquals("컴공", ActualResult.getDepartment());
        Assertions.assertEquals(0, ActualResult.getStudyInfos().size());
    }

    @Test
    @DisplayName("회원 프로필 정보를 로드한다.")
    public void loadProfile() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);

        //when
        MemberProfileResponseDto ActualResult = memberService.getProfile(member.getId());

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getMemberId());
        Assertions.assertEquals(member.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(member.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(member.getDepartment(), ActualResult.getDepartment());
    }

    @Test
    @DisplayName("회원 프로필 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto("xptmxm1!", "철수", "기계공학부");

        //when
        MemberUpdateResponseDto ActualResult = memberService.updateMember(member.getId(), requestDto);

        //then
        Assertions.assertEquals("xptmxm1!", ActualResult.getEmail());
        Assertions.assertEquals("철수", ActualResult.getNickname());
        Assertions.assertEquals("기계공학부", ActualResult.getDepartment());
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);

        //when
        MemberDeleteResponseDto ActualResult = memberService.deleteMember(member.getId());
        List<Member> members = memberRepository.findAll();
        //then
        Assertions.assertEquals(4, members.size());
        Assertions.assertEquals("xptmxm1!", ActualResult.getEmail());
        Assertions.assertEquals("승범", ActualResult.getNickname());
    }
}