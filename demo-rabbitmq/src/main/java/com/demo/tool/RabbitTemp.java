package com.demo.tool;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * <h1>RabbitMQ模板</h1>
 *
 * <p>
 * createDate 2023/08/15 17:50:30
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
public class RabbitTemp {

    /**
     * RabbitMQ模板
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * 静态注入(自动注入)
     *
     * @param rabbitTemplate RabbitTemplate
     */
    public RabbitTemp(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送
     *
     * @param queue 队列
     * @param data  数据
     */
    public void send(String queue, Object data) {
        rabbitTemplate.convertAndSend(queue, data);
    }

    /**
     * 发送
     *
     * @param exchange 交换机
     * @param queue    队列
     * @param data     数据
     */
    public void send(String exchange, String queue, Object data) {
        rabbitTemplate.convertAndSend(exchange, queue, data);
    }

}
