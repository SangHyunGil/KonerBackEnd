package project.SangHyun.common.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Schedule;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyOptions;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.domain.enums.RecruitState;
import project.SangHyun.study.study.domain.enums.StudyMethod;
import project.SangHyun.study.study.domain.enums.StudyState;

import java.util.ArrayList;
import java.util.List;

class SliceResponseDtoTest {
    @Test
    @DisplayName("페이징 엔티티에 대해 Dto를 구성한다.")
    public void makeCreate() throws Exception {
        //given
        List<Study> studies = List.of(new Study("테스트", List.of("테스트"), "테스트", "profile", "컴퓨터공학과", new StudyOptions(StudyState.STUDYING, RecruitState.PROCEED, StudyMethod.FACE), 2L, new Schedule("2021-10-01", "2021-12-25"), new Member(1L), new ArrayList<>(), new ArrayList<>()));
        Slice<Study> sliceStudy = new SliceImpl<>(studies, Pageable.ofSize(6), studies.size() > 6);

        //when
        SliceResponseDto responseDto = SliceResponseDto.create(sliceStudy, StudyFindResponseDto::create);

        //then
        Assertions.assertEquals(1, responseDto.getNumberOfElements());
        Assertions.assertEquals(false, responseDto.isHasNext());
    }
}