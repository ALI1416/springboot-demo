package com.demo.tool;

import com.demo.config.MqttConfig;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <h1>Mqtt模板</h1>
 *
 * <p>
 * createDate 2023/08/16 17:26:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
// @Component
public class MqttTemp {

    /**
     * Mqtt配置
     */
    private final MqttConfig mqttConfig;

    /**
     * 静态注入(自动注入)
     *
     * @param mqttConfig MqttConfig
     */
    public MqttTemp(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    /**
     * 发送
     *
     * @param defaultTopic 默认主题
     * @param defaultQos   默认QoS
     * @return MqttPahoMessageHandler
     */
    public MqttPahoMessageHandler sendMessage(String defaultTopic, int defaultQos) {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(UUID.randomUUID().toString(), mqttConfig.factory());
        handler.setAsync(true);
        handler.setDefaultTopic(defaultTopic);
        handler.setDefaultQos(defaultQos);
        handler.setConverter(new DefaultPahoMessageConverter());
        return handler;
    }

    /**
     * 接收
     *
     * @param topic         主题
     * @param qos           QoS
     * @param outputChannel MessageChannel
     * @return MqttPahoMessageDrivenChannelAdapter
     */
    public MqttPahoMessageDrivenChannelAdapter receiveMessage(String[] topic, int[] qos, MessageChannel outputChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(UUID.randomUUID().toString(), mqttConfig.factory(), topic);
        adapter.setQos(qos);
        adapter.setOutputChannel(outputChannel);
        adapter.setConverter(new DefaultPahoMessageConverter());
        return adapter;
    }

    /**
     * <h1>默认发送接口</h1>
     *
     * <h2><code>topic</code> 主题</h2>
     * <code>/</code> 分隔开主题层次<br>
     * 通配符：(仅可订阅，不可发布)<br>
     * <code>#</code> 匹配多个层次，只能用在尾部<br>
     * <code>+</code> 匹配一个层次<br>
     * 例如：<br>
     * topic/a只能匹配topic/a<br>
     * topic/b/#可以匹配topic/b、topic/b/、topic/b/c，但不能匹配b/c<br>
     * topic/c/+可以匹配topic/c/、topic/c/d，但不能匹配topic/c、topic/c/d/e<br>
     * topic/+/d可以匹配topic/e/d、topic//d，但不能匹配topic/e/f/d、topic/e/d/f
     *
     * <h2><code>QoS</code> 服务质量</h2>
     * <code>0</code> 尽力而为。消息发送者会想尽办法发送消息，但是遇到意外并不会重试<br>
     * <code>1</code> 至少一次。消息接收者如果没有应答或者应答本身丢失，消息发送者会再次发送以保证消息接收者至少会收到一次，可能造成重复消息<br>
     * <code>2</code> 恰好一次。会减少并发或者增加延时。当丢失或者重复消息是不可接受的时候，选择此级别<br>
     * 注意：服务端和客户端的QoS不同时，选取最小的值
     *
     * <p>
     * createDate 2022/06/30 17:38:43
     * </p>
     *
     * @author ALI[ali-k@foxmail.com]
     * @since 1.0.0
     **/
    public interface DefaultSend {

        /**
         * 发送(默认主题和QoS)
         *
         * @param data 数据
         */
        void send(String data);

        /**
         * 发送(默认主题和QoS)
         *
         * @param data 数据
         */
        void send(byte[] data);

        /**
         * 发送(默认QoS)
         *
         * @param topic 主题
         * @param data  数据
         */
        void send(@Header(MqttHeaders.TOPIC) String topic, String data);

        /**
         * 发送(默认QoS)
         *
         * @param topic 主题
         * @param data  数据
         */
        void send(@Header(MqttHeaders.TOPIC) String topic, byte[] data);

        /**
         * 发送(默认主题)
         *
         * @param qos  QoS
         * @param data 数据
         */
        void send(@Header(MqttHeaders.QOS) int qos, String data);

        /**
         * 发送(默认主题)
         *
         * @param qos  QoS
         * @param data 数据
         */
        void send(@Header(MqttHeaders.QOS) int qos, byte[] data);

        /**
         * 发送
         *
         * @param topic 主题
         * @param qos   QoS
         * @param data  数据
         */
        void send(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String data);

        /**
         * 发送
         *
         * @param topic 主题
         * @param qos   QoS
         * @param data  数据
         */
        void send(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, byte[] data);

    }

}
