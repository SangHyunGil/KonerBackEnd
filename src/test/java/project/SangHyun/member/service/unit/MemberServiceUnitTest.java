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
import project.SangHyun.common.helper.AwsS3BucketHelper;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.controller.dto.request.ChangePwRequestDto;
import project.SangHyun.member.controller.dto.response.MemberResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.member.service.dto.response.MemberDto;
import project.SangHyun.member.service.dto.request.MemberUpdateDto;
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
    AwsS3BucketHelper fileStoreHelper;
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
        MemberResponseDto ExpectResult = MemberFactory.makeInfoResponseDto(authMember);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(authMember));

        //when
        MemberDto ActualResult = memberService.getMemberInfo(memberDetails);

        //then
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("회원 프로필 정보를 로드한다.")
    public void loadProfile() throws Exception {
        //given
        MemberResponseDto ExpectResult = MemberFactory.makeProfileResponseDto(authMember);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(authMember));

        //when
        MemberDto ActualResult = memberService.getProfile(authMember.getId());

        //then
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("회원 프로필 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        MemberUpdateDto requestDto = MemberFactory.makeUpdateDto("테스터 변경", "테스터 변경 자기소개글입니다.");

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(authMember));
        given(fileStoreHelper.store(requestDto.getProfileImg())).willReturn("C:\\Users\\Family\\Pictures\\Screenshots\\1.png");

        //when
        MemberDto ActualResult = memberService.updateMember(authMember.getId(), requestDto);

        //then
        Assertions.assertEquals("테스터 변경", ActualResult.getNickname());
    }

    @Test
    @DisplayName("회원 프로필 정보를 삭제한다.")
    public void deleteMember() throws Exception {
        //given

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(authMember));
        willDoNothing().given(memberRepository).delete(authMember);

        //when, then
        Assertions.assertDoesNotThrow(() -> memberService.deleteMember(authMember.getId()));
    }

    @Test
    @DisplayName("비밀번호 변경을 진행한다.")
    public void changePW() throws Exception {
        //given
        ChangePwRequestDto requestDto = SignFactory.makeChangePwRequestDto(authMember.getEmail(), "change");

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(authMember));
        given(passwordEncoder.encode(any())).willReturn("encodedChangedPW");
        willDoNothing().given(redisHelper).delete(any());

        //when, then
        Assertions.assertDoesNotThrow(() -> memberService.changePassword(requestDto));
    }
}