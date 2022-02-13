package project.SangHyun.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.common.helper.AwsS3BucketHelper;
import project.SangHyun.member.service.dto.request.MemberUpdateDto;
import project.SangHyun.member.tools.member.MemberFactory;

import java.io.InputStream;
import java.net.URL;

@SpringBootTest
public class MemberTest {
    @Autowired
    private AwsS3BucketHelper fileStoreHelper;

    @Test
    @DisplayName("회원의 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        Member member = MemberFactory.makeTestAuthMember();
        InputStream fileInputStream = new URL("https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg").openStream();
        MultipartFile multipartFile = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);

        MemberUpdateDto requestDto = new MemberUpdateDto("닉네임 수정", Department.CSE, multipartFile, "자기소개글도 수정했습니다.");

        //when
        Member updateMember = member.update(requestDto.toEntity(), fileStoreHelper.store(multipartFile));

        //then
        Assertions.assertEquals(requestDto.getNickname(), updateMember.getNickname());
        Assertions.assertEquals(requestDto.getIntroduction(), updateMember.getIntroduction());
    }

    @Test
    @DisplayName("회원 인증을 진행한다.")
    public void changeRole() throws Exception {
        //given
        Member member = MemberFactory.makeTestAuthMember();

        //when
        member.authenticate();

        //then
        Assertions.assertEquals(MemberRole.ROLE_MEMBER, member.getMemberRole());
    }

    @Test
    @DisplayName("회원의 비밀번호를 수정한다.")
    public void changePassword() throws Exception {
        //given
        Member member = MemberFactory.makeTestAuthMember();

        //when
        member.changePassword("changed");

        //then
        Assertions.assertEquals("changed", member.getPassword());
    }
}
