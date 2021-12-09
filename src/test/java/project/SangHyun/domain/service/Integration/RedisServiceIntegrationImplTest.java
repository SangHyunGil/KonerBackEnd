package project.SangHyun.domain.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.domain.service.Impl.RedisServiceImpl;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RedisServiceIntegrationImplTest {

    @Autowired
    RedisServiceImpl redisService;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    public void 데이터_저장_로드() throws Exception {
        //given
        String key = "key";
        String value = "value";

        //when
        redisService.setDataWithExpiration(key, value, 60 * 5L);

        //then
        Assertions.assertEquals(value, redisService.getData(key));
    }

    @Test
    public void 데이터_삭제() throws Exception {
        //given
        String key = "key";
        String value = "value";

        //when
        redisService.setDataWithExpiration(key, value, 60 * 5L);

        //then
        redisService.deleteData(key);
    }
}