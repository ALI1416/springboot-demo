package com.demo.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * <h1>Mqtt自动配置</h1>
 *
 * <p>
 * createDate 2023/08/16 14:28:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttAutoConfiguration {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MqttAutoConfiguration.class);

    /**
     * URI默认值{@value}
     */
    private static final String DEFAULT_URI = "tcp://127.0.0.1:1883";
    /**
     * 连接超时时间默认值{@value}秒
     */
    private static final Integer DEFAULT_CONNECTION_TIMEOUT = 30;
    /**
     * 保活时间默认值{@value}秒
     */
    private static final Integer DEFAULT_KEEP_ALIVE_INTERVAL = 60;
    /**
     * 清除会话默认值{@value}
     */
    private static final Boolean DEFAULT_CLEAN_SESSION = true;
    /**
     * 自动重连默认值{@value}
     */
    private static final Boolean DEFAULT_AUTOMATIC_RECONNECT = false;

    /**
     * MqttProperties
     */
    private final MqttProperties mqttProperties;

    /**
     * 构造函数(自动注入)
     *
     * @param mqttProperties MqttProperties
     */
    public MqttAutoConfiguration(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        String msg = "MQTT配置：URI ";
        if (mqttProperties.getUri() == null) {
            mqttProperties.setUri(new String[]{DEFAULT_URI});
            msg += "[" + DEFAULT_URI + "] (默认)";
        } else {
            msg += Arrays.toString(mqttProperties.getUri()) + " ";
        }
        if (mqttProperties.getUsername() != null) {
            msg += "用户名USERNAME " + mqttProperties.getUsername() + " ";
        }
        if (mqttProperties.getPassword() != null) {
            msg += "密码PASSWORD " + mqttProperties.getPassword() + " ";
        }
        msg += "，连接超时时间CONNECTION_TIMEOUT ";
        if (mqttProperties.getConnectionTimeout() == null) {
            mqttProperties.setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
            msg += DEFAULT_CONNECTION_TIMEOUT + " (秒)(默认)";
        } else {
            msg += mqttProperties.getConnectionTimeout() + " (秒)";
        }
        msg += "，保活时间KEEP_ALIVE_INTERVAL ";
        if (mqttProperties.getKeepAliveInterval() == null) {
            mqttProperties.setKeepAliveInterval(DEFAULT_KEEP_ALIVE_INTERVAL);
            msg += DEFAULT_KEEP_ALIVE_INTERVAL + " (秒)(默认)";
        } else {
            msg += mqttProperties.getKeepAliveInterval() + " (秒)";
        }
        msg += "，清除会话CLEAN_SESSION ";
        if (mqttProperties.getCleanSession() == null) {
            mqttProperties.setCleanSession(DEFAULT_CLEAN_SESSION);
            msg += DEFAULT_CLEAN_SESSION + " (默认)";
        } else {
            msg += mqttProperties.getCleanSession() + " ";
        }
        msg += "，自动重连AUTOMATIC_RECONNECT ";
        if (mqttProperties.getAutomaticReconnect() == null) {
            mqttProperties.setAutomaticReconnect(DEFAULT_AUTOMATIC_RECONNECT);
            msg += DEFAULT_AUTOMATIC_RECONNECT + " (默认)";
        } else {
            msg += mqttProperties.getAutomaticReconnect();
        }
        log.info(msg);
    }

}
