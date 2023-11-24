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
        /* http(80)协议切换到https(443)协议 */
        // TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
        //     @Override
        //     protected void postProcessContext(Context context) {
        //         SecurityConstraint constraint = new SecurityConstraint();
        //         constraint.setUserConstraint("CONFIDENTIAL");
        //         SecurityCollection collection = new SecurityCollection();
        //         collection.addPattern("/*");
        //         constraint.addCollection(collection);
        //         context.addConstraint(constraint);
        //     }
        // };
        // Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        // connector.setScheme("http");
        // connector.setPort(80);
        // connector.setSecure(false);
        // connector.setRedirectPort(443);
        // factory.addAdditionalTomcatConnectors(connector);

        /* 请求地址和参数允许接收`^\|[]{}字符 */
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connectorCustomize -> {
            connectorCustomize.setProperty("relaxedPathChars", "`^\\|[]{}");
            connectorCustomize.setProperty("relaxedQueryChars", "`^\\|[]{}");
        });
        return factory;
    }

}
