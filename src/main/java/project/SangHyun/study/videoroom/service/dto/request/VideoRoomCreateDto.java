package project.SangHyun.study.videoroom.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.videoroom.domain.VideoRoom;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("화상회의 방 생성 요청 서비스 계층 DTO")
public class VideoRoomCreateDto {

    @ApiModelProperty(name = "화상회의 방 요청 구분")
    @NotBlank(message = "화상회의 요청 구분을 입력해주세요.")
    private String request;

    @ApiModelProperty(name = "화상회의 방 생성자 ID(PK)")
    private Long memberId;

    @ApiModelProperty(name = "화상회의 방 제목")
    private String title;

    @ApiModelProperty(name = "화상회의 방 비밀번호")
    private String pin;

    public VideoRoom toEntity(Long roomId, Member member) {
        return VideoRoom.builder()
                .title(title)
                .pin(pin)
                .roomId(roomId)
                .member(member)
                .build();
    }
}
