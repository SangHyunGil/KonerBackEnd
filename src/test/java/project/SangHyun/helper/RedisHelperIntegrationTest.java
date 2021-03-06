package project.SangHyun.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.RedisValueDifferentException;

class RedisHelperIntegrationTest extends HelperTest{

    @Test
    @DisplayName("Redis에 저장을 값을 저장한다.")
    public void store() throws Exception {
        //given
        String key = "key";
        String value = "value";
        Long time = 100000L;

        //when, then
        Assertions.assertDoesNotThrow(() -> redisHelper.store(key, value, time));
    }

    @Test
    @DisplayName("Redis에 저장된 값과 같은 경우 예외를 발생시키지 않는다.")
    public void validate1() throws Exception {
        //given
        String key = "key";
        String value = "value";
        String expectValue = "value";
        Long time = 500000000L;
        redisHelper.store(key, value, time);

        //when, then
        Assertions.assertDoesNotThrow(() -> redisHelper.validate(key, expectValue));
    }

    @Test
    @DisplayName("Redis에 저장된 값과 다른 경우 예외를 반환한다.")
    public void validate2() throws Exception {
        //given
        String key = "key";
        String value = "value";
        String expectValue = "wrongValue";
        Long time = 5000000L;
        redisHelper.store(key, value, time);

        //when, then
        Assertions.assertThrows(RedisValueDifferentException.class, () -> redisHelper.validate(key, expectValue));
    }

    @Test
    @DisplayName("Redis에 저장된 값을 삭제한다.")
    public void delete() throws Exception {
        //given
        String key = "key";
        String value = "value";
        Long time = 5000000L;
        redisHelper.store(key, value, time);

        //when, then
        Assertions.assertDoesNotThrow(() -> redisHelper.delete(key));
    }
}