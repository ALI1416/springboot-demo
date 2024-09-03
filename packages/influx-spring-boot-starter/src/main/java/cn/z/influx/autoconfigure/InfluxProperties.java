package cn.z.influx.autoconfigure;

import com.influxdb.LogLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * <h1>InfluxDB配置属性</h1>
 *
 * <p>
 * createDate 2024/09/03 15:17:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@ConfigurationProperties(prefix = "influx")
public class InfluxProperties {

    /**
     * URL(默认值"http://localhost:8086")
     */
    private String url = "http://localhost:8086";
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * token
     */
    private String token;
    /**
     * 写入和查询的默认目标组织
     */
    private String org;
    /**
     * 写入的默认目标储存桶
     */
    private String bucket;
    /**
     * 用于记录HTTP请求和响应的日志级别(默认不记录)
     */
    private LogLevel logLevel = LogLevel.NONE;
    /**
     * {@code OkHttpClient}的读超时(默认值10000ms)
     */
    private Duration readTimeout = Duration.ofMillis(10000);
    /**
     * {@code OkHttpClient}的写超时(默认值10000ms)
     */
    private Duration writeTimeout = Duration.ofMillis(10000);
    /**
     * {@code OkHttpClient}的连接超时(默认值10000ms)
     */
    private Duration connectTimeout = Duration.ofMillis(10000);

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

}
