package com.demo.hutool.clone;

import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.clone.Cloneable;
import cn.hutool.core.util.ObjectUtil;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * <h1>克隆</h1>
 *
 * <p>
 * createDate 2022/03/08 10:26:49
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        /*泛型克隆接口*/
        Cat cat = new Cat();
        Cat cloneCat = cat.clone();
        cat.name = "miao";
        cat.age = 22;
        log.info("泛型克隆接口:" + cat);
        log.info("泛型克隆接口:" + cloneCat);
        /*泛型克隆类*/
        Dog dog = new Dog();
        Dog cloneDog = dog.clone();
        dog.name = "wang";
        dog.age = 33;
        log.info("泛型克隆类:" + dog);
        log.info("泛型克隆类:" + cloneDog);
        /*深克隆*/
        Pig pig = new Pig();
        Pig clonePig = ObjectUtil.cloneByStream(pig);
        pig.name = "heng";
        pig.age = 44;
        log.info("深克隆:" + pig);
        log.info("深克隆:" + clonePig);
    }

    /**
     * 实现Cloneable
     */
    @ToString
    private static class Cat implements Cloneable<Cat> {

        private String name = "miaomiao";
        private int age = 2;

        @Override
        public Cat clone() {
            try {
                return (Cat) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new CloneRuntimeException(e);
            }
        }

    }

    /**
     * 继承CloneSupport
     */
    @ToString
    private static class Dog extends CloneSupport<Dog> {

        private String name = "wangwang";
        private int age = 3;

    }

    /**
     * 实现Serializable
     */
    @ToString
    private static class Pig implements Serializable {

        private String name = "hengheng";
        private int age = 4;

    }

}
