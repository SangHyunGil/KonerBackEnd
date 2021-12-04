package project.SangHyun.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudyJoinCountResponseDto {
    private Long count;

    public static StudyJoinCountResponseDto createDto(Long count) {
        return StudyJoinCountResponseDto.builder()
                .count(count)
                .build();
    }

    @Builder
    public StudyJoinCountResponseDto(Long count) {
        this.count = count;
    }
}
