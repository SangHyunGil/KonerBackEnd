package project.SangHyun.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.tools.member.MemberFactory;
import project.SangHyun.common.helper.FileStoreHelper;

import java.io.File;
import java.io.FileInputStream;

public class MemberTest {
    String filePathDir = new File("C:/Users/Family/Desktop/SH/spring/Study").getAbsolutePath() + "/";
    FileStoreHelper fileStoreHelper = new FileStoreHelper(filePathDir);

    @Test
    @DisplayName("회원의 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        Member member = MemberFactory.makeTestAuthMember();
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Family\\Pictures\\Screenshots\\git.png");
        MultipartFile multipartFile = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);

        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto("test", "닉네임 수정", "컴퓨터공학부", multipartFile);

        //when
        Member updateMember = member.updateMemberInfo(requestDto, fileStoreHelper.storeFile(multipartFile));

        //then
        Assertions.assertEquals(requestDto.getNickname(), updateMember.getNickname());
    }

    @Test
    @DisplayName("회원의 권한을 수정한다.")
    public void changeRole() throws Exception {
        //given
        Member member = MemberFactory.makeTestAuthMember();

        //when
        member.changeRole(MemberRole.ROLE_MEMBER);

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
