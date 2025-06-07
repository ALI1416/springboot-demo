package com.demo.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Tomcat配置</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
@Configuration
public class TomcatConfig {

    /**
     * Tomcat配置
     */
    @Bean
    public TomcatServletWebServerFactory webServerFactory() {
        /* 请求地址和参数允许接收`^\|[]{}字符 */
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connectorCustomize -> {
            connectorCustomize.setProperty("relaxedPathChars", "`^\\|[]{}");
            connectorCustomize.setProperty("relaxedQueryChars", "`^\\|[]{}");
        });
        return factory;
    }

}
