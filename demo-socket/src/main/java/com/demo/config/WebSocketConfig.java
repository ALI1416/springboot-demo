package com.demo.config;

import com.demo.interceptor.SessionAuthHandshakeInterceptor;
import com.demo.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * <h1>WebSocket配置类</h1>
 *
 * <p>
 * createDate 2021/12/16 10:05:53
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 前缀
                .setAllowedOriginPatterns("*") // 启用跨域
                // .withSockJS(); // 使用SockJS
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app") // 客户端--->服务端
                .setUserDestinationPrefix("/user/") // 客户端<--->客户端
                .enableSimpleBroker("/topic", "/queue"); // 服务端--->客户端(广播、队列)
    }

    /*将客户端渠道拦截器加入spring ioc容器*/
    @Bean
    public UserInterceptor createUserInterceptor() {
        return new UserInterceptor();
    }
}
