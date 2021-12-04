package project.SangHyun.domain.service;


public interface RedisService {
    String getData(String key);
    void setDataWithExpiration(String key, String value, Long time);
    void deleteData(String key);
}
