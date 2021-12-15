package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 前缀
                // .setAllowedOrigins("*") // 跨域
                .withSockJS(); // 使用SockJS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 设置广播和队列节点
        registry.enableSimpleBroker("/topic", "/queue");
        // 客户端向服务端发送消息需有/app前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 指定用户发送（一对一）的前缀/user
        registry.setUserDestinationPrefix("/user");
    }

}
