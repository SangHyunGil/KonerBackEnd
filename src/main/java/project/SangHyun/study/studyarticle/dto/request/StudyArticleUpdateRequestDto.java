package project.SangHyun.study.studyarticle.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyArticleUpdateRequestDto {
    private String title;
    private String content;
}
