package project.SangHyun.member.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.tools.member.MemberFactory;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.member.service.impl.MemberServiceImpl;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class MemberServiceUnitTest {

    @InjectMocks
    MemberServiceImpl memberService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    StudyJoinRepository studyJoinRepository;

    @Test
    @DisplayName("회원 정보를 로드한다.")
    public void loadUserInfo() throws Exception {
        //given
        MemberDetails memberDetails = MemberFactory.makeMemberDetails();
        Member member = MemberFactory.makeTestMember();
        MemberInfoResponseDto ExpectResult = MemberFactory.makeInfoResponseDto(member);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(studyJoinRepository.findStudyInfoByMemberId(member.getId())).willReturn(List.of());

        //when
        MemberInfoResponseDto ActualResult = memberService.getMemberInfo(memberDetails);

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("회원 프로필 정보를 로드한다.")
    public void loadProfile() throws Exception {
        //given
        Member member = MemberFactory.makeTestMember();
        MemberProfileResponseDto ExpectResult = MemberFactory.makeProfileResponseDto(member);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        //when
        MemberProfileResponseDto ActualResult = memberService.getProfile(member.getId());

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("회원 프로필 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        Member member = MemberFactory.makeTestMember();
        MemberUpdateRequestDto requestDto = MemberFactory.makeUpdateRequestDto("테스터 변경");

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        //when
        MemberUpdateResponseDto ActualResult = memberService.updateMember(member.getId(), requestDto);

        //then
        Assertions.assertEquals("테스터 변경", ActualResult.getNickname());
    }

    @Test
    @DisplayName("회원 프로필 정보를 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        Member member = MemberFactory.makeTestMember();
        MemberDeleteResponseDto ExpectResult = MemberFactory.makeDeleteResponseDto(member);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));
        willDoNothing().given(memberRepository).delete(member);

        //when
        MemberDeleteResponseDto ActualResult = memberService.deleteMember(member.getId());

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
    }
}