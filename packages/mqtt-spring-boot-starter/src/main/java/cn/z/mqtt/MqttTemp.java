package cn.z.mqtt;

import cn.z.mqtt.config.MqttConfig;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
@Component
public class MqttTemp {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MqttTemp.class);

    /**
     * MQTT配置
     */
    private final MqttConfig mqttConfig;

    /**
     * 构造函数(自动注入)
     *
     * @param mqttConfig MqttConfig
     */
    public MqttTemp(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
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
            mqttConfig.client().publish(topic, msg, qos, retain);
        } catch (MqttException e) {
            log.error("发送失败", e);
        }
    }

}