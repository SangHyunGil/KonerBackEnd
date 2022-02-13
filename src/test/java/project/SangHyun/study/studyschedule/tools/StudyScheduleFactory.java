package project.SangHyun.study.studyschedule.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleCreateDto;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleUpdateDto;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;

public class StudyScheduleFactory extends BasicFactory {

    // Request
    public static StudyScheduleCreateDto makeCreateDto() {
        return new StudyScheduleCreateDto("백엔드 스터디 일정", "2021-12-15", "2022-03-01", "18:00", "22:00");
    }

    public static StudyScheduleUpdateDto makeUpdateDto(String title) {
        return new StudyScheduleUpdateDto(title, "2021-12-15", "2022-03-01", "18:00", "22:00");
    }

    // Response
    public static StudyScheduleDto makeDto(StudySchedule schedule) {
        return StudyScheduleDto.create(schedule);
    }
}
