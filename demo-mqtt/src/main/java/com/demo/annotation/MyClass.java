package com.demo.annotation;

import java.nio.charset.StandardCharsets;

/**
 * <h1>MyClass</h1>
 *
 * <p>
 * createDate 2023/09/15 18:12:44
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MyClass {

    private String string;

    public MyClass(byte[] bytes) {
        this.string = new String(bytes, StandardCharsets.UTF_8);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "string='" + string + '\'' +
                '}';
    }

}
