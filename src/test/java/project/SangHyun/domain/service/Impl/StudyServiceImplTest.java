package project.SangHyun.domain.service.Impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class StudyServiceImplTest {

    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;
    @Test
    public void fetchJoin() throws Exception {
        //given
        Member member = Member.builder()
                .email("email")
                .nickname("nick")
                .build();
        Member saveMember = memberRepository.save(member);

        for (int i = 0; i < 10; i++) {
            Study study = Study.builder()
                    .title("Test")
                    .member(new Member(saveMember.getId()))
                    .build();
            studyRepository.save(study);
        }

        em.flush();
        em.clear();

        //when
        List<Study> studies = studyRepository.findAllByFetchJoin();

        //then
        for (Study study : studies) {
            System.out.println("board = " + study.getMember().getEmail());
        }
    }

}