package project.SangHyun.study.videoroom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.study.videoroom.service.VideoRoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/videoroom")
public class VideoRoomController {

    private final VideoRoomService videoRoomService;
    private final ResponseService responseService;

}
