package project.SangHyun.domain.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.Impl.StudyJoinServiceImpl;
import project.SangHyun.dto.request.study.StudyJoinRequestDto;
import project.SangHyun.dto.response.study.StudyJoinResponseDto;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyJoinServiceIntegrationImplTest {

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
    public void 스터디_참여() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyJoinRequestDto requestDto = new StudyJoinRequestDto(study.getId(), member.getId());

        //when
        StudyJoinResponseDto ActualResult = studyJoinService.join(requestDto);
        //then
        Assertions.assertEquals(1, ActualResult.getStudyInfos().size());
        Assertions.assertEquals(member.getId(), ActualResult.getMemberId());
    }

}