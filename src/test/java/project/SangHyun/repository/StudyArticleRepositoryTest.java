package project.SangHyun.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.repository.StudyBoardRepository;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class StudyArticleRepositoryTest {
    StudyBoard studyBoard1;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyBoardRepository studyBoardRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", Department.CSE, "profileImgUrl", MemberRole.ROLE_MEMBER, "상현입니다.");
        Member storedMemberA = memberRepository.save(memberA);

        Study study = new Study("백엔드 모집", List.of("백엔드"), "백엔드 모집합니다.", "C:\\Users\\Family\\Pictures\\Screenshots\\2.png",
                2L, "2021-10-01", "2021-12-25", StudyCategory.CSE, StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, memberA, new ArrayList<>(), new ArrayList<>());

        StudyJoin studyJoin = new StudyJoin(storedMemberA, null, study, StudyRole.CREATOR);
        study.join(studyJoin);

        studyBoard1 = new StudyBoard("공지사항", study);
        study.addBoard(studyBoard1);

        studyRepository.save(study);

        StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", 0L, storedMemberA, studyBoard1);
        StudyArticle studyArticle2 = new StudyArticle("자유게시판 테스트 글", "자유게시판 테스트 글입니다.", 0L, storedMemberA, studyBoard1);
        StudyArticle studyArticle3 = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", 0L, storedMemberA, studyBoard1);

        studyArticleRepository.save(studyArticle1);
        studyArticleRepository.save(studyArticle2);
        studyArticleRepository.save(studyArticle3);

        for (int i = 0; i < 10; i++) {
            StudyArticle studyArticleTest = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", 0L, storedMemberA, studyBoard1);
            studyArticleRepository.save(studyArticleTest);
        }
    }

    @Test
    @DisplayName("모든 게시글을 조회한다.")
    public void findAllArticles() throws Exception {
        //given

        //when
        List<StudyArticle> allArticles = studyArticleRepository.findAll();

        //then
        Assertions.assertEquals(13, allArticles.size());
    }

    @Test
    @DisplayName("게시글을 제목으로 조회한다.")
    public void findArticleByTitle() throws Exception {
        //given

        //when
        List<StudyArticle> studyArticle = studyArticleRepository.findArticleByTitle("공지사항");

        //then
        Assertions.assertEquals("공지사항 테스트 글", studyArticle.get(0).getTitle());
    }

    @Test
    @DisplayName("13개의 게시글이 존재하므로 첫 번째 페이지를 로드하면 게시글을 10개 불러온다.")
    public void findAllOrderByStudyArticleIdDesc() throws Exception {
        //given

        //when
        Page<StudyArticle> studyArticles = studyArticleRepository.findAllOrderByStudyArticleIdDesc(studyBoard1.getId(), PageRequest.of(0, 10));

        //then
        Assertions.assertEquals(2, studyArticles.getTotalPages());
        Assertions.assertEquals(10, studyArticles.getNumberOfElements());
        Assertions.assertEquals(true,studyArticles.hasNext());
    }

    @Test
    @DisplayName("13개의 게시글이 존재하므로 두 번째 페이지를 로드하면 게시글을 2개 불러온다.")
    public void findAllOrderByStudyArticleIdDesc2() throws Exception {
        //given

        //when
        Page<StudyArticle> studyArticles = studyArticleRepository.findAllOrderByStudyArticleIdDesc(studyBoard1.getId(), PageRequest.of(1, 10));

        //then
        Assertions.assertEquals(2, studyArticles.getTotalPages());
        Assertions.assertEquals(3, studyArticles.getNumberOfElements());
        Assertions.assertEquals(false, studyArticles.hasNext());
    }
}