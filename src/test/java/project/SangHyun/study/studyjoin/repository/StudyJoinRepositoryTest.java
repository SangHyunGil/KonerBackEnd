package project.SangHyun.study.studyjoin.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Schedule;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyOptions;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.study.domain.Tag.Tag;
import project.SangHyun.study.study.domain.Tag.Tags;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class StudyJoinRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    StudyJoinRepository studyJoinRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        Member storeMemberA = memberRepository.save(memberA);

        Member memberB = new Member("xptmxm5!", passwordEncoder.encode("xptmxm5!"), "진영", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER);
        Member storeMemberB = memberRepository.save(memberB);

        Study study = new Study("백엔드 모집", new Tags(List.of(new Tag("백엔드"))), "백엔드 모집합니다.",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", StudyCategory.CSE,
                new StudyOptions(StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE), 2L, new Schedule("2021-10-01", "2021-12-25"), memberA, new ArrayList<>(), new ArrayList<>());
        studyRepository.save(study);

        StudyJoin studyJoin = new StudyJoin(storeMemberA, null, study, StudyRole.CREATOR);
        studyJoinRepository.save(studyJoin);

        StudyJoin studyJoin2 = new StudyJoin(storeMemberB, "빠르게 진행합니다.", study, StudyRole.ADMIN);
        studyJoinRepository.save(studyJoin2);

        StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
        StudyBoard studyBoard2 = new StudyBoard("자유게시판", study);
        StudyBoard studyBoard3 = new StudyBoard("알고리즘", study);
        study.addBoard(studyBoard1);
        study.addBoard(studyBoard2);
        study.addBoard(studyBoard3);

        StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", 0L, storeMemberA, studyBoard1);
        StudyArticle studyArticle2 = new StudyArticle("자유게시판 테스트 글", "자유게시판 테스트 글입니다.", 0L, storeMemberA, studyBoard1);
        StudyArticle studyArticle3 = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", 0L, storeMemberA, studyBoard1);

        studyArticleRepository.save(studyArticle1);
        studyArticleRepository.save(studyArticle2);
        studyArticleRepository.save(studyArticle3);
    }

    @Test
    @DisplayName("모든 스터디에 참여한 사람의 수를 조회한다.")
    public void findStudyJoinCount() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);

        //when
        Long studyJoinCount = studyJoinRepository.findStudyJoinCount(study.getId());

        //then
        Assertions.assertEquals(2, studyJoinCount);
    }

    @Test
    @DisplayName("참여한 모든 스터디를 조회한다.")
    public void findJoinedStudies() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").get();

        //when
        List<Study> studies = studyJoinRepository.findStudiesByMemberId(member.getId());

        //then
        Assertions.assertEquals(1, studies.size());
    }

    @Test
    @DisplayName("해당 회원이 스터디에 참여중인지 조회한다.")
    public void exist() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        Member member = memberRepository.findByEmail("xptmxm3!").get();

        //when
        Boolean exist = studyJoinRepository.exist(study.getId(), member.getId());

        //then
        Assertions.assertEquals(true, exist);
    }

    @Test
    @DisplayName("해당 스터디의 권한을 조회한다.")
    public void findStudyRole() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        Member member = memberRepository.findByEmail("xptmxm3!").get();

        //when
        StudyJoin studyJoin = studyJoinRepository.findStudyRole(member.getId(), study.getId()).get();

        //then
        Assertions.assertEquals(StudyRole.CREATOR, studyJoin.getStudyRole());
    }
}