package com.demo.config;

import com.demo.autoconfigure.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Mqtt配置</h1>
 *
 * <p>
 * createDate 2022/06/30 17:27:09
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
public class MqttConfig2 {

    /**
     * Mqtt配置属性
     */
    private final MqttProperties mqttProperties;

    /**
     * 静态注入(自动注入)
     *
     * @param mqttProperties MqttProperties
     */
    public MqttConfig2(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
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
        client.setCallback(new MqttCallback(this));
        client.connect(options);
        return client;
    }

}
