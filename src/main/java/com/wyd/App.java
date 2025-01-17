package com.wyd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wyd.core.push.PushBootstrap;

@SpringBootApplication
public class App {

    public static void main(String[] args) {

        try {
            PushBootstrap pushBootstrap = new PushBootstrap();
            pushBootstrap.pushInfoService(App.class).init();
        } catch (Exception e) {
            System.out.println("出现了预料中的异常，但不碍事~");
        }
        SpringApplication.run(App.class, args);

    }

}
