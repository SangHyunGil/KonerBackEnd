package project.SangHyun.domain.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.service.MemberService;
import project.SangHyun.dto.request.member.MemberUpdateInfoRequestDto;
import project.SangHyun.dto.response.member.MemberDeleteResponseDto;
import project.SangHyun.dto.response.member.MemberInfoResponseDto;
import project.SangHyun.dto.response.member.MemberProfileResponseDto;
import project.SangHyun.dto.response.member.MemberUpdateInfoResponseDto;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceIntegrationImplTest {

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
    public void 회원정보로드() throws Exception {
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
    public void 프로필로드() throws Exception {
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
    public void 회원정보수정() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        MemberUpdateInfoRequestDto requestDto = new MemberUpdateInfoRequestDto("xptmxm1!", "철수", "기계공학부");

        //when
        MemberUpdateInfoResponseDto ActualResult = memberService.updateMemberInfo(member.getId(), requestDto);

        //then
        Assertions.assertEquals("xptmxm1!", ActualResult.getEmail());
        Assertions.assertEquals("철수", ActualResult.getNickname());
        Assertions.assertEquals("기계공학부", ActualResult.getDepartment());
    }

    @Test
    public void 회원삭제() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);

        //when
        MemberDeleteResponseDto ActualResult = memberService.deleteMember(member.getId());
        List<Member> members = memberRepository.findAll();
        //then
        Assertions.assertEquals(2, members.size());
        Assertions.assertEquals("xptmxm1!", ActualResult.getEmail());
        Assertions.assertEquals("승범", ActualResult.getNickname());
    }
}