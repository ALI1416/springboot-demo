package com.demo.hutool.annotation;

import cn.hutool.core.annotation.AnnotationUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * <h1>注解</h1>
 *
 * <p>
 * createDate 2022/03/10 10:54:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("获取指定注解默认值:" + AnnotationUtil.getAnnotationValue(ClassWithAnnotation.class, AnnotationForTest.class));
        log.info("获取注解类的保留时间:" + AnnotationUtil.getRetentionPolicy(AnnotationForTest.class));
        log.info("获取注解类可以用来修饰哪些程序元素:" + Arrays.toString(AnnotationUtil.getTargetType(AnnotationForTest.class)));
    }

}
