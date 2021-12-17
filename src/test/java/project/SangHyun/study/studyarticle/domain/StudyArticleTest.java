package project.SangHyun.study.studyarticle.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;

class StudyArticleTest {
    @Test
    @DisplayName("스터디 게시글에 대한 정보를 수정한다.")
    public void updateStudyArticle() throws Exception {
        //given
        Member member = new Member(1L);
        StudyBoard studyBoard = new StudyBoard(1L);
        StudyArticle studyArticle = new StudyArticle("테스트 게시글", "하이요", member, studyBoard);

        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("수정", "수정!");

        //when
        studyArticle.updateArticleInfo(requestDto);

        //then
        Assertions.assertEquals("수정", studyArticle.getTitle());
    }
}