package project.SangHyun.study.study.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyProfileImgUrlTest {

    @Test
    @DisplayName("프로필 이미지가 존재하면 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new StudyProfileImgUrl("h".repeat(5)));
    }

    @Test
    @DisplayName("프로필 이미지가 존재하지 않으면 랜덤한 이미지가 선택된다.")
    public void test2() throws Exception {
        //given

        //when
        StudyProfileImgUrl studyProfileImgUrl = new StudyProfileImgUrl(null);

        //then
        Assertions.assertEquals(true, studyProfileImgUrl.getProfileImgUrl().contains("https://koner-bucket.s3.ap-northeast-2.amazonaws.com/logo/KakaoTalk_20220128_143615435.png"));
    }
}