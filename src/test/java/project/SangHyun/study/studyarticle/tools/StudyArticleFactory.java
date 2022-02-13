package project.SangHyun.study.studyarticle.tools;

import org.springframework.data.domain.Page;
import project.SangHyun.BasicFactory;
import project.SangHyun.common.dto.PageResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.response.StudyArticleResponseDto;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.service.dto.StudyArticleCreateDto;
import project.SangHyun.study.studyarticle.service.dto.StudyArticleDto;
import project.SangHyun.study.studyarticle.service.dto.StudyArticleUpdateDto;

public class StudyArticleFactory extends BasicFactory {
    // Request
    public static StudyArticleCreateRequestDto makeCreateRequestDto(Member member) {
        return new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");
    }

    public static StudyArticleCreateDto makeCreateDto(Member member) {
        return new StudyArticleCreateDto(member.getId(), "테스트 글", "테스트 내용");
    }

    public static StudyArticleUpdateRequestDto makeUpdateRequestDto(String title, String content) {
        return new StudyArticleUpdateRequestDto("테스트 글 수정", "테스트 내용 수정");
    }

    public static StudyArticleUpdateDto makeUpdateDto(String title, String content) {
        return new StudyArticleUpdateDto("테스트 글 수정", "테스트 내용 수정");
    }

    // Response
    public static StudyArticleDto makeDto(StudyArticle studyArticle) {
        return StudyArticleDto.create(studyArticle);
    }

    public static StudyArticleResponseDto makeResponseDto(StudyArticleDto studyArticleDto) {
        return StudyArticleResponseDto.create(studyArticleDto);
    }

    public static PageResponseDto makeFindAllResponseDto(Page<StudyArticle> studyArticle) {
        return PageResponseDto.create(studyArticle, StudyArticleDto::create);
    }
}
