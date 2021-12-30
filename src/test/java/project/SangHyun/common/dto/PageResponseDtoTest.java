package project.SangHyun.common.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;

import java.util.ArrayList;
import java.util.List;

class PageResponseDtoTest {
    @Test
    @DisplayName("페이징 엔티티에 대해 Dto를 구성한다.")
    public void makeCreate() throws Exception {
        //given
        List<StudyArticle> studyArticles = List.of(new StudyArticle("테스트", "테스트", 0L, new Member(1L), new StudyBoard(1L)));
        Page<StudyArticle> pageStudyArticle = new PageImpl<>(studyArticles, PageRequest.of(0, 10), studyArticles.size());

        //when
        PageResponseDto responseDto = PageResponseDto.create(pageStudyArticle, StudyArticleFindResponseDto::create);

        //then
        Assertions.assertEquals(1, responseDto.getTotalPages());
        Assertions.assertEquals(1, responseDto.getNumberOfElements());
        Assertions.assertEquals(false, responseDto.isHasNext());
    }

    @Test
    @DisplayName("페이징 엔티티에 대해 Dto를 구성한다.")
    public void makeCreate2() throws Exception {
        //given
        List<StudyArticle> studyArticles = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            studyArticles.add(new StudyArticle("테스트", "테스트", 0L, new Member(1L), new StudyBoard(1L)));
        }
        Page<StudyArticle> pageStudyArticle = new PageImpl<>(studyArticles, PageRequest.of(0, 10), studyArticles.size());

        //when
        PageResponseDto responseDto = PageResponseDto.create(pageStudyArticle, StudyArticleFindResponseDto::create);

        //then
        Assertions.assertEquals(2, responseDto.getTotalPages());
        Assertions.assertEquals(11, responseDto.getNumberOfElements());
        Assertions.assertEquals(true, responseDto.isHasNext());
    }
}