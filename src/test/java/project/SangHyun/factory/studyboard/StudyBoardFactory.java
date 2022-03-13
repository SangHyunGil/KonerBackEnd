package project.SangHyun.factory.studyboard;

import project.SangHyun.factory.BasicFactory;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.response.StudyBoardResponseDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.service.dto.request.StudyBoardCreateDto;
import project.SangHyun.study.studyboard.service.dto.request.StudyBoardUpdateDto;
import project.SangHyun.study.studyboard.service.dto.response.StudyBoardDto;

public class StudyBoardFactory extends BasicFactory {
    // Request
    public static StudyBoardCreateRequestDto makeCreateRequestDto() {
        return new StudyBoardCreateRequestDto("테스트 게시판");
    }

    public static StudyBoardCreateDto makeCreateDto() {
        return new StudyBoardCreateDto("테스트 게시판");
    }

    public static StudyBoardUpdateRequestDto makeUpdateRequestDto(String title) {
        return new StudyBoardUpdateRequestDto(title);
    }

    public static StudyBoardUpdateDto makeUpdateDto(String title) {
        return new StudyBoardUpdateDto(title);
    }

    // Response
    public static StudyBoardDto makeDto(StudyBoard studyBoard) {
        return StudyBoardDto.create(studyBoard);
    }

    public static StudyBoardResponseDto makeResponseDto(StudyBoardDto studyBoardDto) {
        return StudyBoardResponseDto.create(studyBoardDto);
    }
}
