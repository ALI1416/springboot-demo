package cn.z.elasticsearch.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <h1>ElasticSearch配置属性</h1>
 *
 * <p>
 * createDate 2023/09/04 14:28:35
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {

    /**
     * URI(默认值"http://127.0.0.1:9200")
     */
    private String uri = "http://127.0.0.1:9200";
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

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

}
