package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.controller.dto.request.ChangePwRequestDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.member.service.dto.response.MemberDto;
import project.SangHyun.member.service.dto.request.MemberUpdateDto;
import project.SangHyun.factory.member.MemberFactory;
import project.SangHyun.factory.sign.SignFactory;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceIntegrationTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TestDB testDB;
    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("회원 정보를 로드한다.")
    public void loadUserInfo() throws Exception {
        //given
        MemberDetails memberDetails = MemberFactory.makeMemberDetails(testDB.findGeneralMember().getId());

        //when
        MemberDto ActualResult = memberService.getMemberInfo(memberDetails);

        //then
        Assertions.assertEquals("xptmxm1!", ActualResult.getEmail());
    }

    @Test
    @DisplayName("회원 프로필 정보를 로드한다.")
    public void loadProfile() throws Exception {
        //given
        Member member = testDB.findGeneralMember();

        //when
        MemberDto ActualResult = memberService.getProfile(member.getId());

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("회원 프로필 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        MemberUpdateDto requestDto = MemberFactory.makeUpdateDto("철수", "철수 자기소개입니다.");

        //when
        MemberDto ActualResult = memberService.updateMember(member.getId(), requestDto);

        //then

        Assertions.assertEquals("철수", ActualResult.getNickname());;
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        List<Member> prevMembers = memberRepository.findAll();
        persistenceContextClear();

        //when
        memberService.deleteMember(member.getId());
        List<Member> laterMembers = memberRepository.findAll();

        //then
        Assertions.assertEquals(1, prevMembers.size()-laterMembers.size());
    }

    @Test
    @DisplayName("비밀번호를 변경한다.")
    public void changePW() throws Exception {
        //given
        Member member = SignFactory.makeAuthTestMember();
        ChangePwRequestDto requestDto = SignFactory.makeChangePwRequestDto(member.getEmail(), "change");

        //when, then
        Assertions.assertDoesNotThrow(() -> memberService.changePassword(requestDto));
    }

    private void persistenceContextClear() {
        em.flush();
        em.clear();
    }
}