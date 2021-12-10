package project.SangHyun.domain.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.service.Impl.MemberServiceImpl;
import project.SangHyun.dto.request.member.MemberUpdateInfoRequestDto;
import project.SangHyun.dto.response.member.MemberDeleteResponseDto;
import project.SangHyun.dto.response.member.MemberInfoResponseDto;
import project.SangHyun.dto.response.member.MemberProfileResponseDto;
import project.SangHyun.dto.response.member.MemberUpdateInfoResponseDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class MemberServiceUnitImplTest {

    @InjectMocks
    MemberServiceImpl memberService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    StudyJoinRepository studyJoinRepository;

    @Test
    public void 회원정보로드() throws Exception {
        //given
        MemberDetails memberDetails = new MemberDetails(1L, "test", "encodedPW",
                Collections.singletonList(new SimpleGrantedAuthority(MemberRole.ROLE_MEMBER.toString())));

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberInfoResponseDto ExpectResult = MemberInfoResponseDto.createDto(member, List.of());

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
    public void 프로필로드() throws Exception {
        //given
        Long memberId = 1L;

        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberProfileResponseDto ExpectResult = MemberProfileResponseDto.createDto(member);

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
    public void 회원정보수정() throws Exception {
        //given
        Long memberId = 1L;
        MemberUpdateInfoRequestDto requestDto = new MemberUpdateInfoRequestDto("test", "테스터 변경", "기계공학부");

        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        //when
        MemberUpdateInfoResponseDto ActualResult = memberService.updateMemberInfo(memberId, requestDto);

        //then
        Assertions.assertEquals(1L, ActualResult.getMemberId());
        Assertions.assertEquals("test", ActualResult.getEmail());
        Assertions.assertEquals("테스터 변경", ActualResult.getNickname());
        Assertions.assertEquals("기계공학부", ActualResult.getDepartment());
    }

    @Test
    public void 회원삭제() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberDeleteResponseDto ExpectResult = MemberDeleteResponseDto.createDto(member);
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