package project.SangHyun.study.videoroom.service.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.dto.MemberProfile;
import project.SangHyun.study.videoroom.domain.VideoRoom;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomDto {

    @ApiModelProperty(name = "화상회의 방 ID")
    private Long roomId;

    @ApiModelProperty(name = "화상회의 방 제목")
    private String title;

    @ApiModelProperty(name = "화상회의 방 비밀번호")
    private String pin;

    @ApiModelProperty(name = "화상회의 방 생성자")
    private MemberProfile creator;

    public static VideoRoomDto create(VideoRoom room) {
        return new VideoRoomDto(room.getRoomId(), room.getTitleName(),
                room.getPin(), MemberProfile.create(room));
    }

}
