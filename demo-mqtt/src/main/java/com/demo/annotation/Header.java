package com.demo.annotation;

import java.lang.annotation.*;

/**
 * <h1>MQTT头部</h1>
 *
 * <p>
 * createDate 2023/09/15 10:56:25
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Header {

    /**
     * 类型(默认{@link HeaderEnum#MSG})
     */
    HeaderEnum value() default HeaderEnum.MSG;

    /**
     * 主题片段匹配位置(默认0，仅对{@link HeaderEnum#TOPIC_PART}有效)
     */
    int index() default 0;

}
