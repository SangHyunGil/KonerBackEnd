package project.SangHyun.common.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import project.SangHyun.common.dto.response.SliceResponseDto;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.service.dto.response.StudyDto;

import java.util.ArrayList;
import java.util.List;

class SliceResponseDtoTest {
    @Test
    @DisplayName("페이징 엔티티에 대해 Dto를 구성한다.")
    public void makeCreate() throws Exception {
        //given
        List<Study> studies = List.of(new Study("테스트", List.of("백엔드"), "테스트", "profile", 2L, "2021-10-01", "2021-12-25", StudyCategory.CSE,  StudyMethod.FACE, StudyState.STUDYING, RecruitState.PROCEED, new Member("xptmxm1!", "xptmxm1!", "승범", Department.CSE, "C:\\Users\\Family\\Pictures\\Screenshots\\1.png", MemberRole.ROLE_MEMBER, "승범입니다."), new ArrayList<>(), new ArrayList<>()));
        Slice<Study> sliceStudy = new SliceImpl<>(studies, Pageable.ofSize(6), studies.size() > 6);


        //when
        SliceResponseDto responseDto = SliceResponseDto.create(sliceStudy, StudyDto::create);

        //then
        Assertions.assertEquals(1, responseDto.getNumberOfElements());
        Assertions.assertEquals(false, responseDto.isHasNext());
    }
}