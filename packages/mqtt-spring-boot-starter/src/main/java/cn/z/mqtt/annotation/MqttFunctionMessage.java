package cn.z.mqtt.annotation;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * <h1>MQTT函数-MqttMessage</h1>
 *
 * <p>
 * createDate 2020/11/28 20:25:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@FunctionalInterface
public interface MqttFunctionMessage extends MqttFunction<MqttMessage, Object> {

    /**
     * 执行
     */
    @Override
    Object run(MqttMessage topic);

}
