package com.sadalsuud.push.infrastructure.repository;

import com.sadalsuud.push.DoPushApplication;
import com.sadalsuud.push.domain.support.cache.CacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
@SpringBootTest(classes = DoPushApplication.class)
@RunWith(SpringRunner.class)
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Test
    public void getUserData() {
        System.out.println("=========================");
        List<String> strings = cacheService.lRange("2094176918@qq.com", 0, -1);
        strings.forEach(System.out::println);
    }

    @Test
    public void putAndGetTest() {
        cacheService.lPush("2094176918@qq.com", "123", 20000L);
        for (String s : cacheService.lRange("2094176918@qq.com", 0, -1)) {
            System.out.println(s);
        }

    }
}
