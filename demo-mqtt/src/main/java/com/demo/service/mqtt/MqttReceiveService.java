package com.demo.service.mqtt;

import com.demo.tool.MqttTemp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * <h1>接收服务</h1>
 *
 * <p>
 * createDate 2022/07/01 11:26:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
// @Configuration
@Slf4j
@AllArgsConstructor
public class MqttReceiveService {

    private final MqttTemp mqttTemp;

    /**
     * 名称
     */
    private static final String NAME = "mqtt_receive";
    /**
     * 主题
     */
    private static final String[] TOPIC = {"topic/a", "topic/b/#"};
    /**
     * QoS
     */
    private static final int[] QOS = {0};

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
    @Bean(NAME + "_receive")
    public MessageProducer receive() {
        return mqttTemp.receiveMessage(TOPIC, QOS, inputChannel());
    }

    /**
     * 入站消息
     */
    @Bean(NAME + "_message")
    @ServiceActivator(inputChannel = NAME)
    public MessageHandler message() {
        return message -> log.info("topic:{},qos:{},data:{}", //
                message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC), //
                message.getHeaders().get(MqttHeaders.RECEIVED_QOS), //
                message.getPayload() //
        );
    }

}
