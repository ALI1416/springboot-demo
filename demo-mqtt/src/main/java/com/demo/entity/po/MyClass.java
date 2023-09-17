package com.demo.entity.po;

import com.alibaba.fastjson2.JSON;

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

    public MyClass() {
    }

    public MyClass(byte[] bytes) {
        MyClass myClass = JSON.parseObject(bytes, MyClass.class);
        this.string = myClass.getString();
    }

    public MyClass(String string) {
        MyClass myClass = JSON.parseObject(string, MyClass.class);
        this.string = myClass.getString();
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
