package com.wyd.web.arthas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stone
 * @date 2024-12-26
 * @Description:
 */
@RestController
public class TestController {

    /**
     * 大量数据 + 处理慢
     */
    @GetMapping("/test")
    public void test1() throws InterruptedException {
        byte[] bytes = new byte[1024 * 1024 * 100];//100m
        Thread.sleep(10 * 1000L);
    }

    private final Map<String, UserEntity> userCache = new HashMap<>();

    /**
     * 登录接口 传递名字和id,放入hashmap中
     */
    @PostMapping("/login")
    public void login(String name,String id){
        userCache.put(id,new UserEntity(id,name));
    }

    private static class UserEntity {
        private final byte[] bytes = new byte[1024 * 1024 * 10];
        public UserEntity(String id, String name) {
            this.id = id;
            this.name = name;
        }

        private String id;
        private String name;


    }

}
