package com.demo.service.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * <h1>Rabbit消费者服务</h1>
 *
 * <p>
 * createDate 2021/03/13 15:49:32
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
// 监听注解：@RabbitListener
// queuesToDeclare声明队列，如果没有去创建
// Queue("hello")
// "hello"为队列名称，默认是持久化、非独占、不自动删除
// @Queue(value = "hello", durable = "false", exclusive = "true", autoDelete = "true")
// durable = "false"不持久化，exclusive = "true"仅自己可见，autoDelete = "true"队列为空时自动删除
@RabbitListener(queuesToDeclare = @Queue(value = "p2p", autoDelete = "true"))
@Slf4j
public class RabbitService {

    /**
     * 点对点模型：只能有1个消费者<br>
     * 回调注解：@RabbitHandler
     */
    @RabbitHandler
    public void p2p(Long id) {
        log.info("点对点模型 {}", id);
    }

}
