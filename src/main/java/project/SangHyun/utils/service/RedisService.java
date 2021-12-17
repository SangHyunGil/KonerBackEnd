package project.SangHyun.utils.service;

public interface RedisService {
    String getData(String key);
    void setDataWithExpiration(String key, String value, Long time);
    void deleteData(String key);
}
