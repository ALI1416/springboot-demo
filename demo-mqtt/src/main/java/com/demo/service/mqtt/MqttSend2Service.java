package com.demo.service.mqtt;

import com.demo.config.MqttConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * <h1>发送</h1>
 *
 * <p>
 * createDate 2022/07/01 11:26:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@Slf4j
@AllArgsConstructor
public class MqttSend2Service {

    private final MqttConfig mqttConfig;

    /**
     * 名称
     */
    public static final String NAME = "service_send2";
    /**
     * 客户端id
     */
    private static final String CLIENT_ID = "client_" + NAME;
    /**
     * 默认主题
     */
    private static final String TOPIC = "topic/c";
    /**
     * 默认QoS
     */
    private static final int QOS = 0;

    /**
     * 出站通道
     */
    @Bean(NAME)
    public MessageChannel outputChannel() {
        return new DirectChannel();
    }

    /**
     * 出站属性
     */
    @Bean("sendMethod_" + NAME)
    @ServiceActivator(inputChannel = NAME)
    public MessageHandler send() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(CLIENT_ID,
                mqttConfig.mqttPahoClientFactory());
        // 异步
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(TOPIC);
        messageHandler.setDefaultQos(QOS);
        // 消息转换器
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        // 按字节发送消息
        // defaultPahoMessageConverter.setPayloadAsBytes(true);
        messageHandler.setConverter(defaultPahoMessageConverter);
        return messageHandler;
    }

}
