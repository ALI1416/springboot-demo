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
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    /**
     * URI(默认值"tcp://127.0.0.1:1883")<br>
     * (ssl例如"ssl://127.0.0.1:8883")
     */
    private String uri = "tcp://127.0.0.1:1883";
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
    private int connectionTimeout = 30;
    /**
     * 保活时间(秒)(默认值60)
     */
    private int keepAliveInterval = 60;
    /**
     * 清除会话(默认值true)
     */
    private boolean cleanSession = true;
    /**
     * 自动重连(默认值false)
     */
    private boolean automaticReconnect = false;

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

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(int keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public boolean isAutomaticReconnect() {
        return automaticReconnect;
    }

    public void setAutomaticReconnect(boolean automaticReconnect) {
        this.automaticReconnect = automaticReconnect;
    }

}
