package cn.z.mqtt;

import cn.z.mqtt.annotation.MqttAnnotationProcessor;

import java.nio.charset.StandardCharsets;

/**
 * <h1>MQTT模板</h1>
 *
 * <p>
 * createDate 2023/09/15 13:56:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MqttTemp {

    /**
     * MQTT注解处理
     */
    private final MqttAnnotationProcessor mqttAnnotationProcessor;

    /**
     * 构造函数(自动注入)
     *
     * @param mqttAnnotationProcessor MqttAnnotationProcessor
     */
    public MqttTemp(MqttAnnotationProcessor mqttAnnotationProcessor) {
        this.mqttAnnotationProcessor = mqttAnnotationProcessor;
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
     * 发送(QoS=0 不保留)
     *
     * @param topic 主题
     * @param msg   消息
     */
    public void send(String topic, byte[] msg) {
        send(topic, msg, 0, false);
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
     * 发送(不保留)
     *
     * @param topic 主题
     * @param msg   消息
     * @param qos   QoS
     */
    public void send(String topic, byte[] msg, int qos) {
        send(topic, msg, qos, false);
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
            mqttAnnotationProcessor.getMqttClient().publish(topic, msg, qos, retain);
        } catch (Exception e) {
            throw new MqttException(e);
        }
    }

}
