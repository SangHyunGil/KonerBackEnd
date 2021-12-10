package project.SangHyun.dto.request.study;

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
