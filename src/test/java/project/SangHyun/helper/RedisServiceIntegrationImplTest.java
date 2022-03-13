//package project.SangHyun.helper;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//import project.SangHyun.TestDB;
//import project.SangHyun.member.helper.RedisHelper;
//
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test")
//class RedisServiceIntegrationImplTest {
//
//    @Autowired
//    RedisHelper redisHelper;
//    @Autowired
//    TestDB testDB;
//
//    @BeforeEach
//    void beforeEach() {
//        testDB.init();
//    }
//
//    @Test
//    @DisplayName("Redis에 데이터를 저장한다.")
//    public void save() throws Exception {
//        //given
//        String key = "key";
//        String value = "value";
//
//        //when
//        redisHelper.store(key, value, 60 * 5L);
//
//        //then
//        Assertions.assertEquals(value, redisService.getData(key));
//    }
//
//    @Test
//    @DisplayName("Redis에 데이터를 로드한다.")
//    public void load() throws Exception {
//        //given
//        String key = "key";
//        String value = "value";
//
//        //when
//        redisHelper.store(key, value, 60 * 5L);
//
//        //then
//        Assertions.assertEquals(value, redisHelper.validate(key));
//    }
//
//    @Test
//    @DisplayName("Redis에 데이터를 삭제한다.")
//    public void delete() throws Exception {
//        //given
//        String key = "key";
//        String value = "value";
//
//        //when
//        redisHelper.store(key, value, 60 * 5L);
//
//        //then
//        redisHelper.delete(key);
//    }
//}