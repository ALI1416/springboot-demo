package com.demo.service.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

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
public class RabbitService3 {

    /**
     * 死信测试1
     */
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            exchange = @Exchange(type = ExchangeTypes.FANOUT, value = "deadLetterTest", autoDelete = "true"),
                            value = @Queue(value = "deadLetterTest1", autoDelete = "true", //
                                    arguments = { // 参数
                                            // @Argument(name = "x-message-ttl", value = "10000", type = "int"), // 队列过期时间(单位：毫秒)
                                            // @Argument(name = "x-max-length", value = "10", type = "int"), // 最大队列长度
                                            // @Argument(name = "x-max-length-bytes", value = "10", type = "int"), // 最大数据长度
                                            @Argument(name = "x-dead-letter-exchange", value = "dead"), // 死信交换机
                                            @Argument(name = "x-dead-letter-routing-key", value = "letter.Test1"), // 死信路由key
                                    }
                            )
                    )
            }
    )
    public void deadLetterTest1(String message) {
        if (message.length() == 1) {
            throw new RuntimeException("长度为1 " + message);
        }
        log.info("死信测试1 {}", message);
    }

    /**
     * 死信消息
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue, //
            exchange = @Exchange(type = ExchangeTypes.TOPIC, value = "dead", autoDelete = "true"), //
            key = {"letter.#"} //
    )})
    public void deadLetterMsg(String msg, Message message) {
        log.info("死信消息 {} 属性 {}", msg, message.getMessageProperties());
    }

}
