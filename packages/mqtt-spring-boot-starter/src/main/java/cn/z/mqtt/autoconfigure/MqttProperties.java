package cn.z.mqtt.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <h1>MQTT配置属性</h1>
 *
 * <p>
 * createDate 2023/08/16 14:28:35
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@ConfigurationProperties(prefix = MqttProperties.MQTT_PREFIX)
public class MqttProperties {

    /**
     * 前缀{@value}
     */
    public static final String MQTT_PREFIX = "mqtt";

    /**
     * URI(默认值"tcp://127.0.0.1:1883")
     */
    private String uri;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 连接超时时间(秒)(默认值30)
     */
    private Integer connectionTimeout;
    /**
     * 保活时间(秒)(默认值60)
     */
    private Integer keepAliveInterval;
    /**
     * 清除会话(默认值true)
     */
    private Boolean cleanSession;
    /**
     * 自动重连(默认值false)
     */
    private Boolean automaticReconnect;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(Integer keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public Boolean getCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(Boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public Boolean getAutomaticReconnect() {
        return automaticReconnect;
    }

    public void setAutomaticReconnect(Boolean automaticReconnect) {
        this.automaticReconnect = automaticReconnect;
    }

}
