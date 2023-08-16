package com.demo.tool;

import com.demo.autoconfigure.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

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
//@Component
public class MqttTemp {

    /**
     * Mqtt配置属性
     */
    private final MqttProperties mqttProperties;

    /**
     * 静态注入(自动注入)
     *
     * @param mqttProperties MqttProperties
     */
    public MqttTemp(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    /**
     * MqttPahoClientFactory
     */
    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttProperties.getUri());
        options.setUserName(mqttProperties.getUsername());
        if (mqttProperties.getPassword() != null) {
            options.setPassword(mqttProperties.getPassword().toCharArray());
        }
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        options.setCleanSession(mqttProperties.getCleanSession());
        options.setAutomaticReconnect(mqttProperties.getAutomaticReconnect());
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * <h1>默认发送接口</h1>
     *
     * <h2>topic 主题</h2>
     * `/`分隔开主题层次<br>
     * 通配符：(仅可订阅，不可发布)<br>
     * `#`匹配多个层次，只能用在尾部<br>
     * `+`匹配一个层次<br>
     * 例如：<br>
     * topic/a只能匹配topic/a<br>
     * topic/b/#可以匹配topic/b、topic/b/、topic/b/c，但不能匹配b/c<br>
     * topic/c/+可以匹配topic/c/、topic/c/d，但不能匹配topic/c、topic/c/d/e<br>
     * topic/+/d可以匹配topic/e/d、topic//d，但不能匹配topic/e/f/d、topic/e/d/f
     *
     * <h2>QoS 服务质量</h2>
     * `0`尽力而为。消息发送者会想尽办法发送消息，但是遇到意外并不会重试<br>
     * `1`至少一次。消息接收者如果没有应答或者应答本身丢失，消息发送者会再次发送以保证消息接收者至少会收到一次，可能造成重复消息<br>
     * `2`恰好一次。会减少并发或者增加延时。当丢失或者重复消息是不可接受的时候，选择此级别<br>
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
         * 发送消息(默认主题和QoS)
         *
         * @param data 数据
         */
        void send(String data);

        /**
         * 发送消息(默认主题和QoS)
         *
         * @param data 数据
         */
        void send(byte[] data);

        /**
         * 发送消息(默认QoS)
         *
         * @param topic 主题
         * @param data  数据
         */
        void send(@Header(MqttHeaders.TOPIC) String topic, String data);

        /**
         * 发送消息(默认QoS)
         *
         * @param topic 主题
         * @param data  数据
         */
        void send(@Header(MqttHeaders.TOPIC) String topic, byte[] data);

        /**
         * 发送消息(默认主题)
         *
         * @param qos  QoS
         * @param data 数据
         */
        void send(@Header(MqttHeaders.QOS) int qos, String data);

        /**
         * 发送消息(默认主题)
         *
         * @param qos  QoS
         * @param data 数据
         */
        void send(@Header(MqttHeaders.QOS) int qos, byte[] data);

        /**
         * 发送消息
         *
         * @param topic 主题
         * @param qos   QoS
         * @param data  数据
         */
        void send(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String data);

        /**
         * 发送消息
         *
         * @param topic 主题
         * @param qos   QoS
         * @param data  数据
         */
        void send(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, byte[] data);

    }

}
