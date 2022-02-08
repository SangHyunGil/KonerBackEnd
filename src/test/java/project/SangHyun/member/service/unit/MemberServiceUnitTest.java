package project.SangHyun.member.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.SangHyun.common.helper.FileStoreHelper;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberChangePwRequestDto;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.*;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.member.tools.member.MemberFactory;
import project.SangHyun.member.tools.sign.SignFactory;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class MemberServiceUnitTest {
    Member authMember;
    Member notAuthMember;

    @InjectMocks
    MemberService memberService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    FileStoreHelper fileStoreHelper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    RedisHelper redisHelper;

    @BeforeEach
    public void init() {
        authMember = SignFactory.makeAuthTestMember();
        notAuthMember = SignFactory.makeTestNotAuthMember();
    }

    @Test
    @DisplayName("회원 정보를 로드한다.")
    public void loadUserInfo() throws Exception {
        //given
        MemberDetails memberDetails = MemberFactory.makeMemberDetails(authMember.getId());
        MemberInfoResponseDto ExpectResult = MemberFactory.makeInfoResponseDto(authMember);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(authMember));

        //when
        MemberInfoResponseDto ActualResult = memberService.getMemberInfo(memberDetails);

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("회원 프로필 정보를 로드한다.")
    public void loadProfile() throws Exception {
        //given
        MemberProfileResponseDto ExpectResult = MemberFactory.makeProfileResponseDto(authMember);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(authMember));

        //when
        MemberProfileResponseDto ActualResult = memberService.getProfile(authMember.getId());

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("회원 프로필 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        MemberUpdateRequestDto requestDto = MemberFactory.makeUpdateRequestDto("테스터 변경");

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(authMember));
        given(fileStoreHelper.storeFile(requestDto.getProfileImg())).willReturn("C:\\Users\\Family\\Pictures\\Screenshots\\1.png");

        //when
        MemberUpdateResponseDto ActualResult = memberService.updateMember(authMember.getId(), requestDto);

        //then
        Assertions.assertEquals("테스터 변경", ActualResult.getNickname());
    }

    @Test
    @DisplayName("회원 프로필 정보를 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        MemberDeleteResponseDto ExpectResult = MemberFactory.makeDeleteResponseDto(authMember);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(authMember));
        willDoNothing().given(memberRepository).delete(authMember);

        //when
        MemberDeleteResponseDto ActualResult = memberService.deleteMember(authMember.getId());

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("비밀번호 변경을 진행한다.")
    public void changePW() throws Exception {
        //given
        MemberChangePwRequestDto requestDto = SignFactory.makeChangePwRequestDto(authMember.getEmail(), "change");

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(authMember));
        given(passwordEncoder.encode(any())).willReturn("encodedChangedPW");
        willDoNothing().given(redisHelper).delete(any());

        //when
        MemberChangePwResponseDto ActualResult = memberService.changePassword(requestDto);

        //then
        Assertions.assertEquals(1L, ActualResult.getId());
    }
}