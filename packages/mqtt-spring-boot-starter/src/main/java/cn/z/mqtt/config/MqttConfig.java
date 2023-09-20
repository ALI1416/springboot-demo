package cn.z.mqtt.config;

import cn.z.mqtt.annotation.MqttStorage;
import cn.z.mqtt.autoconfigure.MqttProperties;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>MQTT配置</h1>
 *
 * <p>
 * createDate 2022/06/30 17:27:09
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
public class MqttConfig {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MqttConfig.class);

    /**
     * MQTT配置属性
     */
    private final MqttProperties mqttProperties;
    /**
     * MQTT存储
     */
    private final MqttStorage mqttStorage;

    /**
     * 构造函数(自动注入)
     *
     * @param mqttProperties MqttProperties
     * @param mqttStorage    MqttStorage
     */
    public MqttConfig(MqttProperties mqttProperties, MqttStorage mqttStorage) {
        this.mqttProperties = mqttProperties;
        this.mqttStorage = mqttStorage;
    }

    /**
     * MqttClient
     */
    @Bean
    public MqttClient client() throws MqttException {
        MqttClient client = new MqttClient(mqttProperties.getUri(), "mqtt_" + System.currentTimeMillis(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        if (mqttProperties.getUsername() != null && mqttProperties.getPassword() != null) {
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
        }
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        options.setCleanSession(mqttProperties.getCleanSession());
        options.setAutomaticReconnect(mqttProperties.getAutomaticReconnect());
        client.setCallback(new MqttCallbackExtended() {

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
                try {
                    client.subscribeWithResponse(mqttStorage.getTopicArray(), mqttStorage.getQosArray(), mqttStorage.getCallbackArray());
                } catch (MqttException e) {
                    log.error("订阅失败", e);
                }
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

        });
        client.connect(options);
        return client;
    }

}
