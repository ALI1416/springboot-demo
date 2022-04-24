package com.demo.reflect;

import java.io.File;
import java.nio.file.Files;

/**
 * <h1>自定义类加载器</h1>
 *
 * <p>
 * createDate 2022/04/24 11:21:29
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MyClassLoader extends ClassLoader {

    /**
     * 重写findClass方法
     *
     * @param name class文件路径
     * @return Class
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] bytes = Files.readAllBytes(new File(name).toPath());
            return defineClass(null, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(name, e);
        }
    }

}
