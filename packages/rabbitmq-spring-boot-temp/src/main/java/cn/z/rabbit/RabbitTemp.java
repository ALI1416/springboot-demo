package cn.z.rabbit;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

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
public class RabbitTemp {

    /**
     * RabbitMQ模板
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * 构造函数(自动注入)
     *
     * @param rabbitTemplate RabbitTemplate
     */
    public RabbitTemp(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送(点对点模型、工作模型)
     *
     * @param queue 队列
     * @param data  数据
     */
    public void send(String queue, Object data) {
        rabbitTemplate.convertAndSend(queue, data);
    }

    /**
     * 发送(点对点模型、工作模型)
     *
     * @param queue  队列
     * @param data   数据
     * @param expire 消息过期时间(秒)
     */
    public void send(String queue, Object data, long expire) {
        rabbitTemplate.convertAndSend(queue, data, setExpire(expire));
    }

    /**
     * 广播
     *
     * @param exchange 交换机
     * @param data     数据
     */
    public void broadcast(String exchange, Object data) {
        rabbitTemplate.convertAndSend(exchange, "", data);
    }

    /**
     * 广播
     *
     * @param exchange 交换机
     * @param data     数据
     * @param expire   消息过期时间(秒)
     */
    public void broadcast(String exchange, Object data, long expire) {
        rabbitTemplate.convertAndSend(exchange, "", data, setExpire(expire));
    }

    /**
     * 发送(路由模型、动态路由模型)
     *
     * @param exchange   交换机
     * @param routingKey 路由key
     * @param data       数据
     */
    public void send(String exchange, String routingKey, Object data) {
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
    }

    /**
     * 发送(路由模型、动态路由模型)
     *
     * @param exchange   交换机
     * @param routingKey 路由key
     * @param data       数据
     * @param expire     消息过期时间(秒)
     */
    public void send(String exchange, String routingKey, Object data, long expire) {
        rabbitTemplate.convertAndSend(exchange, routingKey, data, setExpire(expire));
    }

    /**
     * 设置消息过期时间
     *
     * @param expire 消息过期时间(秒)
     * @return MessagePostProcessor
     */
    private static MessagePostProcessor setExpire(long expire) {
        return message -> {
            message.getMessageProperties().setExpiration(Long.toString(expire * 1000));
            return message;
        };
    }

}
