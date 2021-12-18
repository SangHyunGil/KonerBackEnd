package project.SangHyun.study.studyarticle.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;
import project.SangHyun.study.studyarticle.service.StudyArticleService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/board/{boardId}/article")
public class StudyArticleController {

    public final StudyArticleService studyArticleService;
    public final ResponseServiceImpl responseService;

    @ApiOperation(value = "스터디 게시글 모두 찾기", notes = "스터디에 포함된 게시글을 모두 찾는다.")
    @GetMapping
    public MultipleResult<StudyArticleFindResponseDto> findStudyArticles(@PathVariable Long studyId, @PathVariable Long boardId) {
        return responseService.getMultipleResult(studyArticleService.findAllArticles(studyId, boardId));
    }

    @ApiOperation(value = "스터디 게시글 생성", notes = "스터디에 포함된 게시글을 생성한다.")
    @PostMapping
    public SingleResult<StudyArticleCreateResponseDto> createStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                          @Valid @RequestBody StudyArticleCreateRequestDto requestDto) {
        return responseService.getSingleResult(studyArticleService.createArticle(studyId, boardId, requestDto));
    }

    @ApiOperation(value = "스터디 게시글 세부사항 찾기", notes = "스터디에 포함된 게시글의 세부사항을 찾는다.")
    @GetMapping("/{articleId}")
    public SingleResult<StudyArticleFindResponseDto> findStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                      @PathVariable Long articleId) {
        return responseService.getSingleResult(studyArticleService.findArticle(studyId, articleId));
    }

    @ApiOperation(value = "스터디 게시글 수정", notes = "스터디에 포함된 게시글을 수정한다.")
    @PutMapping("/{articleId}")
    public SingleResult<StudyArticleUpdateResponseDto> updateStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId, @PathVariable Long articleId,
                                                                          @Valid @RequestBody StudyArticleUpdateRequestDto requestDto) {
        return responseService.getSingleResult(studyArticleService.updateArticle(studyId, articleId, requestDto));
    }

    @ApiOperation(value = "스터디 게시글 삭제", notes = "스터디에 포함된 게시글을 삭제한다.")
    @DeleteMapping("/{articleId}")
    public SingleResult<StudyArticleDeleteResponseDto> deleteStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                          @PathVariable Long articleId) {
        return responseService.getSingleResult(studyArticleService.deleteArticle(studyId, articleId));
    }
}
