package project.SangHyun.study.studyarticle.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.TestDB;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudyArticleRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", "컴공", MemberRole.ROLE_MEMBER);
        memberRepository.save(memberA);

        Study study = new Study("백엔드 모집", "백엔드", "백엔드 모집합니다.", StudyState.STUDYING, RecruitState.PROCEED, 3L, memberA, new ArrayList<>(), new ArrayList<>());

        StudyJoin studyJoin = new StudyJoin(memberA, study, StudyRole.CREATOR);
        study.join(studyJoin);

        StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
        study.addBoard(studyBoard1);

        studyRepository.save(study);

        StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", 0L, memberA, studyBoard1);
        StudyArticle studyArticle2 = new StudyArticle("자유게시판 테스트 글", "자유게시판 테스트 글입니다.", 0L, memberA, studyBoard1);
        StudyArticle studyArticle3 = new StudyArticle("알고리즘 테스트 글", "알고리즘 테스트 글입니다.", 0L, memberA, studyBoard1);

        studyArticleRepository.save(studyArticle1);
        studyArticleRepository.save(studyArticle2);
        studyArticleRepository.save(studyArticle3);
    }

    @Test
    @DisplayName("모든 게시글을 조회한다.")
    public void findAllArticles() throws Exception {
        //given
        List<StudyArticle> allArticles = studyArticleRepository.findAllArticles(1L);

        //when
        int size = allArticles.size();

        //then
        Assertions.assertEquals(3, size);
    }
}