package project.SangHyun.study.studyjoin.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.AlreadyJoinStudyMember;
import project.SangHyun.advice.exception.ExceedMaximumStudyMember;
import project.SangHyun.advice.exception.StudyJoinNotFoundException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyjoin.service.impl.StudyJoinServiceImpl;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyJoinServiceIntegrationTest {

    @Autowired
    StudyJoinServiceImpl studyJoinService;
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

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.applyJoin(study.getId(), member.getId());
        System.out.println("ActualResult = " + ActualResult.getStudyInfos());

        //then
        Assertions.assertEquals(StudyRole.APPLY, ActualResult.getStudyInfos().getStudyRole());
        Assertions.assertEquals(member.getId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("스터디에 이미 참가한 회원은 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail1() {
        //given
        Member member = testDB.findStudyGeneralMember();
        Study study = testDB.findBackEndStudy();

        //when, then
        Assertions.assertThrows(AlreadyJoinStudyMember.class, () -> studyJoinService.applyJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디의 정원이 꽉 찼다면 스터디 참가 신청에 실패한다.")
    public void applyJoin_fail2() {
        //given
        Member member = testDB.findGeneralMember();
        Study study = testDB.findZeroHeadCountStudy();

        //when, then
        Assertions.assertThrows(ExceedMaximumStudyMember.class, () -> studyJoinService.applyJoin(study.getId(), member.getId()));
    }

    @Test
    @DisplayName("스터디 참가 신청을 수락한다.")
    public void acceptJoin() throws Exception {
        //given
        Member member = testDB.findStudyApplyMember();
        Study study = testDB.findBackEndStudy();
        List<StudyFindMembersResponseDto> prevStudyMembers = studyJoinService.findStudyMembers(study.getId());

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.acceptJoin(study.getId(), member.getId());

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getMemberId());
        Assertions.assertEquals(StudyRole.MEMBER, ActualResult.getStudyInfos().getStudyRole());
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

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.rejectJoin(study.getId(), member.getId());

        //then
        Assertions.assertEquals(Optional.empty(), studyJoinRepository.findApplyStudy(study.getId(), member.getId()));
        Assertions.assertEquals(member.getId(), ActualResult.getMemberId());
        Assertions.assertEquals(4, studyJoinRepository.findStudyMembers(study.getId()).size());
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
        List<StudyFindMembersResponseDto> ActualResult = studyJoinService.findStudyMembers(study.getId());

        //then
        Assertions.assertEquals(5, ActualResult.size());
    }

}