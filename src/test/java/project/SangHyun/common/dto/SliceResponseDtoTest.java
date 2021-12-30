package project.SangHyun.common.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyMethod;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;

import java.util.ArrayList;
import java.util.List;

class SliceResponseDtoTest {
    @Test
    @DisplayName("페이징 엔티티에 대해 Dto를 구성한다.")
    public void makeCreate() throws Exception {
        //given
        List<Study> studies = List.of(new Study("테스트", List.of("테스트"), "테스트", "profile", "컴퓨터공학과", StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE, 2L, "schedule", new Member(1L), new ArrayList<>(), new ArrayList<>()));
        Slice<Study> sliceStudy = new SliceImpl<>(studies, Pageable.ofSize(6), studies.size() > 6);

        //when
        SliceResponseDto responseDto = SliceResponseDto.create(sliceStudy, StudyFindResponseDto::create);

        //then
        Assertions.assertEquals(1, responseDto.getNumberOfElements());
        Assertions.assertEquals(false, responseDto.isHasNext());
    }

    @Test
    @DisplayName("페이징 엔티티에 대해 Dto를 구성한다.")
    public void makeCreate2() throws Exception {
       //given
        List<Study> studies = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            studies.add(new Study("테스트", List.of("테스트"), "테스트", "profile", "컴퓨터공학과", StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE, 2L, "schedule", new Member(1L), new ArrayList<>(), new ArrayList<>()));
        }
        Slice<Study> sliceStudy = new SliceImpl<>(studies, Pageable.ofSize(6), studies.size() > 6);

        //when
        SliceResponseDto responseDto = SliceResponseDto.create(sliceStudy, StudyFindResponseDto::create);

        //then
        Assertions.assertEquals(11, responseDto.getNumberOfElements());
        Assertions.assertEquals(true, responseDto.isHasNext());
    }
}