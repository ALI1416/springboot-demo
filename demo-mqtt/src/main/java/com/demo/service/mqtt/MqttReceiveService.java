package com.demo.service.mqtt;

import com.demo.config.MqttConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.UUID;

/**
 * <h1>接收</h1>
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
public class MqttReceiveService {

    private final MqttConfig mqttConfig;

    /**
     * 名称
     */
    private static final String NAME = "receive";
    /**
     * 客户端id
     */
    private static final String CLIENT_ID = UUID.randomUUID().toString();
    /**
     * 主题
     */
    private static final String[] TOPIC = {"topic/a", "topic/b/#"};
    /**
     * QoS
     */
    private static final int[] QOS = {0};
    /**
     * 完成超时时长(毫秒)[默认:30000]
     */
    private static final int TIMEOUT = 5000;

    /**
     * 入站通道
     */
    @Bean(NAME)
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    /**
     * 入站属性
     */
    @Bean("receive_" + NAME)
    public MessageProducer receive() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(CLIENT_ID,
                mqttConfig.mqttPahoClientFactory(), TOPIC);
        adapter.setCompletionTimeout(TIMEOUT);
        // 消息转换器
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        // 按字节接收消息
        // defaultPahoMessageConverter.setPayloadAsBytes(true);
        adapter.setConverter(defaultPahoMessageConverter);
        adapter.setQos(QOS);
        adapter.setOutputChannel(inputChannel());
        return adapter;
    }

    /**
     * 入站消息
     */
    @Bean("message_" + NAME)
    @ServiceActivator(inputChannel = NAME)
    public MessageHandler message() {
        return message -> log.info("payload:{},topic:{},qos:{}", //
                message.getPayload(), //
                message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC), //
                message.getHeaders().get(MqttHeaders.RECEIVED_QOS));
    }

}
