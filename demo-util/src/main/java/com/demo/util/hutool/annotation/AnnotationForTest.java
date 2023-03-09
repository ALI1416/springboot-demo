package com.demo.util.hutool.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>AnnotationForTest</h1>
 *
 * <p>
 * createDate 2022/03/10 10:54:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AnnotationForTest {

    /**
     * 注解的默认属性值
     *
     * @return 属性值
     */
    String value();

}
