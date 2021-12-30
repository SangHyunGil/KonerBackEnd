package project.SangHyun.study.studyarticle.tools;

import org.springframework.data.domain.Page;
import project.SangHyun.BasicFactory;
import project.SangHyun.common.dto.PageResponseDto;
import project.SangHyun.common.dto.SliceResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;

public class StudyArticleFactory extends BasicFactory {
    // Request
    public static StudyArticleCreateRequestDto makeCreateDto(Member member) {
        return new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");
    }

    public static StudyArticleUpdateRequestDto makeUpdateDto(String title, String content) {
        return new StudyArticleUpdateRequestDto("테스트 글 수정", "테스트 내용 수정");
    }

    // Response
    public static StudyArticleCreateResponseDto makeCreateResponseDto(StudyArticle studyArticle) {
        return StudyArticleCreateResponseDto.create(studyArticle);
    }

    public static PageResponseDto makeFindAllResponseDto(Page<StudyArticle> studyArticle) {
        return PageResponseDto.create(studyArticle, StudyArticleFindResponseDto::create);
    }

    public static StudyArticleFindResponseDto makeFindResponseDto(StudyArticle studyArticle) {
        return StudyArticleFindResponseDto.create(studyArticle);
    }

    public static StudyArticleUpdateResponseDto makeUpdateResponseDto(StudyArticle studyArticle, String title, String content) {
        StudyArticleUpdateResponseDto responseDto = StudyArticleUpdateResponseDto.create(studyArticle);
        responseDto.setTitle(title);
        responseDto.setContent(content);
        return responseDto;
    }

    public static StudyArticleDeleteResponseDto makeDeleteResponseDto(StudyArticle studyArticle) {
        return StudyArticleDeleteResponseDto.create(studyArticle);
    }
}
