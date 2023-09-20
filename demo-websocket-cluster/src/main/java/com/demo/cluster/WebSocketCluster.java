package com.demo.cluster;

import cn.z.rabbit.RabbitTemp;
import cn.z.websocket.WebSocketTemp;
import com.demo.entity.po.WsMsg;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

/**
 * <h1>WebSocket集群</h1>
 *
 * <p>
 * createDate 2021/12/16 10:05:53
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Controller
@AllArgsConstructor
@Slf4j
public class WebSocketCluster {

    /**
     * 交换机名称
     */
    private static final String EXCHANGE_NAME = "WebSocketBroadcast";

    private final WebSocketTemp webSocketTemp;
    private final RabbitTemp rabbitTemp;

    /**
     * WebSocket接收用户发给用户的消息
     */
    @MessageMapping("/one")
    public void receiveMsgFromUser2User(@RequestBody WsMsg<String> wsMsg, Principal principal) {
        log.info("WebSocket接收到用户[{}]发给用户[{}]的消息[{}]", principal.getName(), wsMsg.getUsername(), wsMsg.getMsg());
        // 通过Rabbit发送广播消息
        rabbitTemp.send(EXCHANGE_NAME, "", wsMsg);
    }

    /**
     * Rabbit接收广播消息
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = EXCHANGE_NAME, type = "fanout")
    )})
    public void receiveWsMsg(WsMsg<String> wsMsg) {
        log.info("Rabbit接收到发给用户[{}]的消息[{}]", wsMsg.getUsername(), wsMsg.getMsg());
        // 通过WebSocket发送给指定用户消息
        webSocketTemp.send("/queue/one", wsMsg.getUsername(), wsMsg.getMsg());
    }

}
