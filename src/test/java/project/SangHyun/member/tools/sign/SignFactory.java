package project.SangHyun.member.tools.sign;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.BasicFactory;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.controller.dto.request.*;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.controller.dto.response.LoginResponseDto;
import project.SangHyun.member.controller.dto.response.MemberResponseDto;
import project.SangHyun.member.controller.dto.response.TokenResponseDto;
import project.SangHyun.member.service.dto.JwtTokens;
import project.SangHyun.member.service.dto.response.MemberDto;
import project.SangHyun.member.service.dto.request.MemberRegisterDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SignFactory extends BasicFactory {
    private static InputStream fileInputStream;
    private static MultipartFile multipartFile;

    static {
        try {
            fileInputStream = new URL("https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg").openStream();
            multipartFile = new MockMultipartFile("git", "git.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Request
    public static MemberRegisterRequestDto makeRegisterRequestDto() {
        return new MemberRegisterRequestDto("xptmxm6!", "xptmxm6!", "테스터6", Department.CSE, multipartFile);
    }
    public static MemberRegisterDto makeRegisterDto() {
        return new MemberRegisterDto("xptmxm6!", "xptmxm6!", "테스터6", Department.CSE, multipartFile);
    }

    public static MemberRegisterRequestDto makeNotValidRequestDto() {
        return new MemberRegisterRequestDto("xptmxm6!", "xptmx", "테스터6", Department.CSE, multipartFile);
    }

    public static MemberRegisterDto makeNotValidDto() {
        return new MemberRegisterDto("xptmxm6!", "xptmx", "테스터6", Department.CSE, multipartFile);
    }


    public static MemberRegisterRequestDto makeDuplicateEmailRequestDto() {
        return new MemberRegisterRequestDto("xptmxm1!", "xptmxm6!", "테스터6", Department.CSE, multipartFile);
    }

    public static MemberRegisterDto makeDuplicateEmailDto() {
        return new MemberRegisterDto("xptmxm1!", "xptmxm6!", "테스터6", Department.CSE, multipartFile);
    }

    public static MemberRegisterRequestDto makeDuplicateNicknameRequestDto() {
        return new MemberRegisterRequestDto("xptmxm6!", "xptmxm6!", "승범", Department.CSE, multipartFile);
    }

    public static MemberRegisterDto makeDuplicateNicknameDto() {
        return new MemberRegisterDto("xptmxm6!", "xptmxm6!", "승범", Department.CSE, multipartFile);
    }

    public static LoginRequestDto makeAuthMemberLoginRequestDto() {
        return new LoginRequestDto("xptmxm1!", "xptmxm1!");
    }

    public static LoginRequestDto makeNotAuthMemberLoginRequestDto() {
        return new LoginRequestDto("xptmxm2!", "xptmxm2!");
    }

    public static LoginRequestDto makeWrongPwMemberLoginRequestDto() {
        return new LoginRequestDto("xptmxm1!", "wrong");
    }

    public static EmailAuthRequestDto makeEmailAuthRequestDto(RedisKey redisKey) {
        return new EmailAuthRequestDto("xptmxm1!", redisKey);
    }

    public static VerifyRequestDto makeVerifyEmailRequestDto(String email, String authCode, RedisKey redisKey) {
        return new VerifyRequestDto(email, authCode, redisKey);
    }

    public static ChangePwRequestDto makeChangePwRequestDto(String email, String password) {
        return new ChangePwRequestDto(email, password);
    }

    public static TokenRequestDto makeTokenRequestDto(String refreshToken) {
        return new TokenRequestDto(refreshToken);
    }

    public static Member makeAuthTestMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "승범", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "승범입니다.");
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    // Response
    public static MemberDto makeMemberDto(Member member) {
        return MemberDto.create(member);
    }

    public static MemberResponseDto makeRegisterResponseDto(MemberDto memberDto) {
        return MemberResponseDto.create(memberDto);
    }

    public static LoginResponseDto makeLoginResponseDto(Member member) {
        return LoginResponseDto.create(member, new JwtTokens("accessToken", "refreshToken"));
    }

    public static TokenResponseDto makeTokenResponseDto(Member member) {
        return TokenResponseDto.create(member, new JwtTokens("newAccessToken", "newRefreshToken"));
    }
}
