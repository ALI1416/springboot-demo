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
        String msg = "MQTT配置：URI " + mqttProperties.getUri() +
                " ，连接超时时间CONNECTION_TIMEOUT " + mqttProperties.getConnectionTimeout() +
                " ，保活时间KEEP_ALIVE_INTERVAL " + mqttProperties.getKeepAliveInterval() +
                " ，清除会话CLEAN_SESSION " + mqttProperties.isCleanSession() +
                " ，自动重连AUTOMATIC_RECONNECT " + mqttProperties.isAutomaticReconnect() +
                " ，使用账号密码访问 " + (mqttProperties.getUsername() != null && !mqttProperties.getUsername().isEmpty() && mqttProperties.getPassword() != null && !mqttProperties.getPassword().isEmpty());
        String sslMsg = "MQTT SSL配置：启用ENABLE " + mqttSslProperties.isEnable();
        if (mqttSslProperties.isEnable()) {
            sslMsg += " ，校验证书CHECK " + mqttSslProperties.isCheck() +
                    " ，CA服务器自动签名CA_SERVER_AUTO_SIGN " + mqttSslProperties.isCaServerAutoSign();
            if (!mqttSslProperties.isCaServerAutoSign()) {
                if (mqttSslProperties.getCaCrtResourcePath() != null) {
                    sslMsg += " ，CA证书资源路径CA_CRT_RESOURCE_PATH " + mqttSslProperties.getCaCrtResourcePath();
                } else if (mqttSslProperties.getCaCrtLocalPath() != null) {
                    sslMsg += " ，CA证书本地路径CA_CRT_LOCAL_PATH " + mqttSslProperties.getCaCrtLocalPath();
                }
                if (mqttSslProperties.getClientCrtResourcePath() != null) {
                    sslMsg += " ，客户端证书资源路径CLIENT_CRT_RESOURCE_PATH " + mqttSslProperties.getClientCrtResourcePath();
                } else if (mqttSslProperties.getClientCrtLocalPath() != null) {
                    sslMsg += " ，客户端证书本地路径CLIENT_CRT_LOCAL_PATH " + mqttSslProperties.getClientCrtLocalPath();
                }
                if (mqttSslProperties.getClientKeyResourcePath() != null) {
                    sslMsg += " ，客户端密钥资源路径CLIENT_KEY_RESOURCE_PATH " + mqttSslProperties.getClientKeyResourcePath();
                } else if (mqttSslProperties.getClientKeyLocalPath() != null) {
                    sslMsg += " ，客户端密钥本地路径CLIENT_KEY_LOCAL_PATH " + mqttSslProperties.getClientKeyLocalPath();
                }
            }
        }
        log.info(msg);
        log.info(sslMsg);
    }

}
