package project.SangHyun.study.studyboard.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardFindResponseDto;

public class StudyBoardFactory extends BasicFactory {
    // Request
    public static StudyBoardCreateRequestDto makeCreateDto() {
        return new StudyBoardCreateRequestDto("테스트 게시판");
    }

    public static StudyBoardUpdateRequestDto makeUpdateDto(String title) {
        return new StudyBoardUpdateRequestDto(title);
    }

    // Response
    public static StudyBoardCreateResponseDto makeCreateResponseDto(StudyBoard studyBoard) {
        return StudyBoardCreateResponseDto.create(studyBoard);
    }

    public static StudyBoardFindResponseDto makeFindResponseDto(StudyBoard studyBoard) {
        return StudyBoardFindResponseDto.create(studyBoard);
    }
}
