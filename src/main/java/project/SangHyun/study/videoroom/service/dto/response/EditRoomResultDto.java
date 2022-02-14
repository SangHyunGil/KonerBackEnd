package project.SangHyun.study.videoroom.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditRoomResultDto {
    private String janus;
    private String transaction;
    private EditRoomResultResponseDto response;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class EditRoomResultResponseDto {
        private String videoroom;
        private Long room;
    }
}
