package com.demo.config;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * <h1>MQTT回调</h1>
 *
 * <p>
 * createDate 2023/09/14 15:14:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MqttCallback implements MqttCallbackExtended {

    private static final Logger log = LoggerFactory.getLogger(MqttCallback.class);
    private final MqttConfig2 mqttConfig2;

    public static final String[] topicArray = new String[]{"#"};
    public static final int[] qosArray = new int[]{0};
    public static final IMqttMessageListener[] callbackArray = new IMqttMessageListener[]{
            (topic, message) -> log.info("topic:{},id:{},qos:{},duplicate:{},retained:{},msg:{}", topic, message.getId(), message.getQos(), message.isDuplicate(), message.isRetained(), message)
    };

    /**
     * 构造函数
     *
     * @param mqttConfig2 MqttConfig
     */
    public MqttCallback(MqttConfig2 mqttConfig2) {
        this.mqttConfig2 = mqttConfig2;
    }

    /**
     * 连接成功
     *
     * @param reconnect 重连
     * @param uri       URI
     */
    @Override
    public void connectComplete(boolean reconnect, String uri) {
        if (reconnect) {
            log.info("MQTT重连成功");
        } else {
            log.info("MQTT连接成功");
        }
        subscribe(topicArray, qosArray, callbackArray);
    }

    /**
     * 失去连接
     *
     * @param cause Throwable
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.error("MQTT失去连接", cause);
    }

    /**
     * 消息到达
     *
     * @param topic   主题
     * @param message MqttMessage
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
    }

    /**
     * 消息发送成功
     *
     * @param token IMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
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
