package com.demo.config;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.messaging.converter.MappingFastJsonMessageConverter;
import com.demo.constant.FormatConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>WebSocket配置</h1>
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

    /**
     * 前缀
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 前缀
                .setAllowedOriginPatterns("*") // 启用跨域
        ;
    }

    /**
     * 映射
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        ThreadPoolTaskScheduler heartbeat = new ThreadPoolTaskScheduler();
        heartbeat.setPoolSize(1);
        heartbeat.setThreadNamePrefix("ws-heartbeat-");
        heartbeat.initialize();
        registry.setApplicationDestinationPrefixes("/app") // 客户端--->服务端
                .setUserDestinationPrefix("/user/") // 客户端<--->客户端
                .enableSimpleBroker("/topic", "/queue") // 服务端--->客户端(广播、队列)
                .setHeartbeatValue(new long[]{10000, 10000}) // 心跳
                .setTaskScheduler(heartbeat) // 心跳定时器
        ;
    }

    /**
     * 消息转换器
     */
    @Override
    public boolean configureMessageConverters(List<MessageConverter> converters) {
        MappingFastJsonMessageConverter converter = new MappingFastJsonMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat(FormatConstant.DATE);
        config.setReaderFeatures(FormatConstant.JSON_READER_FEATURE);
        config.setWriterFeatures(FormatConstant.JSON_WRITER_FEATURE);
        converter.setFastJsonConfig(config);
        converters.add(new StringMessageConverter());
        converters.add(new ByteArrayMessageConverter());
        converters.add(converter);
        return false;
    }

    /**
     * 拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor == null) {
                    return getDisconnectMessage(message);
                }
                StompCommand command = accessor.getCommand();
                // 心跳
                if (command == null) {
                    return message;
                }
                switch (command) {
                    // 首次连接
                    case CONNECT: {
                        List<String> usernameList = accessor.getNativeHeader("username");
                        // 没有用户名
                        if (usernameList == null || usernameList.isEmpty()) {
                            return getDisconnectMessage(message);
                        }
                        String username = usernameList.get(0);
                        if (username == null || username.isEmpty()) {
                            return getDisconnectMessage(message);
                        }
                        // 设置用户名
                        accessor.setUser(() -> username);
                        break;
                    }
                    default:
                }
                return message;
            }
        });
    }

    /**
     * 获取关闭连接消息
     */
    private Message<?> getDisconnectMessage(Message<?> message) {
        Map<String, Object> headers = new HashMap<>(3);
        headers.put("simpMessageType", SimpMessageType.DISCONNECT);
        headers.put("stompCommand", StompCommand.DISCONNECT);
        headers.put("simpSessionId", message.getHeaders().get("simpSessionId"));
        return new GenericMessage<>(new byte[0], headers);
    }

}
