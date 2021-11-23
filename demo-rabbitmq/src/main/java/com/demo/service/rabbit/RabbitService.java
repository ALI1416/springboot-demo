package com.demo.service.rabbit;

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
// @RabbitListener消费者监听
// queuesToDeclare声明队列，如果没有去创建
// Queue("hello")中"hello"为队列名称，默认是持久化、非独占、不自动删除
// @Queue(value = "hello", durable = "false", autoDelete = "true")中
// durable = "false"不持久化，autoDelete = "true"队列为空时自动删除
@RabbitListener(queuesToDeclare = @Queue("hello"))
public class RabbitService {

    /**
     * 点对点模型：只有1个消费者<br>
     * 可以定义任意的方法名，需要接受一个String类型的参数<br>
     * RabbitHandler取出队列消息的回调方法
     *
     * @param message 队列中取出的内容
     */
    @RabbitHandler
    public void receiver(String message) {
        System.out.println("RabbitService.receiver收到消息：" + message);
    }

}
