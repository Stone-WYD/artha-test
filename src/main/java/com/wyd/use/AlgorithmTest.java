package com.wyd.use;

import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * @program: sword-to-offer-test
 * @description: 整数除法，题目内容：输入2个 int 型整数，它们进行除法运算并返回商。当发生溢出时，返回最大的整数值。不得使用 ‘*’、‘/’、‘%’。该题目位于书中第 1 页
 * @author: Stone
 * @create: 2023-08-17 11:38
 **/
public class AlgorithmTest {

    public Thread test(boolean auto) {
        Thread resultThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                if (!auto) System.out.print("输入的被除数为：");
                int m = auto ? 10 : scanner.nextInt();
                if (!auto) System.out.print("输入的除数为：");
                int i = auto ? 2 : scanner.nextInt();
                int result = divide(m, i);
                System.out.println("得到的结果为：" + result);

                if (!auto) {
                    System.out.print("此时输入 'exit' 可结束程序：");
                    String e = scanner.next();
                    if ("exit".equals(e)) break;
                }
                try {
                    System.out.println("算法线程：睡眠一会");
                    sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("算法线程报错：" + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });
        resultThread.start();
        return resultThread;
    }


    public static int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            // 唯一可能会溢出的情况，返回最大值
            return Integer.MAX_VALUE;
        }
        if (dividend == Integer.MIN_VALUE || divisor == Integer.MIN_VALUE) {
            // 这种情况不计算
            return 404;
        }
        if (dividend == 0) return 0;
        // 结果是否取反的标志
        int negative = 2;
        if (dividend < 0) {
            negative--;
            dividend = -dividend;
        }
        if (divisor < 0) {
            negative--;
            divisor = -divisor;
        }
        int result = divideCore(dividend, divisor);
        return negative == 1 ? -result : result;
    }

    private static int divideCore(int dividend, int divisor) {
        // 计算时会把 dividend 和 divisor 都当作正数
        int result = 0;
        while (dividend >= divisor) {
            int quotient = 1;
            int value = divisor;
            while (value < Integer.MAX_VALUE && dividend >= value + value) {
                value += value;
                quotient += quotient;
            }
            dividend -= value;
            result += quotient;
        }
        return result;
    }

    public static int divide2(int dividend, int divisor) {
        // 这种情况会溢出，按照题意返回最大整数值
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        int reverFlag = 0;
        if (dividend > 0) {
            dividend = -dividend;
            reverFlag++;
        }
        if (divisor > 0) {
            divisor = -divisor;
            reverFlag++;
        }
        int result = divideCore2(dividend, divisor);
        return (reverFlag & 1) == 0 ? result : -result;
    }

    private static int divideCore2(int dividend, int divisor) {
        int result = 0;
        while (dividend < divisor) {
            int tempResult = 1;
            int value = divisor;
            while (dividend < value + value && value > Integer.MIN_VALUE) {
                value += value;
                tempResult += tempResult;
            }
            dividend -= value;
            result += tempResult;
        }
        return result;
    }

}
