package com.demo.controller;

import cn.z.websocket.WebSocketTemp;
import com.demo.entity.po.WsMsg;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

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
     * 广播模式(注解实现)
     */
    // @MessageMapping是服务器处理客户端发来的消息，目的地是/app/broadcast(/app前缀是隐含的，用户发给服务器都必须带/app)
    @MessageMapping("/broadcast")
    // @SendTo不加这个注解，默认转发给消息代理(加上前缀/topic，也是/topic/broadcast)。
    // 这个是重写消息代理的目的地为/topic/broadcast，即广播地址
    @SendTo("/topic/broadcast")
    public String broadcast(@RequestBody WsMsg<String> wsMsg, Principal principal) {
        log.info("broadcast:接收到用户[{}]发来的广播消息[{}]", principal.getName(), wsMsg.getMsg());
        return wsMsg.getMsg();
    }

    /**
     * 广播模式(方法实现)
     */
    @MessageMapping("/broadcast2")
    public void broadcast2(@RequestBody WsMsg<String> wsMsg, Principal principal) {
        log.info("broadcast2:接收到用户[{}]发来的广播消息[{}]", principal.getName(), wsMsg.getMsg());
        webSocketTemp.send("/topic/broadcast2", wsMsg.getMsg());
    }

    /**
     * 订阅模式(订阅成功返回消息)<br>
     * 用户发送请求，服务器直接返回数据，与HTTP类似，只不过这个是异步的
     */
    @SubscribeMapping("/subscribe/{user}")
    public String subscribe(@DestinationVariable String user, Principal principal) {
        log.info("subscribe:接收到用户[{}]发来的订阅消息[{}]", principal.getName(), user);
        return user;
    }

    /**
     * 用户模式
     */
    @MessageMapping("/one")
    public void sendToUser(@RequestBody WsMsg<String> wsMsg, Principal principal) {
        webSocketTemp.send("/queue/one", wsMsg.getUsername(), wsMsg.getMsg());
        log.info("sendToUser:接收到用户[{}]发给用户[{}]的消息[{}]", principal.getName(), wsMsg.getUsername(), wsMsg.getMsg());
    }

}
