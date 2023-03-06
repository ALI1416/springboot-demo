package com.demo.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Tomcat配置类</h1>
 *
 * <p>
 * 功能1(默认关闭)：http(80)协议切换到https(443)协议<br>
 * 功能2(默认开启)：请求地址和参数可以接收<code>"<>[\]^`{|}</code>字符
 * </p>
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
        /*功能1(默认关闭)：http(80)协议切换到https(443)协议*/
        // TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
        //     @Override
        //     protected void postProcessContext(Context context) {
        //         SecurityConstraint securityConstraint = new SecurityConstraint();
        //         securityConstraint.setUserConstraint("CONFIDENTIAL");
        //         SecurityCollection collection = new SecurityCollection();
        //         collection.addPattern("/*");
        //         securityConstraint.addCollection(collection);
        //         context.addConstraint(securityConstraint);
        //     }
        // };
        // Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        // connector.setScheme("http");
        // connector.setPort(80);
        // connector.setSecure(false);
        // connector.setRedirectPort(443);
        // factory.addAdditionalTomcatConnectors(connector);

        /*功能2(默认开启)：请求地址和参数允许接收`^\|[]{}字符*/
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((Connector connectorCustomize) -> {
            connectorCustomize.setProperty("relaxedPathChars", "`^\\|[]{}");
            connectorCustomize.setProperty("relaxedQueryChars", "`^\\|[]{}");
        });
        return factory;
    }

}
