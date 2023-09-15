package com.demo.tool;

import com.demo.config.MqttConfig2;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * <h1>Mqtt模板</h1>
 *
 * <p>
 * createDate 2023/09/15 13:56:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
public class MqttTemp2 {

    private static final Logger log = LoggerFactory.getLogger(MqttTemp2.class);
    /**
     * MQTT配置
     */
    private final MqttConfig2 mqttConfig2;

    public MqttTemp2(MqttConfig2 mqttConfig2) {
        this.mqttConfig2 = mqttConfig2;
    }

    /**
     * 订阅
     *
     * @param topic    主题
     * @param qos      QoS
     * @param callback 回调
     */
    public void subscribe(String topic, int qos, IMqttMessageListener callback) {
        subscribe(new String[]{topic}, new int[]{qos}, new IMqttMessageListener[]{callback});
    }

    /**
     * 订阅
     *
     * @param topicArray    主题数组
     * @param qosArray      QoS数组
     * @param callbackArray 回调数组
     */
    public void subscribe(String[] topicArray, int[] qosArray, IMqttMessageListener[] callbackArray) {
        try {
            mqttConfig2.client().subscribeWithResponse(topicArray, qosArray, callbackArray);
        } catch (MqttException e) {
            log.error("订阅失败", e);
        }
    }

    /**
     * 发送(QoS=0 不保留)
     *
     * @param topic 主题
     * @param msg   消息
     */
    public void send(String topic, String msg) {
        send(topic, msg.getBytes(StandardCharsets.UTF_8), 0, false);
    }

    /**
     * 发送(不保留)
     *
     * @param topic 主题
     * @param msg   消息
     * @param qos   QoS
     */
    public void send(String topic, String msg, int qos) {
        send(topic, msg.getBytes(StandardCharsets.UTF_8), qos, false);
    }

    /**
     * 发送
     *
     * @param topic  主题
     * @param msg    消息
     * @param qos    QoS
     * @param retain 保留
     */
    public void send(String topic, String msg, int qos, boolean retain) {
        send(topic, msg.getBytes(StandardCharsets.UTF_8), qos, retain);
    }

    /**
     * 发送
     *
     * @param topic  主题
     * @param msg    消息
     * @param qos    QoS
     * @param retain 保留
     */
    public void send(String topic, byte[] msg, int qos, boolean retain) {
        try {
            mqttConfig2.client().publish(topic, msg, qos, retain);
        } catch (MqttException e) {
            log.error("发送失败", e);
        }
    }

}
