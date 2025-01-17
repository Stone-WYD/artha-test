package com.wyd.web.arthas;

import com.wyd.web.common.AjaxResult;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stone
 * @date 2024-12-26
 * @Description: 模拟测试因高并发而引起的内存溢出问题
 */
@RestController
@RequestMapping("/arthas/leak")
public class TestController {

    /**
     * 大量数据 + 处理慢
     */
    @GetMapping("/test")
    public void test1() throws InterruptedException {
        byte[] bytes = new byte[1024 * 1024 * 100];//100m
        Thread.sleep(10 * 1000L);
    }

    private final Map<Long, UserEntity> userCache = new HashMap<>();

    /**
     * 登录接口 传递名字和id,放入hashmap中
     */
    @PostMapping("/login")
    public void login(String name,Long id){
        userCache.put(id,new UserEntity(id,name));
    }

    @PostMapping("/feign")
    public AjaxResult<UserEntity> feignTest(String name, Long id){
        UserEntity data = new UserEntity(id, name);
        System.out.println("====================== feign 被调用了 ===========================");
        return AjaxResult.getTrueAjaxResult(data);
    }

    @Data
    public static class UserEntity {
        public UserEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        private Long id;
        private String name;


    }

}
