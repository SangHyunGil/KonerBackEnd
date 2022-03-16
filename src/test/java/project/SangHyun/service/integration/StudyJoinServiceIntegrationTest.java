package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.*;
import project.SangHyun.factory.studyjoin.StudyJoinFactory;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinUpdateAuthorityDto;
import project.SangHyun.study.studyjoin.service.dto.response.FindJoinedStudyDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;

import java.util.List;

class StudyJoinServiceIntegrationTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("스터디 참가 신청을 진행한다.")
    public void applyJoin() throws Exception {
        //given
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.applyJoin(backendStudy.getId(), notStudyMember.getId(), requestDto));
    }

    @Test
    @DisplayName("참가한 모든 스터디를 조회한다.")
    public void findJoinedStudy() throws Exception {
        //given

        //when
        List<FindJoinedStudyDto> ActualResult = studyJoinService.findJoinedStudies(hasNoResourceMember.getId());

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail1() {
        //given
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.applyJoin(backendStudy.getId(), studyMember.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail2() {
        //given
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.applyJoin(zeroHeadCountStudy.getId(), studyMember.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디 참가 신청을 수락한다.")
    public void acceptJoin() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.acceptJoin(backendStudy.getId(), studyApplyMember.getId()));
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail1() {
        //given

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.acceptJoin(backendStudy.getId(), studyMember.getId()));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail2() {
        //given

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.acceptJoin(zeroHeadCountStudy.getId(), studyApplyMember.getId()));
    }

    @Test
    @DisplayName("스터디에 참여하지 않았다면 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail3() {
        //given

        //when, then
        Assertions.assertThrows(StudyJoinNotFoundException.class, () -> studyJoinService.acceptJoin(backendStudy.getId(), notStudyMember.getId()));
    }

    @Test
    @DisplayName("스터디 참가 신청을 거절한다.")
    public void rejectJoin() {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.rejectJoin(backendStudy.getId(), studyApplyMember.getId()));
    }

    @Test
    @DisplayName("스터디에 참여하지 않았다면 스터디 참가 거절에 실패한다.")
    public void rejectJoin_fail3() {
        //given

        //when, then
        Assertions.assertThrows(StudyJoinNotFoundException.class, () -> studyJoinService.rejectJoin(backendStudy.getId(), notStudyMember.getId()));
    }

    @Test
    @DisplayName("스터디에 참여 및 지원한 스터디원의 정보를 로드한다.")
    public void findStudyMembers() throws Exception {
        //given

        //when
        List<StudyMembersDto> ActualResult = studyJoinService.findStudyMembers(backendStudy.getId());

        //then
        Assertions.assertEquals(5, ActualResult.size());
    }

    @Test
    @DisplayName("스터디 멤버의 권한을 수정한다.")
    public void updateStudyRole() throws Exception {
        //given
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.ADMIN);

        //when
        studyJoinService.updateAuthority(backendStudy.getId(), studyMember.getId(), requestDto);
        StudyJoin studyJoin = studyJoinRepository.findApplyStudy(backendStudy.getId(), studyMember.getId()).get();

        //then
        Assertions.assertEquals(StudyRole.ADMIN, studyJoin.getStudyRole());
    }

    @Test
    @DisplayName("잘못된 권한으로 요청한다면 스터디 멤버의 권한 수정이 실패한다.")
    public void updateStudyRole_fail() throws Exception {
        //given
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.CREATOR);

        //when, then
        Assertions.assertThrows(InvalidAuthorityException.class, () -> studyJoinService.updateAuthority(backendStudy.getId(), studyMember.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디 생성자의 권한의 수정은 실패한다.")
    public void updateStudyRole_fail2() throws Exception {
        //given
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.ADMIN);

        //when, then
        Assertions.assertThrows(StudyCreatorChangeAuthorityException.class, () -> studyJoinService.updateAuthority(backendStudy.getId(), studyCreator.getId(), requestDto));
    }
}