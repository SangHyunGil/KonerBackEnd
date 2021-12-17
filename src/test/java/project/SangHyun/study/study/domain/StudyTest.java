package project.SangHyun.study.study.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {
    @Test
    @DisplayName("스터디 정보를 업데이트한다.")
    public void updateStudyInfo() throws Exception {
        //given
        Member member = new Member(1L);
        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
        Study study = new Study("백엔드 스터디", "백엔드", "백엔드 스터디 모집합니다!", StudyState.STUDYING, RecruitState.PROCEED,
                2L, member, studyJoins, studyBoards);

        StudyUpdateRequestDto requestDto = new StudyUpdateRequestDto("프론트엔드 모집", "프론트엔드", "음..", 2L, StudyState.STUDYING, RecruitState.PROCEED);

        //when
        Study ActualResult = study.updateStudyInfo(requestDto);

        //then
        Assertions.assertEquals("프론트엔드 모집", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디에 참여한다.")
    public void joinStudy() throws Exception {
        //given
        Member member = new Member(1L);
        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
        Study study = new Study("백엔드 스터디", "백엔드", "백엔드 스터디 모집합니다!", StudyState.STUDYING, RecruitState.PROCEED,
                2L, member, studyJoins, studyBoards);

        StudyJoin studyJoin = new StudyJoin(1L);
        //when
        study.join(studyJoin);

        //then
        Assertions.assertEquals(2, study.getStudyJoins().size());
    }

    @Test
    @DisplayName("스터디 게시판을 추가한다.")
    public void addBoard() throws Exception {
        //given
        Member member = new Member(1L);
        List<StudyJoin> studyJoins = new ArrayList<>(List.of(new StudyJoin(1L)));
        List<StudyBoard> studyBoards = new ArrayList<>(List.of(new StudyBoard(1L)));
        Study study = new Study("백엔드 스터디", "백엔드", "백엔드 스터디 모집합니다!", StudyState.STUDYING, RecruitState.PROCEED,
                2L, member, studyJoins, studyBoards);

        StudyBoard studyBoard = new StudyBoard(1L);
        //when
        study.addBoard(studyBoard);

        //then
        Assertions.assertEquals(2, study.getStudyBoards().size());
    }
}