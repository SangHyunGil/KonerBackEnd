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
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;
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
        StudyComment comment = testDB.findParentComment().get(0);
        Member member = testDB.findStudyGeneralMember();
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyCommentCreateRequestDto requestDto = StudyCommentFactory.makeCreateRequestDto(member, comment);

        //when
        StudyCommentCreateResponseDto ActualResult = studyCommentService.createComment(studyArticle.getId(), requestDto);
        em.flush();
        em.clear();

        //then
        Assertions.assertEquals("테스트 댓글입니다.", ActualResult.getContent());
        Assertions.assertEquals(3, studyCommentRepository.findByMemberId(member.getId()).get(0).getChildren().size());
    }

    @Test
    @DisplayName("댓글을 수정한다.")
    public void updateComment() throws Exception {
        //given
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyCommentUpdateRequestDto requestDto = StudyCommentFactory.makeUpdateRequestDto();

        //when
        StudyCommentUpdateResponseDto ActualResult = studyCommentService.updateComment(studyArticle.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 댓글 수정입니다.", ActualResult.getContent());
    }
}
