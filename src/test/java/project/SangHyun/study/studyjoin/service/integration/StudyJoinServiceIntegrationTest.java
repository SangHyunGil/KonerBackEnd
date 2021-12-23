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
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyjoin.service.impl.StudyJoinServiceImpl;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.studyjoin.tools.StudyJoinFactory;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

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

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("스터디에 참여한다.")
    public void join() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        Study study = testDB.findBackEndStudy();
        StudyJoinRequestDto requestDto = StudyJoinFactory.makeCreateDto(study, member);

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.joinStudy(requestDto);

        //then
        Assertions.assertEquals(1, ActualResult.getStudyInfos().size());
        Assertions.assertEquals(member.getId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("스터디에 참여한 스터디원의 정보를 로드한다.")
    public void findStudyMembers() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();

        //when
        List<StudyFindMembersResponseDto> ActualResult = studyJoinService.findStudyMembers(study.getId());

        //then
        Assertions.assertEquals(3, ActualResult.size());
    }

}