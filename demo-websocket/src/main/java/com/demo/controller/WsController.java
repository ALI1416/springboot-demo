package com.demo.controller;

import cn.z.websocket.WebSocketTemp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * <h1>WsController</h1>
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
public class WsController {

    private final WebSocketTemp webSocketTemp;

    /**
     * 广播模式
     */
    // 接收来自用户发送到/app/broadcast的消息，默认转发到/topic/broadcast
    @MessageMapping("/broadcast")
    // 重写转发地址
    @SendTo("/topic/broadcast")
    public String broadcast(String msg, Principal principal) {
        String data = "broadcast:用户[" + principal.getName() + "]发送广播消息[" + msg + "]";
        log.info(data);
        // 手动发送广播
        webSocketTemp.send("/topic/broadcast2", "broadcast2:" + data);
        return data;
    }

    /**
     * 订阅模式(等同于HTTP异步请求)
     */
    // 接收来自用户发送到/app/subscribe/{path}的请求(没有消息)，不会转发，仅返回一次订阅成功的消息(返回到原用户原路径)
    @SubscribeMapping("/subscribe/{path}")
    public String subscribe(@DestinationVariable String path, Principal principal) {
        String data = "subscribe:用户[" + principal.getName() + "]订阅[/subscribe/" + path + "]";
        log.info(data);
        return data;
    }

    /**
     * 用户模式
     */
    // 接收来自用户发送到/app/sendToUser/{user}的消息，返回类型为void，不会转发
    @MessageMapping("/sendToUser/{user}")
    public void sendToUser(@DestinationVariable String user, String msg, Principal principal) {
        String data = "sendToUser:用户[" + principal.getName() + "]发送给用户[" + user + "]消息[" + msg + "]";
        log.info(data);
        // 手动发送给用户
        webSocketTemp.send("/queue/sendToUser", user, data);
    }

}
