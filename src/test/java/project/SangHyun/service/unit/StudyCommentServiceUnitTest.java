package project.SangHyun.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.factory.study.StudyFactory;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studycomment.service.StudyCommentService;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentCreateDto;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentUpdateDto;
import project.SangHyun.study.studycomment.service.dto.response.StudyCommentDto;
import project.SangHyun.factory.studycomment.StudyCommentFactory;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class StudyCommentServiceUnitTest {
    Member member;
    StudyArticle studyArticle;
    StudyComment studyComment;
    StudyComment studyReplyComment;
    StudyComment studyReplyComment2;

    @InjectMocks
    StudyCommentService studyCommentService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    StudyArticleRepository studyArticleRepository;
    @Mock
    StudyCommentRepository studyCommentRepository;

    @BeforeEach
    public void init() {
        member = StudyFactory.makeTestAuthMember();
        Study study = StudyCommentFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        StudyJoin studyJoin = StudyCommentFactory.makeTestStudyJoinCreator(member, study);
        study.join(studyJoin);
        StudyBoard studyBoard = StudyCommentFactory.makeTestStudyBoard(study);
        study.addBoard(studyBoard);
        studyArticle = StudyCommentFactory.makeTestStudyArticle(member, studyBoard);
        studyComment = StudyCommentFactory.makeTestStudyComment(member, studyArticle);
        studyReplyComment = StudyCommentFactory.makeTestStudyReplyComment(member, studyArticle, studyComment);
    }

    @Test
    @DisplayName("????????? ????????? ????????????.")
    public void addComment() throws Exception {
        //given
        StudyCommentCreateDto requestDto = StudyCommentFactory.makeCreateDto(member, null);
        StudyComment studyComment = requestDto.toEntity(member, studyArticle);
        StudyCommentDto ExpectResult = StudyCommentDto.create(studyComment);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));
        given(studyCommentRepository.save(any())).willReturn(this.studyComment);

        //when
        StudyCommentDto ActualResult = studyCommentService.createComment(studyArticle.getId(), requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getContent(), ActualResult.getContent());
    }

    @Test
    @DisplayName("????????? ????????? ????????????.")
    public void addReplyComment() throws Exception {
        //given
        StudyCommentCreateDto requestDto = StudyCommentFactory.makeCreateDto(member, studyComment);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));
        given(studyCommentRepository.save(any())).willReturn(studyReplyComment);

        //when
        StudyCommentDto ActualResult = studyCommentService.createComment(studyArticle.getId(), requestDto);

        //then
        Assertions.assertEquals(2L, ActualResult.getId());
    }

    @Test
    @DisplayName("????????? ????????????.")
    public void updateComment() throws Exception {
        //given
        StudyCommentUpdateDto requestDto = StudyCommentFactory.makeUpdateDto("????????? ?????? ???????????????.");

        //mocking
        given(studyCommentRepository.findById(any())).willReturn(java.util.Optional.ofNullable(studyComment));

        //when
        StudyCommentDto ActualResult = studyCommentService.updateComment(studyComment.getId(), requestDto);

        //then
        Assertions.assertEquals("????????? ?????? ???????????????.", ActualResult.getContent());
    }

    @Test
    @DisplayName("????????? ????????????.(????????? ?????????????????? ???????????? ????????? ??????????????????.)")
    public void deleteComment() throws Exception {
        //given

        //mocking
        given(studyCommentRepository.findById(any())).willReturn(Optional.ofNullable(studyReplyComment));
        willDoNothing().given(studyCommentRepository).delete(any());

        //when, then
        Assertions.assertDoesNotThrow(() -> studyCommentService.deleteComment(studyReplyComment.getId()));
    }

    @Test
    @DisplayName("????????? ????????????.(????????? ?????????????????? ????????? ????????? ??????????????????.)")
    public void deleteComment2() throws Exception {
        //given
        studyComment.delete();

        //mocking
        given(studyCommentRepository.findById(any())).willReturn(Optional.ofNullable(studyReplyComment));
        willDoNothing().given(studyCommentRepository).delete(any());

        //when
        Assertions.assertDoesNotThrow(() -> studyCommentService.deleteComment(studyReplyComment.getId()));
    }
}
