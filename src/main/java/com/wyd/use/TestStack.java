package com.wyd.use;

/**
 * @program: arthas-test
 * @description: 栈测试
 * @author: Stone
 * @create: 2024-03-12 15:13
 **/
public class TestStack {

    public Thread testStack() {
        // 栈空间随着时间推移不断加深
        Thread thread = new Thread(this::doTestStack1);
        thread.start();
        return thread;
    }

    private void doTestStack1() {
        try {
            // 避免太快就充满栈
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TestStack testStack = new TestStack();
        testStack.testStack();
    }
}

