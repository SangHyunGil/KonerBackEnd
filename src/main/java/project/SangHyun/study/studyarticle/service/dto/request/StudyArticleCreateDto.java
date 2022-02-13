package project.SangHyun.study.studyarticle.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시글 요청 서비스 계층 DTO")
public class StudyArticleCreateDto {

    @ApiModelProperty(value = "스터디 게시글 생성자")
    private Long memberId;

    @ApiModelProperty(value = "스터디 게시글 제목")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용")
    private String content;

    public StudyArticle toEntity(Member member, StudyBoard studyBoard) {
        return StudyArticle.builder()
                .studyBoard(studyBoard)
                .member(member)
                .title(title)
                .content(content)
                .views(0L)
                .build();
    }
}
