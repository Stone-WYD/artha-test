package com.wyd.jvm;

/**
 * @program: arthas-test
 * @description: 拙劣的面试题，让人迷糊的父与子
 * @author: Stone
 * @create: 2024-03-14 14:55
 **/
public class FatherAndSonTest {
    static class Father {
        public int money = 1;

        public Father() {
            money = 2;
            showMeTheMoney();
        }

        public void showMeTheMoney() {
            System.out.println("I am Father, i have $" + money);
        }
    }

    static class Son extends Father {
        public int money = 3;

        public Son() {
            money = 4;
            showMeTheMoney();
        }

        @Override
        public void showMeTheMoney() {
            System.out.println("I am Son, i have $" + money);
        }
    }

    public static void main(String[] args) {
        Father guy = new Son();
        System.out.println("This guy has $" + guy.money);
    }

}

