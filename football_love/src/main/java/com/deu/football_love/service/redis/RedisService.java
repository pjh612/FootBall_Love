package com.deu.football_love.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public void setStringValue(String key, List<String> value, Long expTime) {
        redisTemplate.opsForList().rightPush(key, value.get(0));
        redisTemplate.opsForList().rightPush(key, value.get(1));
        redisTemplate.expire(key, expTime, TimeUnit.MILLISECONDS);
    }


    public void setStringValue(String key, Object value, Long expTime){
        redisTemplate.opsForList().rightPush(key, value);
        redisTemplate.expire(key, expTime, TimeUnit.MILLISECONDS);
    }

    public List<Object> getListValue(String key){
        List<Object> ret = redisTemplate.opsForList().range(key, 0, -1);
        System.out.println(ret);
        return ret;
    }

    public void del(String key){
        redisTemplate.delete(key);
    }
}
