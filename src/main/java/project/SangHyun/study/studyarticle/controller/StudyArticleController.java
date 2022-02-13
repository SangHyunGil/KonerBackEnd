package project.SangHyun.study.studyarticle.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.dto.response.PageResponseDto;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.response.StudyArticleResponseDto;
import project.SangHyun.study.studyarticle.service.StudyArticleService;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/board/{boardId}/article")
public class StudyArticleController {

    public final StudyArticleService studyArticleService;
    public final ResponseService responseService;

    @ApiOperation(value = "스터디 게시글 모두 찾기", notes = "스터디에 포함된 게시글을 모두 찾는다.")
    @GetMapping
    public SingleResult<PageResponseDto> findStudyArticles(@PathVariable Long studyId, @PathVariable Long boardId,
                                                           @RequestParam Integer page, @RequestParam Integer size) {
        return responseService.getSingleResult(studyArticleService.findAllArticles(boardId, page, size));
    }

    @ApiOperation(value = "스터디 게시글 생성", notes = "스터디에 포함된 게시글을 생성한다.")
    @PostMapping
    public SingleResult<StudyArticleResponseDto> createStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                    @Valid @RequestBody StudyArticleCreateRequestDto requestDto) {
        StudyArticleDto articleDto = studyArticleService.createArticle(boardId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyArticleResponseDto.create(articleDto));
    }

    @ApiOperation(value = "스터디 게시글 세부사항 찾기", notes = "스터디에 포함된 게시글의 세부사항을 찾는다.")
    @GetMapping("/{articleId}")
    public SingleResult<StudyArticleResponseDto> findStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                      @PathVariable Long articleId) {
        StudyArticleDto articleDto = studyArticleService.findArticle(articleId);
        return responseService.getSingleResult(StudyArticleResponseDto.create(articleDto));
    }

    @ApiOperation(value = "스터디 게시글 수정", notes = "스터디에 포함된 게시글을 수정한다.")
    @PutMapping("/{articleId}")
    public SingleResult<StudyArticleResponseDto> updateStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId, @PathVariable Long articleId,
                                                                          @Valid @RequestBody StudyArticleUpdateRequestDto requestDto) {
        StudyArticleDto articleDto = studyArticleService.updateArticle(articleId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyArticleResponseDto.create(articleDto));
    }

    @ApiOperation(value = "스터디 게시글 삭제", notes = "스터디에 포함된 게시글을 삭제한다.")
    @DeleteMapping("/{articleId}")
    public Result deleteStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                     @PathVariable Long articleId) {
        studyArticleService.deleteArticle(articleId);
        return responseService.getDefaultSuccessResult();
    }
}
