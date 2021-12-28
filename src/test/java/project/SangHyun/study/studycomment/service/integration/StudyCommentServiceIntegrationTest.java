package project.SangHyun.study.studycomment.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.service.StudyArticleService;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentDeleteResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentUpdateResponseDto;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studycomment.service.StudyCommentService;
import project.SangHyun.study.studycomment.tools.StudyCommentFactory;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class StudyCommentServiceIntegrationTest {
    @Autowired
    StudyCommentService studyCommentService;
    @Autowired
    StudyArticleService studyArticleService;
    @Autowired
    StudyCommentRepository studyCommentRepository;
    @Autowired
    EntityManager em;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("새로운 댓글을 추가한다.")
    public void addComment() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(member, null);

        //when
        StudyCommentCreateResponseDto ActualResult = studyCommentService.createComment(studyArticle.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 댓글입니다.", ActualResult.getContent());
    }

    @Test
    @DisplayName("댓글에 답글을 추가한다.")
    public void addReplyComment() throws Exception {
        //given
        StudyComment comment = testDB.findParentComment();
        Member member = testDB.findStudyGeneralMember();
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(member, comment);

        //when
        StudyCommentCreateResponseDto ActualResult = studyCommentService.createComment(studyArticle.getId(), requestDto);
        persistContextClear();

        //then
        Assertions.assertEquals("테스트 댓글입니다.", ActualResult.getContent());
        Assertions.assertEquals(2, studyCommentRepository.findByMemberId(member.getId()).get(0).getChildren().size());
    }

    @Test
    @DisplayName("게시글이 삭제되면 해당 댓글들 모두 삭제된다.")
    public void onDeleteTest() throws Exception {
        //given
        StudyArticle announceArticle = testDB.findAnnounceArticle();

        //when
        StudyArticleDeleteResponseDto ActualResult = studyArticleService.deleteArticle(announceArticle.getId());
        persistContextClear();

        //then
        Assertions.assertEquals(0, studyCommentRepository.findAll().size());
    }

    @Test
    @DisplayName("댓글을 삭제한다.(부모가 삭제처리되지 않았다면 자신만 삭제처리된다.)")
    public void deleteComment() throws Exception {
        //given
        StudyComment studyComment = testDB.findChildComment();

        //when
        StudyCommentDeleteResponseDto ActualResult = studyCommentService.deleteComment(studyComment.getId());

        //then
        Assertions.assertEquals(1, studyCommentRepository.findAll().size());
    }

    @Test
    @DisplayName("댓글을 삭제한다.(부모가 삭제처리되어있다면 부모와 자신 모두 삭제처리된다.)")
    public void deleteComment2() throws Exception {
        //given
        persistContextClear();
        StudyComment parentComment = testDB.findParentComment();
        StudyComment studyComment = testDB.findChildComment();
        parentComment.delete();

        //when
        StudyCommentDeleteResponseDto ActualResult = studyCommentService.deleteComment(studyComment.getId());


        //then
        Assertions.assertEquals(0, studyCommentRepository.findAll().size());
    }

    private void persistContextClear() {
        em.flush();
        em.clear();
    }
}
