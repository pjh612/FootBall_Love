package com.deu.football_love.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setStringValue(String key, List<String> value, Long expTime) {
        redisTemplate.opsForList().rightPush(key, value.get(0));
        redisTemplate.opsForList().rightPush(key, value.get(1));
        redisTemplate.expire(key, expTime, TimeUnit.MILLISECONDS);
    }


    public void setStringValue(String key, Object value, Long expTime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expTime, TimeUnit.MILLISECONDS);
    }

    public String getStringValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public List<Object> getListValue(String key) {
        List<Object> ret = redisTemplate.opsForList().range(key, 0, -1);
        return ret;
    }

    public Long getExpirationTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }
}
