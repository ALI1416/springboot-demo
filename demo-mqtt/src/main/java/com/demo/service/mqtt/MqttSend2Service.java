package com.demo.service.mqtt;

import com.demo.tool.MqttTemp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * <h1>发送服务2</h1>
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

    private final MqttTemp mqttTemp;

    /**
     * 名称
     */
    public static final String NAME = "mqtt_send2";
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
    @Bean(NAME + "_send")
    @ServiceActivator(inputChannel = NAME)
    public MessageHandler send() {
        return mqttTemp.sendMessage(TOPIC, QOS);
    }

}
