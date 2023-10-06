package com.demo.service;

import cn.z.rabbit.RabbitTemp;
import cn.z.tool.ThreadPool;
import com.demo.constant.RabbitQueue;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Rabbit消费者服务3</h1>
 *
 * <p>
 * createDate 2023/09/18 11:28:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@Slf4j
@AllArgsConstructor
public class RabbitService3 {

    private final RabbitTemp rabbitTemp;
    private final ConnectionFactory factory;

    /**
     * 死信测试1
     */
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitQueue.DEAD_LETTER_TEST1, autoDelete = "true"))
    public void deadLetterTest1(Integer delay) throws Exception {
        log.info("死信测试1 {}", delay);
        Thread.sleep(delay);
    }

    /**
     * 死信测试2
     */
    @RabbitListener(queuesToDeclare = @Queue( //
            value = RabbitQueue.DEAD_LETTER_TEST2, autoDelete = "true", //
            arguments = { // 参数
                    @Argument(name = "x-dead-letter-exchange", value = ""), // 死信交换机
                    @Argument(name = "x-dead-letter-routing-key", value = RabbitQueue.DEAD_LETTER), // 死信队列
            }))
    public void deadLetterTest2(Integer delay) throws Exception {
        log.info("死信测试2 {}", delay);
        Thread.sleep(delay);
    }

    /**
     * 死信测试3<br>
     * 非注解创建队列
     */
    @Bean
    public void deadLetterTest3() throws Exception {
        try (Connection connection = factory.createConnection()) {
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-message-ttl", 10000); // 队列消息过期时间(单位：毫秒)(ready状态)
            arguments.put("x-max-length", 5); // 最大队列长度(ready状态)
            arguments.put("x-max-length-bytes", 10); // 最大总数据长度(所有ready状态body的总长度)
            arguments.put("x-dead-letter-exchange", ""); // 死信交换机
            arguments.put("x-dead-letter-routing-key", RabbitQueue.DEAD_LETTER); // 死信队列
            try (Channel channel = connection.createChannel(false)) {
                channel.queueDeclare(RabbitQueue.DEAD_LETTER_TEST3, true, true, true, arguments);
            }
        }
    }

    /**
     * 非注解监听队列
     */
    @Bean
    public void test() throws Exception {
        try (Connection connection = factory.createConnection()) {
            try (Channel channel = connection.createChannel(false)) {
                channel.queueDeclare(RabbitQueue.TEST, true, true, true, null);
                channel.basicConsume(RabbitQueue.TEST, true, new DefaultConsumer(channel) {

                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                        log.info("测试 {}", new String(body, StandardCharsets.UTF_8));
                    }

                });
            }
        }
    }

    /**
     * 死信测试4(线程池模拟死信)
     */
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitQueue.DEAD_LETTER_TEST4, autoDelete = "true"))
    public void deadLetterTest4(Integer delay) {
        log.info("死信测试4(线程池模拟死信) {}", delay);
        ThreadPool.execute(() -> {
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                // 异常手动发送到死信队列
                rabbitTemp.send(RabbitQueue.DEAD_LETTER, delay);
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 死信消息
     */
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitQueue.DEAD_LETTER, autoDelete = "true"))
    public void deadLetterMsg(String msg, Message message) {
        log.info("死信消息 {} 属性 {}", msg, message.getMessageProperties());
    }

}
