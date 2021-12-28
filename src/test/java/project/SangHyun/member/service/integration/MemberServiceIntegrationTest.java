package project.SangHyun.member.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;
import project.SangHyun.member.tools.member.MemberFactory;

import java.util.List;

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
        MemberDetails memberDetails = MemberFactory.makeMemberDetails();

        //when
        MemberInfoResponseDto ActualResult = memberService.getMemberInfo(memberDetails);

        //then
        Assertions.assertEquals("xptmxm1!", ActualResult.getEmail());
    }

    @Test
    @DisplayName("회원 프로필 정보를 로드한다.")
    public void loadProfile() throws Exception {
        //given
        Member member = testDB.findGeneralMember();

        //when
        MemberProfileResponseDto ActualResult = memberService.getProfile(member.getId());

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("회원 프로필 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        MemberUpdateRequestDto requestDto = MemberFactory.makeUpdateRequestDto("철수");

        //when
        MemberUpdateResponseDto ActualResult = memberService.updateMember(member.getId(), requestDto);

        //then

        Assertions.assertEquals("철수", ActualResult.getNickname());;
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        Member member = testDB.findGeneralMember();

        //when
        MemberDeleteResponseDto ActualResult = memberService.deleteMember(member.getId());
        List<Member> members = memberRepository.findAll();

        //then
        Assertions.assertEquals(7, members.size());
        Assertions.assertEquals(member.getId(), ActualResult.getMemberId());
    }
}