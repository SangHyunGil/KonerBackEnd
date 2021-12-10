package project.SangHyun.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.dto.request.study.StudyArticleUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleCreateResponseDto;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.StudyArticleService;
import project.SangHyun.dto.request.study.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyArticleFindResponseDto;
import project.SangHyun.dto.response.study.StudyArticleUpdateResponseDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/board/{boardId}/article")
public class StudyArticleController {

    public final StudyArticleService studyArticleService;
    public final ResponseServiceImpl responseService;

    @GetMapping
    public MultipleResult<StudyArticleFindResponseDto> findStudyArticles(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                         @PathVariable Long studyId, @PathVariable Long boardId) {
        log.info("findStudyArticles = {}, {}, {}, {}", memberDetails.getId(), memberDetails.getUsername(), studyId, boardId);
        return responseService.getMultipleResult(studyArticleService.findAllArticles(memberDetails.getId(), studyId, boardId));
    }

    @PostMapping
    public SingleResult<StudyArticleCreateResponseDto> createStudyArticle(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                          @PathVariable Long studyId, @PathVariable Long boardId,
                                                                          @Valid @RequestBody StudyArticleCreateRequestDto requestDto) {
        return responseService.getSingleResult(studyArticleService.createArticle(memberDetails.getId(), studyId, boardId, requestDto));
    }

    @PutMapping("/{articleId}")
    public SingleResult<StudyArticleUpdateResponseDto> updateStudyArticle(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                          @PathVariable Long studyId, @PathVariable Long boardId, @PathVariable Long articleId,
                                                                          @Valid @RequestBody StudyArticleUpdateRequestDto requestDto) {
        return responseService.getSingleResult(studyArticleService.updateArticle(memberDetails.getId(), studyId, articleId, requestDto));
    }

    @DeleteMapping("/{articleId}")
    public SingleResult<StudyArticleDeleteResponseDto> deleteStudyArticle(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                          @PathVariable Long studyId, @PathVariable Long boardId, @PathVariable Long articleId) {
        return responseService.getSingleResult(studyArticleService.deleteArticle(memberDetails.getId(), studyId, articleId));
    }
}
