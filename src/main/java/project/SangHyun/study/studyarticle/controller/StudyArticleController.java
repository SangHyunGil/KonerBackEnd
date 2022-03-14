package project.SangHyun.study.studyarticle.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.PageResponseDto;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleImageUploadRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.controller.dto.response.StudyArticleImageResponseDto;
import project.SangHyun.study.studyarticle.controller.dto.response.StudyArticleResponseDto;
import project.SangHyun.study.studyarticle.service.StudyArticleService;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleDto;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleImageDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/board/{boardId}/article")
public class StudyArticleController {

    public final StudyArticleService studyArticleService;
    public final ResponseService responseService;

    @ApiOperation(value = "스터디 게시글 모두 찾기", notes = "스터디에 포함된 게시글을 모두 찾는다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<PageResponseDto> findStudyArticles(@PathVariable Long studyId, @PathVariable Long boardId,
                                                           @RequestParam Integer page, @RequestParam Integer size) {
        return responseService.getSingleResult(studyArticleService.findAllArticles(boardId, page, size));
    }

    @ApiOperation(value = "스터디 게시글 생성", notes = "스터디에 포함된 게시글을 생성한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResult<StudyArticleResponseDto> createStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                    @Valid @RequestBody StudyArticleCreateRequestDto requestDto) {
        StudyArticleDto articleDto = studyArticleService.createArticle(boardId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyArticleResponseDto.create(articleDto));
    }

    @ApiOperation(value = "스터디 게시글 이미지 업로드", notes = "스터디에 포함된 게시글의 이미지를 업로드한다.")
    @PostMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<StudyArticleImageResponseDto> uploadStudyArticleImage(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                        @Valid @RequestBody StudyArticleImageUploadRequestDto requestDto) {
        List<StudyArticleImageDto> articleImageDto = studyArticleService.uploadImages(requestDto.toServiceDto());
        List<StudyArticleImageResponseDto> responseDto = responseService.convertToControllerDto(articleImageDto, StudyArticleImageResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "스터디 게시글 세부사항 찾기", notes = "스터디에 포함된 게시글의 세부사항을 찾는다.")
    @GetMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<StudyArticleResponseDto> findStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                      @PathVariable Long articleId) {
        StudyArticleDto articleDto = studyArticleService.findArticle(articleId);
        return responseService.getSingleResult(StudyArticleResponseDto.create(articleDto));
    }

    @ApiOperation(value = "스터디 게시글 수정", notes = "스터디에 포함된 게시글을 수정한다.")
    @PutMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<StudyArticleResponseDto> updateStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId, @PathVariable Long articleId,
                                                                          @Valid @RequestBody StudyArticleUpdateRequestDto requestDto) {
        StudyArticleDto articleDto = studyArticleService.updateArticle(articleId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyArticleResponseDto.create(articleDto));
    }

    @ApiOperation(value = "스터디 게시글 삭제", notes = "스터디에 포함된 게시글을 삭제한다.")
    @DeleteMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteStudyArticle(@PathVariable Long studyId, @PathVariable Long boardId,
                                     @PathVariable Long articleId) {
        studyArticleService.deleteArticle(articleId);
        return responseService.getDefaultSuccessResult();
    }
}
