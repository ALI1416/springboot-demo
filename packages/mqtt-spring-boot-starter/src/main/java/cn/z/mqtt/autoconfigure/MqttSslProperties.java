package cn.z.mqtt.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <h1>MQTT SSL配置属性</h1>
 *
 * <p>
 * createDate 2023/11/28 17:32:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@ConfigurationProperties(prefix = "mqtt.ssl")
public class MqttSslProperties {

    /**
     * 启用(默认值false)
     */
    private boolean enable = false;
    /**
     * 校验证书(默认值false)
     */
    private boolean check = false;
    /**
     * CA服务器自动签名(默认值true)
     */
    private boolean caServerAutoSign = true;
    /**
     * CA证书(资源路径)(优先级0)<br>
     * 读取项目文件夹resources下的路径
     */
    private String caCrtResourcePath;
    /**
     * CA证书(本地路径)(优先级1)<br>
     * 读取本地物理路径
     */
    private String caCrtLocalPath;
    /**
     * 客户端证书(资源路径)(优先级0)<br>
     * 读取项目文件夹resources下的路径
     */
    private String clientCrtResourcePath;
    /**
     * 客户端证书(本地路径)(优先级1)<br>
     * 读取本地物理路径
     */
    private String clientCrtLocalPath;
    /**
     * 客户端密钥(资源路径)(优先级0)<br>
     * 读取项目文件夹resources下的路径
     */
    private String clientKeyResourcePath;
    /**
     * 客户端密钥(本地路径)(优先级1)<br>
     * 读取本地物理路径
     */
    private String clientKeyLocalPath;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCaServerAutoSign() {
        return caServerAutoSign;
    }

    public void setCaServerAutoSign(boolean caServerAutoSign) {
        this.caServerAutoSign = caServerAutoSign;
    }

    public String getCaCrtResourcePath() {
        return caCrtResourcePath;
    }

    public void setCaCrtResourcePath(String caCrtResourcePath) {
        this.caCrtResourcePath = caCrtResourcePath;
    }

    public String getCaCrtLocalPath() {
        return caCrtLocalPath;
    }

    public void setCaCrtLocalPath(String caCrtLocalPath) {
        this.caCrtLocalPath = caCrtLocalPath;
    }

    public String getClientCrtResourcePath() {
        return clientCrtResourcePath;
    }

    public void setClientCrtResourcePath(String clientCrtResourcePath) {
        this.clientCrtResourcePath = clientCrtResourcePath;
    }

    public String getClientCrtLocalPath() {
        return clientCrtLocalPath;
    }

    public void setClientCrtLocalPath(String clientCrtLocalPath) {
        this.clientCrtLocalPath = clientCrtLocalPath;
    }

    public String getClientKeyResourcePath() {
        return clientKeyResourcePath;
    }

    public void setClientKeyResourcePath(String clientKeyResourcePath) {
        this.clientKeyResourcePath = clientKeyResourcePath;
    }

    public String getClientKeyLocalPath() {
        return clientKeyLocalPath;
    }

    public void setClientKeyLocalPath(String clientKeyLocalPath) {
        this.clientKeyLocalPath = clientKeyLocalPath;
    }

}
