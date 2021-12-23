package project.SangHyun.study.studyarticle.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<StudyArticleFindResponseDto> makeFindAllResponseDto(StudyArticle ... studyArticles) {
        return Arrays.stream(studyArticles)
                    .map(studyArticle -> StudyArticleFindResponseDto.create(studyArticle))
                    .collect(Collectors.toList());
    }

    public static StudyArticleFindResponseDto makeFindResponseDto(StudyArticle studyArticle) {
        return StudyArticleFindResponseDto.create(studyArticle);
    }

    public static StudyArticleUpdateResponseDto makeUpdateResponseDto(StudyArticle studyArticle) {
        return StudyArticleUpdateResponseDto.create(studyArticle);
    }

    public static StudyArticleDeleteResponseDto makeDeleteResponseDto(StudyArticle studyArticle) {
        return StudyArticleDeleteResponseDto.create(studyArticle);
    }
}
