package project.SangHyun.study.studyboard.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardDeleteResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardUpdateResponseDto;

public class StudyBoardFactory extends BasicFactory {
    // Request
    public static StudyBoardCreateRequestDto makeCreateRequestDto() {
        return new StudyBoardCreateRequestDto("테스트 게시판");
    }

    public static StudyBoardUpdateRequestDto makeUpdateRequestDto(String title) {
        return new StudyBoardUpdateRequestDto(title);
    }

    // Response
    public static StudyBoardCreateResponseDto makeCreateResponseDto(StudyBoard studyBoard) {
        return StudyBoardCreateResponseDto.create(studyBoard);
    }

    public static StudyBoardFindResponseDto makeFindResponseDto(StudyBoard studyBoard) {
        return StudyBoardFindResponseDto.create(studyBoard);
    }

    public static StudyBoardUpdateResponseDto makeUpdateResponseDto(StudyBoard studyBoard, String title) {
        StudyBoardUpdateResponseDto responseDto = StudyBoardUpdateResponseDto.create(studyBoard);
        responseDto.setTitle(title);
        return responseDto;
    }

    public static StudyBoardDeleteResponseDto makeDeleteResponseDto(StudyBoard studyBoard) {
        return StudyBoardDeleteResponseDto.create(studyBoard);
    }
}
