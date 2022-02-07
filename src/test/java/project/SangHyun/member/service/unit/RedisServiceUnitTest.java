package project.SangHyun.member.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.SangHyun.common.helper.RedisHelper;
import project.SangHyun.member.service.impl.RedisService;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class RedisServiceUnitTest {

    @InjectMocks
    RedisService redisService;
    @Mock
    RedisHelper redisHelper;

    @Test
    @DisplayName("Redis에 저장을 값을 저장한다.")
    public void store() throws Exception {
        //given
        String key = "key";
        String value = "value";
        Long time = 100000L;

        //mocking
        willDoNothing().given(redisHelper).setDataWithExpiration(key, value, time);

        //when, then
        Assertions.assertDoesNotThrow(() -> redisService.store(key, value, time));
    }

    @Test
    @DisplayName("Redis에 저장된 값과 같은 경우 True를 반환한다.")
    public void validate1() throws Exception {
        //given
        String key = "key";
        String expectValue = "value";
        String realValue = "value";

        //mocking
        given(redisHelper.getData(key)).willReturn(realValue);

        //when
        Boolean result = redisService.validate(key, expectValue);

        // then
        Assertions.assertEquals(true, result);
    }

    @Test
    @DisplayName("Redis에 저장된 값과 다른 경우 False를 반환한다.")
    public void validate2() throws Exception {
        //given
        String key = "key";
        String expectValue = "wrongValue";
        String realValue = "value";

        //mocking
        given(redisHelper.getData(key)).willReturn(realValue);

        //when
        Boolean result = redisService.validate(key, expectValue);

        // then
        Assertions.assertEquals(false, result);
    }

    @Test
    @DisplayName("Redis에 저장된 값을 삭제한다.")
    public void delete() throws Exception {
        //given
        String key = "key";

        //mocking
        willDoNothing().given(redisHelper).deleteData(key);

        //when, then
        Assertions.assertDoesNotThrow(() -> redisService.delete(key));
    }

}