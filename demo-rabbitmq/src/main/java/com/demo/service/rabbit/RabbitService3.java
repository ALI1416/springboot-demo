package com.demo.service.rabbit;

import com.demo.constant.RabbitQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

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

    private final RabbitTemplate rabbitTemplate;

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
     * 死信测试3
     */
    @Bean
    public void deadLetterTest3() throws Exception {
        ConnectionFactory factory = rabbitTemplate.getConnectionFactory();
        Connection connection = factory.createConnection();
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 10000); // 队列消息过期时间(单位：毫秒)(ready状态)
        arguments.put("x-max-length", 5); // 最大队列长度(ready状态)
        arguments.put("x-max-length-bytes", 10); // 最大数据长度(所有ready状态body的总长度)
        arguments.put("x-dead-letter-exchange", ""); // 死信交换机
        arguments.put("x-dead-letter-routing-key", RabbitQueue.DEAD_LETTER); // 死信队列
        connection.createChannel(false).queueDeclare(RabbitQueue.DEAD_LETTER_TEST3, true, true, true, arguments);
        connection.close();
    }

    /**
     * 死信消息
     */
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitQueue.DEAD_LETTER, autoDelete = "true"))
    public void deadLetterMsg(String msg, Message message) {
        log.info("死信消息 {} 属性 {}", msg, message.getMessageProperties());
    }

}
