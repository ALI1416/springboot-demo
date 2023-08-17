package com.demo.config;

import com.demo.autoconfigure.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

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
public class MqttConfig {

    /**
     * Mqtt配置属性
     */
    private final MqttProperties mqttProperties;

    /**
     * 静态注入(自动注入)
     *
     * @param mqttProperties MqttProperties
     */
    public MqttConfig(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    /**
     * MqttPahoClientFactory
     */
    @Bean
    public MqttPahoClientFactory factory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttProperties.getUri());
        options.setUserName(mqttProperties.getUsername());
        if (mqttProperties.getPassword() != null) {
            options.setPassword(mqttProperties.getPassword().toCharArray());
        }
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        options.setCleanSession(mqttProperties.getCleanSession());
        options.setAutomaticReconnect(mqttProperties.getAutomaticReconnect());
        factory.setConnectionOptions(options);
        return factory;
    }

}
