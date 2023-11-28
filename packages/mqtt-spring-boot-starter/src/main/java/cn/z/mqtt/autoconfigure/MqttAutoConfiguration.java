package cn.z.mqtt.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <h1>MQTT自动配置</h1>
 *
 * <p>
 * createDate 2023/08/16 14:28:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@EnableConfigurationProperties({MqttProperties.class, MqttSslProperties.class})
public class MqttAutoConfiguration {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MqttAutoConfiguration.class);

    /**
     * 构造函数(自动注入)
     *
     * @param mqttProperties    MqttProperties
     * @param mqttSslProperties MqttSslProperties
     */
    public MqttAutoConfiguration(MqttProperties mqttProperties, MqttSslProperties mqttSslProperties) {
        log.info("MQTT配置：URI {} ，连接超时时间CONNECTION_TIMEOUT {} ，保活时间KEEP_ALIVE_INTERVAL {} ，清除会话CLEAN_SESSION {} ，自动重连AUTOMATIC_RECONNECT {}",
                mqttProperties.getUri(), mqttProperties.getConnectionTimeout(), mqttProperties.getKeepAliveInterval(), mqttProperties.isCleanSession(), mqttProperties.isAutomaticReconnect());
    }

}
