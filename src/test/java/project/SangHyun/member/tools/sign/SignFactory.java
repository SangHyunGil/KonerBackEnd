package project.SangHyun.member.tools.sign;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.BasicFactory;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.dto.request.*;
import project.SangHyun.member.dto.response.MemberChangePwResponseDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.service.impl.dto.JwtTokens;

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

    public static MemberRegisterRequestDto makeNotValidRequestDto() {
        return new MemberRegisterRequestDto("xptmxm6!", "xptmx", "테스터6", Department.CSE, multipartFile);
    }

    public static MemberRegisterRequestDto makeDuplicateEmailRequestDto() {
        return new MemberRegisterRequestDto("xptmxm1!", "xptmxm6!", "테스터6", Department.CSE, multipartFile);
    }

    public static MemberRegisterRequestDto makeDuplicateNicknameRequestDto() {
        return new MemberRegisterRequestDto("xptmxm6!", "xptmxm6!", "승범", Department.CSE, multipartFile);
    }

    public static MemberLoginRequestDto makeAuthMemberLoginRequestDto() {
        return new MemberLoginRequestDto("xptmxm1!", "xptmxm1!");
    }

    public static MemberLoginRequestDto makeNotAuthMemberLoginRequestDto() {
        return new MemberLoginRequestDto("xptmxm2!", "xptmxm2!");
    }

    public static MemberLoginRequestDto makeWrongPwMemberLoginRequestDto() {
        return new MemberLoginRequestDto("xptmxm1!", "wrong");
    }

    public static MemberEmailAuthRequestDto makeEmailAuthRequestDto(RedisKey redisKey) {
        return new MemberEmailAuthRequestDto("xptmxm1!", redisKey);
    }

    public static VerifyEmailRequestDto makeVerifyEmailRequestDto(String email, String authCode, RedisKey redisKey) {
        return new VerifyEmailRequestDto(email, authCode, redisKey);
    }

    public static MemberChangePwRequestDto makeChangePwRequestDto(String email, String password) {
        return new MemberChangePwRequestDto(email, password);
    }

    public static TokenRequestDto makeTokenRequestDto(String refreshToken) {
        return new TokenRequestDto(refreshToken);
    }

    public static Member makeAuthTestMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "승범", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    // Response
    public static MemberRegisterResponseDto makeRegisterResponseDto(Member member) {
        return MemberRegisterResponseDto.create(member);
    }

    public static MemberLoginResponseDto makeLoginResponseDto(Member member) {
        return MemberLoginResponseDto.create(member, new JwtTokens("accessToken", "refreshToken"));
    }

    public static TokenResponseDto makeTokenResponseDto(Member member) {
        return TokenResponseDto.create(member, new JwtTokens("newAccessToken", "newRefreshToken"));
    }

    public static MemberChangePwResponseDto makeChangePwResponseDto(Member member) {
        return MemberChangePwResponseDto.create(member);
    }
}
