package cn.z.mqtt.annotation;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.function.Function;

/**
 * <h1>消息函数</h1>
 *
 * <p>
 * createDate 2020/11/28 20:25:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@FunctionalInterface
public interface FunctionMessage extends Function<MqttMessage, Object> {

    /**
     * 应用
     *
     * @param topic MqttMessage
     * @return Object
     */
    @Override
    Object apply(MqttMessage topic);

}
