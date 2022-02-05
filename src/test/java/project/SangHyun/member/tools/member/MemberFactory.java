package project.SangHyun.member.tools.member;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.BasicFactory;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

public class MemberFactory extends BasicFactory {
    private static InputStream fileInputStream;
    private static MultipartFile multipartFile;

    static {
        try {
            InputStream fileInputStream = new URL("https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg").openStream();
            multipartFile = new MockMultipartFile("git", "git.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MemberDetails makeMemberDetails() {
        return new MemberDetails(1L,"xptmxm1!", "encodedPW",
                Collections.singletonList(new SimpleGrantedAuthority(MemberRole.ROLE_MEMBER.toString())));
    }

    public static MemberUpdateRequestDto makeUpdateRequestDto(String nickname) {
        return new MemberUpdateRequestDto("xptmxm1!", nickname, Department.CSE, multipartFile);
    }

    public static MemberInfoResponseDto makeInfoResponseDto(Member member) {
        return MemberInfoResponseDto.create(member);
    }

    public static MemberProfileResponseDto makeProfileResponseDto(Member member) {
        return MemberProfileResponseDto.create(member);
    }

    public static MemberUpdateResponseDto makeUpdateResponseDto(Member member) {
        return MemberUpdateResponseDto.create(member);
    }

    public static MemberDeleteResponseDto makeDeleteResponseDto(Member member) {
        return MemberDeleteResponseDto.create(member);
    }
}
