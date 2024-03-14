package com.wyd;

import com.wyd.use.AlgorithmTest;
import com.wyd.use.TestHeap;

public class Main {
    public static void main(String[] args) {
        Thread t1 = new AlgorithmTest().test(true);
        Thread t2 = new TestHeap().testHeap(true);
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}