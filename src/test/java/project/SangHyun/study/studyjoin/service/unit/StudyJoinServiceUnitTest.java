package project.SangHyun.study.studyjoin.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.SangHyun.advice.exception.AlreadyJoinStudyMember;
import project.SangHyun.advice.exception.ExceedMaximumStudyMember;
import project.SangHyun.advice.exception.StudyJoinNotFoundException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.impl.StudyJoinServiceImpl;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.studyjoin.tools.StudyJoinFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudyJoinServiceUnitTest {
    Member member;
    Study study;
    StudyJoin studyJoin;

    @InjectMocks
    StudyJoinServiceImpl studyJoinService;
    @Mock
    StudyJoinRepository studyJoinRepository;
    @Mock
    StudyRepository studyRepository;

    @BeforeEach
    public void init() {
        member = StudyJoinFactory.makeTestAuthMember();
        study = StudyJoinFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyJoin = StudyJoinFactory.makeTestStudyJoinCreator(member, study);
    }

    @Test
    @DisplayName("스터디에 참가 신청한다.")
    public void applyJoin() {
        //given
        StudyJoinRequestDto requestDto = StudyJoinFactory.makeRequestDto("빠르게 지원합니다.");
        StudyJoinResponseDto ExpectResult = StudyJoinFactory.makeResponseDto(studyJoin);

        //mocking
        given(studyJoinRepository.exist(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(1L);
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.save(any())).willReturn(studyJoin);

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.applyJoin(study.getId(), member.getId(), requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getStudyInfos(), ActualResult.getStudyInfos());
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail1() {
        //given
        StudyJoinRequestDto requestDto = StudyJoinFactory.makeRequestDto("빠르게 지원합니다.");

        //mocking
        given(studyJoinRepository.exist(any(), any())).willReturn(true);
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.applyJoin(study.getId(), member.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail2() {
        //given
        StudyJoinRequestDto requestDto = StudyJoinFactory.makeRequestDto("빠르게 지원합니다.");

        //mocking
        given(studyJoinRepository.exist(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(2L);
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.applyJoin(study.getId(), member.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디 참가를 수락한다.")
    public void acceptJoin() {
        //given
        StudyJoin studyJoin = StudyJoinFactory.makeTestStudyJoinApply(member, study);
        StudyJoinResponseDto ExpectResult = StudyJoinFactory.makeResponseDto(studyJoin);

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.exist(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(1L);
        given(studyJoinRepository.findApplyStudy(any(), any())).willReturn(Optional.of(studyJoin));

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.acceptJoin(study.getId(), member.getId());

        //then
        Assertions.assertEquals(ExpectResult.getStudyJoinId(), ActualResult.getStudyJoinId());
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail1() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.exist(any(), any())).willReturn(true);

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail2() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.exist(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(2L);

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 참여하지 않았다면 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail3() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.exist(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(1L);
        given(studyJoinRepository.findApplyStudy(any(), any())).willReturn(Optional.empty());

        //when, then
        Assertions.assertThrows(StudyJoinNotFoundException.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디 참가를 거절한다.")
    public void rejectJoin() {
        //given
        StudyJoin studyJoin = StudyJoinFactory.makeTestStudyJoinApply(member, study);
        StudyJoinResponseDto ExpectResult = StudyJoinFactory.makeResponseDto(studyJoin);

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.exist(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(1L);
        given(studyJoinRepository.findApplyStudy(any(), any())).willReturn(Optional.of(studyJoin));

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.rejectJoin(study.getId(), member.getId());

        //then
        Assertions.assertEquals(ExpectResult.getStudyJoinId(), ActualResult.getStudyJoinId());
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 거절에 실패한다.")
    public void rejectJoin_fail1() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.exist(any(), any())).willReturn(true);

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.rejectJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 수락에 실패한다.")
    public void rejectJoin_fail2() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.exist(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(2L);

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.rejectJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 참여하지 않았다면 스터디 참가 수락에 실패한다.")
    public void rejectJoin_fail3() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.exist(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(1L);
        given(studyJoinRepository.findApplyStudy(any(), any())).willReturn(Optional.empty());

        //when, then
        Assertions.assertThrows(StudyJoinNotFoundException.class, () -> studyJoinService.rejectJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 참여한 스터디원의 정보를 얻어온다.")
    public void getStudyMembers() {
        //given
        Long memberId1 = 1L;
        Long memberId2 = 2L;
        Long memberId3 = 3L;
        Long studyId = 1L;

        StudyMembersInfoDto studyMember1 = new StudyMembersInfoDto(memberId1, "테스터1", StudyRole.MEMBER, "빠르게 지원합니다.");
        StudyMembersInfoDto studyMember2 = new StudyMembersInfoDto(memberId2, "테스터2", StudyRole.ADMIN, "빠르게 지원합니다.");
        StudyMembersInfoDto studyMember3 = new StudyMembersInfoDto(memberId3, "테스터3", StudyRole.CREATOR, "빠르게 지원합니다.");
        ArrayList<StudyMembersInfoDto> studyMembersInfoDtos = new ArrayList<>(Arrays.asList(studyMember1, studyMember2, studyMember3));

        //mocking
        given(studyJoinRepository.findStudyMembers(studyId)).willReturn(studyMembersInfoDtos);

        //when
        List<StudyFindMembersResponseDto> ActualResult = studyJoinService.findStudyMembers(studyId);

        //then
        Assertions.assertEquals(3, ActualResult.size());
    }

}