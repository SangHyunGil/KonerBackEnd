package project.SangHyun.domain.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import project.SangHyun.domain.service.Impl.RedisServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class RedisServiceUnitImplTest {

    @InjectMocks
    RedisServiceImpl redisService;
    @Mock
    RedisTemplate redisTemplate;
    @Mock
    private ValueOperations valueOperations;

    @Test
    public void 데이터_로드() throws Exception {
        //given
        String key = "key";
        String value = "value";

        //mocking
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get(any())).willReturn(value);

        //when
        String ActualResult = redisService.getData(key);

        //then
        Assertions.assertEquals(value, ActualResult);
    }

    @Test
    public void 데이터_저장() throws Exception {
        //given
        String key = "key";
        String value = "value";

        //mocking
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        willDoNothing().given(valueOperations).set(any(), any(), any());

        //when, then
        redisService.setDataWithExpiration(key, value, 1L);
    }

    @Test
    public void 데이터_삭제() throws Exception {
        //given
        String key = "key";

        //mocking

        //when, then
        redisService.deleteData(key);
    }
}