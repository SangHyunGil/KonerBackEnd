package project.SangHyun.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import project.SangHyun.common.advice.exception.*;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinUpdateAuthorityDto;
import project.SangHyun.study.studyjoin.service.dto.response.FindJoinedStudyDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;
import project.SangHyun.factory.studyjoin.StudyJoinFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudyJoinServiceUnitTest {
    Member member;
    Study study;
    StudyJoin studyJoinCreator;
    StudyJoin studyJoinApply;

    @InjectMocks
    StudyJoinService studyJoinService;
    @Mock
    StudyJoinRepository studyJoinRepository;
    @Mock
    StudyRepository studyRepository;
    @Mock
    ApplicationEventPublisher publisher;


    @BeforeEach
    public void init() {
        member = StudyJoinFactory.makeTestAuthMember();
        study = StudyJoinFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyJoinCreator = StudyJoinFactory.makeTestStudyJoinCreator(member, study);
        studyJoinApply = StudyJoinFactory.makeTestStudyJoinApply(member, study);
    }

    @Test
    @DisplayName("스터디에 참가 신청한다.")
    public void applyJoin() {
        //given
        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");
        List<StudyJoin> admins = List.of(StudyJoinFactory.makeTestStudyJoinCreator(member, study));

        //mocking
        given(studyJoinRepository.isStudyMember(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(1L);
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.findAdminAndCreator(any())).willReturn(admins);
        given(studyJoinRepository.save(any())).willReturn(studyJoinCreator);

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.applyJoin(study.getId(), member.getId(), requestDto));
        verify(publisher).publishEvent(eventCaptor.capture());
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail1() {
        //given
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");

        //mocking
        given(studyJoinRepository.isStudyMember(any(), any())).willReturn(true);
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.applyJoin(study.getId(), member.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail2() {
        //given
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");

        //mocking
        given(studyJoinRepository.isStudyMember(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(2L);
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.applyJoin(study.getId(), member.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디 참가를 수락한다.")
    public void acceptJoin() {
        //given
        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);
        StudyJoin studyJoin = StudyJoinFactory.makeTestStudyJoinApply(member, study);

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.isStudyMember(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(1L);
        given(studyJoinRepository.findApplyStudy(any(), any())).willReturn(Optional.of(studyJoin));

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.acceptJoin(study.getId(), member.getId()));
        verify(publisher).publishEvent(eventCaptor.capture());
    }

    @Test
    @DisplayName("참여한 모든 스터디 조회한다.")
    public void findJoinedStudy() {
        //given

        //mocking
        given(studyJoinRepository.findStudiesByMemberId(any())).willReturn(new ArrayList<>(List.of(study)));

        //when
        List<FindJoinedStudyDto> ActualResult = studyJoinService.findJoinedStudies(member.getId());

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail1() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.isStudyMember(any(), any())).willReturn(true);

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail2() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.isStudyMember(any(), any())).willReturn(false);
        given(studyJoinRepository.findStudyJoinCount(any())).willReturn(100L);

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 참여하지 않았다면 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail3() {
        //given

        //mocking
        given(studyRepository.findById(study.getId())).willReturn(Optional.ofNullable(study));
        given(studyJoinRepository.isStudyMember(any(), any())).willReturn(false);

        //when, then
        Assertions.assertThrows(StudyJoinNotFoundException.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디 참가를 거절한다.")
    public void rejectJoin() {
        //given
        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);
        StudyJoin studyJoin = StudyJoinFactory.makeTestStudyJoinApply(member, study);

        //mocking
        given(studyJoinRepository.findApplyStudy(study.getId(), member.getId())).willReturn(Optional.of(studyJoin));
        willDoNothing().given(studyJoinRepository).delete(studyJoin);

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.rejectJoin(study.getId(), member.getId()));
        verify(publisher).publishEvent(eventCaptor.capture());
    }

    @Test
    @DisplayName("스터디에 참여한 스터디원의 정보를 얻어온다.")
    public void getStudyMembers() {
        //given
        Long studyId = 1L;

        StudyMembersInfoDto studyMember1 = new StudyMembersInfoDto(1L, "테스터1", "profileUrlImg", StudyRole.MEMBER, "빠르게 지원합니다.");
        StudyMembersInfoDto studyMember2 = new StudyMembersInfoDto(2L, "테스터2", "profileUrlImg", StudyRole.ADMIN, "빠르게 지원합니다.");
        StudyMembersInfoDto studyMember3 = new StudyMembersInfoDto(3L, "테스터3", "profileUrlImg", StudyRole.CREATOR, "빠르게 지원합니다.");
        ArrayList<StudyMembersInfoDto> studyMembersInfoDto = new ArrayList<>(Arrays.asList(studyMember1, studyMember2, studyMember3));

        //mocking
        given(studyJoinRepository.findStudyMembers(studyId)).willReturn(studyMembersInfoDto);

        //when
        List<StudyMembersDto> ActualResult = studyJoinService.findStudyMembers(studyId);

        //then
        Assertions.assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("스터디 멤버의 권한을 수정한다.")
    public void updateStudyRole() throws Exception {
        //given
        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.ADMIN);

        //mocking
        given(studyJoinRepository.findApplyStudy(any(), any())).willReturn(Optional.of(studyJoinApply));

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.updateAuthority(study.getId(), member.getId(), requestDto));
        verify(publisher).publishEvent(eventCaptor.capture());
    }

    @Test
    @DisplayName("잘못된 권한으로 요청한다면 스터디 멤버의 권한 수정이 실패한다.")
    public void updateStudyRole_fail() throws Exception {
        //given
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.CREATOR);

        //mocking

        //when, then
        Assertions.assertThrows(InvalidAuthorityException.class, () -> studyJoinService.updateAuthority(study.getId(), member.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디 생성자의 권한의 수정은 실패한다.")
    public void updateStudyRole_fail2() throws Exception {
        //given
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.ADMIN);

        //mocking
        given(studyJoinRepository.findApplyStudy(any(), any())).willReturn(Optional.of(studyJoinCreator));

        //when, then
        Assertions.assertThrows(StudyCreatorChangeAuthorityException.class, () -> studyJoinService.updateAuthority(study.getId(), member.getId(), requestDto));
    }
}