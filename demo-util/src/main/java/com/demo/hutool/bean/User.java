package com.demo.hutool.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * <h1>User</h1>
 *
 * <p>
 * createDate 2022/03/09 13:51:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@ToString
public class User {

    private String name;
    private int age;
    private List<String> list;
    private Map<String, String> map;
    private School school;

    public String testMethod() {
        return "test for " + this.name;
    }

    @Getter
    @Setter
    @ToString
    public static class School {
        private String name;
        private String address;
    }

}
