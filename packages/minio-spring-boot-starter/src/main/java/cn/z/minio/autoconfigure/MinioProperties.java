package cn.z.minio.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <h1>Minio配置属性</h1>
 *
 * <p>
 * createDate 2023/08/31 14:28:35
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * URI(默认值"http://127.0.0.1:9000")
     */
    private String uri = "http://127.0.0.1:9000";
    /**
     * 用户名(默认值"minioadmin")
     */
    private String username = "minioadmin";
    /**
     * 密码(默认值"minioadmin")
     */
    private String password = "minioadmin";
    /**
     * 区域
     */
    private String region;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
