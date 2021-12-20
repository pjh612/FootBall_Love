package com.deu.football_love.service.redis;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        //hashmap같은 key value 구조
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set("jdkSerial", "jdk");
        String result = (String) vop.get("jdkSerial");
        System.out.println(result);//jdk
    }

    @Test
    public void save()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("hi");
        list.add("hello");
        redisService.setStringValue("key",list, 6000L);
    }
}