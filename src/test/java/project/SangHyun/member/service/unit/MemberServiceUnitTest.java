package project.SangHyun.member.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.member.service.impl.MemberServiceImpl;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;

import java.util.Collections;
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
        MemberDetails memberDetails = new MemberDetails(1L, "test", "encodedPW",
                Collections.singletonList(new SimpleGrantedAuthority(MemberRole.ROLE_MEMBER.toString())));

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberInfoResponseDto ExpectResult = MemberInfoResponseDto.create(member, List.of());

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(studyJoinRepository.findStudyInfoByMemberId(member.getId())).willReturn(List.of());

        //when
        MemberInfoResponseDto ActualResult = memberService.getMemberInfo(memberDetails);

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
        Assertions.assertEquals(ExpectResult.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(ExpectResult.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(ExpectResult.getDepartment(), ActualResult.getDepartment());
        Assertions.assertEquals(ExpectResult.getStudyInfos(), ActualResult.getStudyInfos());
    }

    @Test
    @DisplayName("회원 프로필 정보를 로드한다.")
    public void loadProfile() throws Exception {
        //given
        Long memberId = 1L;

        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberProfileResponseDto ExpectResult = MemberProfileResponseDto.create(member);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        //when
        MemberProfileResponseDto ActualResult = memberService.getProfile(memberId);

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
        Assertions.assertEquals(ExpectResult.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(ExpectResult.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(ExpectResult.getDepartment(), ActualResult.getDepartment());
    }

    @Test
    @DisplayName("회원 프로필 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        Long memberId = 1L;
        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto("test", "테스터 변경", "기계공학부");

        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        //when
        MemberUpdateResponseDto ActualResult = memberService.updateMember(memberId, requestDto);

        //then
        Assertions.assertEquals(1L, ActualResult.getMemberId());
        Assertions.assertEquals("test", ActualResult.getEmail());
        Assertions.assertEquals("테스터 변경", ActualResult.getNickname());
        Assertions.assertEquals("기계공학부", ActualResult.getDepartment());
    }

    @Test
    @DisplayName("회원 프로필 정보를 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberDeleteResponseDto ExpectResult = MemberDeleteResponseDto.create(member);
        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));
        willDoNothing().given(memberRepository).delete(member);

        //when
        MemberDeleteResponseDto ActualResult = memberService.deleteMember(memberId);

        //then
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
        Assertions.assertEquals(ExpectResult.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(ExpectResult.getNickname(), ActualResult.getNickname());
    }
}