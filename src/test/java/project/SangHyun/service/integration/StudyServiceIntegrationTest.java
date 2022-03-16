package project.SangHyun.service.integration;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.dto.response.SliceResponseDto;
import project.SangHyun.factory.study.StudyFactory;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.service.dto.request.StudyCreateDto;
import project.SangHyun.study.study.service.dto.request.StudyUpdateDto;
import project.SangHyun.study.study.service.dto.response.StudyDto;

import java.util.List;


class StudyServiceIntegrationTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("스터디를 생성한다.")
    public void createStudy() throws Exception {
        //given
        StudyCreateDto requestDto = StudyFactory.makeCreateDto(studyMember);

        //when
        StudyDto ActualResult = studyService.createStudy(requestDto);
        Study study = studyRepository.findStudyByTitle("프론트엔드 모집").get(0);

        //then
        Assertions.assertEquals(study.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("모든 스터디 정보를 로드한다.")
    public void loadStudyInfo() throws Exception {
        //given

        //when
        SliceResponseDto ActualResult = studyService.findAllStudiesByDepartment(Long.MAX_VALUE, StudyCategory.CSE, 2);

        //then
        Assertions.assertEquals(2, ActualResult.getNumberOfElements());
        Assertions.assertEquals(2, ActualResult.getData().size());
        Assertions.assertEquals(false, ActualResult.isHasNext());
    }

    @Test
    @DisplayName("스터디에 대한 세부정보를 로드한다.")
    public void loadStudyDetail() throws Exception {
        //given

        //when
        StudyDto ActualResult = studyService.findStudy(backendStudy.getId());

        //then
        Assertions.assertEquals(backendStudy.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("스터디의 정보를 업데이트한다.")
    public void updateStudy() throws Exception {
        //given
        StudyUpdateDto updateRequestDto = StudyFactory.makeUpdateDto("프론트엔드 스터디", List.of("프론트엔드"));

        //when
        StudyDto ActualResult = studyService.updateStudy(backendStudy.getId(), updateRequestDto);

        //then
        Assertions.assertEquals("프론트엔드 스터디", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디의 정보를 삭제한다.")
    public void deleteStudy() {
        //given

        //when
        studyService.deleteStudy(backendStudy.getId());

        //then
        Assertions.assertEquals(1, studyRepository.findAll().size());
    }
}