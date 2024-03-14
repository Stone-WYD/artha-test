package com.wyd.jvm;

import java.io.InputStream;
import java.util.ServiceLoader;

/**
 * @program: arthas-test
 * @description: 类加载器测试类
 * @author: Stone
 * @create: 2024-03-13 14:10
 **/
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                try {
                    // 当前类的类加载器根据文件名获取文件输入流
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    System.out.println("匿名类加载器未能找到类资源！");
                }
                return super.loadClass(name);
            }
        };

        try {
            Class<?> loadClass = myLoader.loadClass("com.wyd.jvm.ClassLoaderTest");
            Object obj = loadClass.newInstance();
            System.out.println(obj.getClass());
            // 类加载器不同，所以会返回 false
            System.out.println(obj instanceof ClassLoaderTest);
            // Class来自不同类加载器，所以也不相等
            System.out.println(loadClass == ClassLoaderTest.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

