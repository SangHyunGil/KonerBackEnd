package project.SangHyun.study.videoroom.tools;

import project.SangHyun.study.videoroom.service.dto.request.CreateRoomRequestDto;
import project.SangHyun.study.videoroom.service.dto.request.DestroyRoomRequestDto;
import project.SangHyun.study.videoroom.service.dto.request.EditRoomRequestDto;

public class RoomTestCaseFactory {

    public static CreateRoomRequestDto createRoomRequestDto() {
        return new CreateRoomRequestDto("create", "제목", "11", "상현");
    }

    public static EditRoomRequestDto editRoomRequestDto() {
        return new EditRoomRequestDto("edit", "제목 수정", "22");
    }

    public static DestroyRoomRequestDto destroyRoomRequestDto(Long number) {
        return new DestroyRoomRequestDto("destroy", number);
    }
}
