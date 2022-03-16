package project.SangHyun.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import project.SangHyun.dto.response.PageResponseDto;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;

import java.util.List;

class PageResponseDtoTest {
    @Test
    @DisplayName("페이징 엔티티에 대해 Dto를 구성한다.")
    public void makeCreate() throws Exception {
        //given
        List<StudyArticle> studyArticles = List.of(new StudyArticle("테스트", "테스트", 0L, new Member("xptmxm1!", "xptmxm1!", "승범", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "승범입니다."), new StudyBoard(1L)));
        Page<StudyArticle> pageStudyArticle = new PageImpl<>(studyArticles, PageRequest.of(0, 10), studyArticles.size());

        //when
        PageResponseDto responseDto = PageResponseDto.create(pageStudyArticle, StudyArticleDto::create);

        //then
        Assertions.assertEquals(1, responseDto.getTotalPages());
        Assertions.assertEquals(1, responseDto.getNumberOfElements());
        Assertions.assertEquals(false, responseDto.isHasNext());
    }
}