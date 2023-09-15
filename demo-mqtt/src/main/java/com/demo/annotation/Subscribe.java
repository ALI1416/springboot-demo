package com.demo.annotation;

import java.lang.annotation.*;

/**
 * <h1>MQTT订阅</h1>
 *
 * <p>
 *
 * </p>
 *
 * <p>
 * createDate 2023/09/14 18:05:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Subscribe {

    /**
     * 主题
     */
    String value();

    /**
     * QoS(默认0)
     */
    int qos() default 0;

}
