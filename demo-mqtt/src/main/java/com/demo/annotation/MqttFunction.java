package com.demo.annotation;

/**
 * <h1>MQTT函数</h1>
 *
 * <p>
 * createDate 2020/11/28 20:25:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface MqttFunction<K, V> {

    /**
     * 执行
     */
    V run(K object);

}
