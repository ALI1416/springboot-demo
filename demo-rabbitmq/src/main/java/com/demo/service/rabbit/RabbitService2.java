package com.demo.service.rabbit;

import com.alibaba.fastjson2.JSONObject;
import com.demo.constant.RabbitExchange;
import com.demo.constant.RabbitQueue;
import com.demo.entity.po.Person;
import com.demo.entity.proto.PersonProto;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * <h1>Rabbit消费者服务2</h1>
 *
 * <p>
 * createDate 2021/03/13 15:49:32
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
// @Service
@Slf4j
public class RabbitService2 {

    /**
     * 工作模型，至少2个消费者(队列需要相同名字)，默认平均分配<br>
     * RabbitListener可以直接写到方法上<br>
     * 消费者1
     */
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitQueue.WORK, autoDelete = "true"))
    public void work1(String message) {
        log.info("工作模型消费者1 {}", message);
    }

    /**
     * 工作模型<br>
     * 消费者2
     */
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitQueue.WORK, autoDelete = "true"))
    public void work2(String message) {
        log.info("工作模型消费者2 {}", message);
    }

    /**
     * 广播模型(type为fanout)<br>
     * 消费者1
     */
    @RabbitListener(bindings = {@QueueBinding( // 绑定交换机和队列
            value = @Queue, // 队列：随机名称、自动删除、独占
            exchange = @Exchange(type = ExchangeTypes.FANOUT, value = RabbitExchange.BROADCAST, autoDelete = "true") // 交换机：类型、名称、自动删除
    )})
    public void broadcast1(Long id) {
        log.info("广播模型消费者1 {}", id);
    }

    /**
     * 广播模型<br>
     * 消费者2
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue, //
            exchange = @Exchange(type = ExchangeTypes.FANOUT, value = RabbitExchange.BROADCAST, autoDelete = "true") //
    )})
    public void broadcast2(Long id) {
        log.info("广播模型消费者2 {}", id);
    }

    /**
     * 路由模型<br>
     * 消费者1
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue, //
            exchange = @Exchange(type = ExchangeTypes.DIRECT, value = RabbitExchange.ROUTE, autoDelete = "true"), // 类型：默认路由模型
            key = {"error", "warn", "info", "trace", "debug"} // 可接收的路由key
    )})
    public void route1(String message) {
        log.info("路由模型消费者1 {}", message);
    }

    /**
     * 路由模型<br>
     * 消费者2
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue, //
            exchange = @Exchange(value = RabbitExchange.ROUTE, autoDelete = "true"), //
            key = {"error", "warn"} //
    )})
    public void route2(String message) {
        log.info("路由模型消费者2 {}", message);
    }

    /**
     * 动态路由模型<br>
     * 消费者1
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue, //
            exchange = @Exchange(type = ExchangeTypes.TOPIC, value = RabbitExchange.DYNAMIC_ROUTE, autoDelete = "true"), // 类型：动态路由模型
            key = {"user", "admin.*", "root.#"} // 可接收的路由key，用.划分层次，*匹配1个层次，#匹配0个及以上层次
    )})
    public void dynamicRoute1(String message) {
        log.info("动态路由模型消费者1 {}", message);
    }

    /**
     * 动态路由模型<br>
     * 消费者2
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue, //
            exchange = @Exchange(type = ExchangeTypes.TOPIC, value = RabbitExchange.DYNAMIC_ROUTE, autoDelete = "true"), //
            key = {"admin", "root.*"} //
    )})
    public void dynamicRoute2(String message) {
        log.info("动态路由模型消费者2 {}", message);
    }

    /**
     * ProtocolBuffers1<br>
     */
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitQueue.PROTOCOL_BUFFERS1, autoDelete = "true"))
    public void protocolBuffers1(byte[] bytes) throws Exception {
        // 解码并转换成JSON字符串
        String string = JsonFormat.printer().print(PersonProto.Person.parseFrom(bytes));
        log.info("ProtocolBuffers1 {}", string);
    }

    /**
     * ProtocolBuffers2<br>
     */
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitQueue.PROTOCOL_BUFFERS2, autoDelete = "true"))
    public void protocolBuffers2(byte[] bytes) throws Exception {
        // 先解码并转换成JSON字符串，再转换成Person对象
        String string = JsonFormat.printer().print(PersonProto.Person.parseFrom(bytes));
        Person person = JSONObject.parseObject(string, Person.class);
        log.info("ProtocolBuffers2 {}", person);
    }

}
