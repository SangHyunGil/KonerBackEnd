package project.SangHyun.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomCreateRequestDto {
    private String roomName;
    private Long memberId;

}
