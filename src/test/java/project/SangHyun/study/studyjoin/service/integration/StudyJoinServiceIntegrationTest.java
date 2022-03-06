package project.SangHyun.study.studyjoin.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.common.advice.exception.AlreadyJoinStudyMember;
import project.SangHyun.common.advice.exception.ExceedMaximumStudyMember;
import project.SangHyun.common.advice.exception.InvalidAuthorityException;
import project.SangHyun.common.advice.exception.StudyJoinNotFoundException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinUpdateAuthorityDto;
import project.SangHyun.study.studyjoin.service.dto.response.FindJoinedStudyDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;
import project.SangHyun.study.studyjoin.tools.StudyJoinFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyJoinServiceIntegrationTest {

    @Autowired
    StudyJoinService studyJoinService;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    TestDB testDB;
    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("스터디 참가 신청을 진행한다.")
    public void applyJoin() throws Exception {
        //given
        Member member = testDB.findNotStudyMember();
        Study study = testDB.findBackEndStudy();
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.applyJoin(study.getId(), member.getId(), requestDto));
    }

    @Test
    @DisplayName("참가한 모든 스터디를 조회한다.")
    public void findJoinedStudy() throws Exception {
        //given
        Member member = testDB.findStudyMemberNotResourceOwner();

        //when
        List<FindJoinedStudyDto> ActualResult = studyJoinService.findJoinedStudies(member.getId());

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail1() {
        //given
        Member member = testDB.findStudyGeneralMember();
        Study study = testDB.findBackEndStudy();
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.applyJoin(study.getId(), member.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail2() {
        //given
        Member member = testDB.findGeneralMember();
        Study study = testDB.findZeroHeadCountStudy();
        StudyJoinCreateDto requestDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.applyJoin(study.getId(), member.getId(), requestDto));
    }

    @Test
    @DisplayName("스터디 참가 신청을 수락한다.")
    public void acceptJoin() throws Exception {
        //given
        Member member = testDB.findStudyApplyMember();
        Study study = testDB.findBackEndStudy();

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail1() {
        //given
        Member member = testDB.findStudyGeneralMember();
        Study study = testDB.findBackEndStudy();

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail2() {
        //given
        Member member = testDB.findStudyApplyMember();
        Study study = testDB.findZeroHeadCountStudy();

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 참여하지 않았다면 스터디 참가 수락에 실패한다.")
    public void acceptJoin_fail3() {
        //given
        Member member = testDB.findNotStudyMember();
        Study study = testDB.findBackEndStudy();

        //when, then
        Assertions.assertThrows(StudyJoinNotFoundException.class, () -> studyJoinService.acceptJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디 참가 신청을 거절한다.")
    public void rejectJoin() {
        //given
        Member member = testDB.findStudyApplyMember();
        Study study = testDB.findBackEndStudy();

        //when, then
        Assertions.assertDoesNotThrow(() -> studyJoinService.rejectJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 거절에 실패한다.")
    public void rejectJoin_fail1() {
        //given
        Member member = testDB.findStudyGeneralMember();
        Study study = testDB.findBackEndStudy();

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.rejectJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 거절에 실패한다.")
    public void rejectJoin_fail2() {
        //given
        Member member = testDB.findStudyApplyMember();
        Study study = testDB.findZeroHeadCountStudy();

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.rejectJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 참여하지 않았다면 스터디 참가 거절에 실패한다.")
    public void rejectJoin_fail3() {
        //given
        Member member = testDB.findNotStudyMember();
        Study study = testDB.findBackEndStudy();

        //when, then
        Assertions.assertThrows(StudyJoinNotFoundException.class, () -> studyJoinService.rejectJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디에 참여 및 지원한 스터디원의 정보를 로드한다.")
    public void findStudyMembers() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();

        //when
        List<StudyMembersDto> ActualResult = studyJoinService.findStudyMembers(study.getId());

        //then
        Assertions.assertEquals(5, ActualResult.size());
    }

    @Test
    @DisplayName("스터디 멤버의 권한을 수정한다.")
    public void updateStudyRole() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        Study study = testDB.findBackEndStudy();
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.ADMIN);

        //when
        studyJoinService.updateAuthority(study.getId(), member.getId(), requestDto);
        StudyJoin studyJoin = studyJoinRepository.findApplyStudy(study.getId(), member.getId()).get();

        //then
        Assertions.assertEquals(StudyRole.ADMIN, studyJoin.getStudyRole());
    }

    @Test
    @DisplayName("잘못된 권한으로 요청한다면 스터디 멤버의 권한 수정이 실패한다.")
    public void updateStudyRole_fail() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        Study study = testDB.findBackEndStudy();
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.CREATOR);

        //when, then
        Assertions.assertThrows(InvalidAuthorityException.class, () -> studyJoinService.updateAuthority(study.getId(), member.getId(), requestDto));
    }
}