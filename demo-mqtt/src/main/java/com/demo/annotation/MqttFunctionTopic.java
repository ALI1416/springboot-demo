package com.demo.annotation;

/**
 * <h1>自定义函数</h1>
 *
 * <p>
 * createDate 2020/11/28 20:25:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@FunctionalInterface
public interface MqttFunctionTopic extends MqttFunction<String, Object> {

    /**
     * 执行
     */
    @Override
    Object run(String topic);

}
