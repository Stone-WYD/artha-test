package com.wyd.use;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * @program: arthas-test
 * @description: 堆测试
 * @author: Stone
 * @create: 2024-03-01 16:02
 **/
public class TestHeap {

    private List<String> content;

    public void setContent(List<String> content) {
        this.content = content;
    }

    public Thread testHeap(boolean auto) {
        Thread resultThread = new Thread(() -> {
            // 测试一次会创建 1000 个实例
            while (auto) {
                for (int i = 0; i < 10; i++) {
                    doTestHeap1(100);
                }
            }
        });
        resultThread.start();
        return resultThread;
    }

    private void doTestHeap1(int times) {
        // 不断创建新的实例，填充堆，触发gc
        for (int i = 0; i < times; i++) {
            TestHeap testHeap = new TestHeap();
            List<String> content = new ArrayList<>();
            content.add("111111");
            testHeap.setContent(content);
            System.out.println("创建了一个实例" + i + "..." );
            try {
                System.out.println("堆测试线程：睡眠一会");
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

