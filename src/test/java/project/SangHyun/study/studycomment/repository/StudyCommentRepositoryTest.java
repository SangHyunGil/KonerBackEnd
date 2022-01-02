package project.SangHyun.study.studycomment.repository;

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
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.*;
import project.SangHyun.study.study.domain.enums.RecruitState;
import project.SangHyun.study.study.domain.enums.StudyMethod;
import project.SangHyun.study.study.domain.enums.StudyRole;
import project.SangHyun.study.study.domain.enums.StudyState;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.repository.StudyBoardRepository;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudyCommentRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyBoardRepository studyBoardRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    StudyCommentRepository studyCommentRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        Member memberA = new Member("xptmxm3!", passwordEncoder.encode("xptmxm3!"), "상현", "컴공", null, MemberRole.ROLE_MEMBER);
        memberRepository.save(memberA);

        Study study = new Study("백엔드 모집", new Tags(List.of(new Tag("백엔드"))), "백엔드 모집합니다.",  "C:\\Users\\Family\\Pictures\\Screenshots\\2.png", "컴퓨터공학과",
                new StudyOptions(StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE), 2L, new Schedule("2021-10-01", "2021-12-25"), memberA, new ArrayList<>(), new ArrayList<>());

        StudyJoin studyJoin = new StudyJoin(memberA, null, study, StudyRole.CREATOR);
        study.join(studyJoin);

        StudyBoard studyBoard1 = new StudyBoard("공지사항", study);
        study.addBoard(studyBoard1);

        studyRepository.save(study);

        StudyArticle studyArticle1 = new StudyArticle("공지사항 테스트 글", "공지사항 테스트 글입니다.", 0L, memberA, studyBoard1);

        studyArticleRepository.save(studyArticle1);

        StudyComment studyComment1 = new StudyComment(memberA, studyArticle1, null, "테스트 댓글1", false);
        StudyComment studyComment2 = new StudyComment(memberA, studyArticle1, studyComment1, "테스트 댓글2", false);
        StudyComment studyComment3 = new StudyComment(memberA, studyArticle1, studyComment1, "테스트 댓글3", false);
        StudyComment studyComment4 = new StudyComment(memberA, studyArticle1, studyComment1, "테스트 댓글4", false);

        studyCommentRepository.save(studyComment1);
        studyCommentRepository.save(studyComment2);
        studyCommentRepository.save(studyComment3);
        studyCommentRepository.save(studyComment4);
    }

    @Test
    @DisplayName("회원 ID로 회원이 작성한 모든 댓글을 조회한다.")
    public void findByMemberId() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);

        //when
        List<StudyComment> studyComments = studyCommentRepository.findAllByMemberId(member.getId());

        //then
        Assertions.assertEquals(4, studyComments.size());
    }

    @Test
    @DisplayName("게시물에 작성된 모든 댓글을 작성순으로 조회한다.")
    public void findAllByStudyArticleId() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드 모집").get(0);
        StudyArticle studyArticle = studyArticleRepository.findAll().get(0);

        //when
        List<StudyComment> studyComments = studyCommentRepository.findAllByStudyArticleId(studyArticle.getId());

        //then
        Assertions.assertEquals(4, studyComments.size());
        Assertions.assertEquals("테스트 댓글1", studyComments.get(0).getContent());
        Assertions.assertEquals("테스트 댓글2", studyComments.get(1).getContent());
        Assertions.assertEquals("테스트 댓글3", studyComments.get(2).getContent());
        Assertions.assertEquals("테스트 댓글4", studyComments.get(3).getContent());
    }
}