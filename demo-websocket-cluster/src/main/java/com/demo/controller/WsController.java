package com.demo.controller;

import cn.z.redis.RedisTemp;
import cn.z.websocket.WebSocketTemp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    public static final String REDIS_WEBSOCKET_BROADCAST_PREFIX = "WebSocketBroadcast:";

    private final RedisTemp redisTemp;

    /**
     * WebSocket接收用户发给用户的消息
     */
    @MessageMapping("/sendToUser/{user}")
    public void sendToUser(@DestinationVariable String user, String msg, Principal principal) {
        String data = "sendToUser:用户[" + principal.getName() + "]发送给用户[" + user + "]消息[" + msg + "]";
        log.info(data);
        redisTemp.broadcast(REDIS_WEBSOCKET_BROADCAST_PREFIX + user, data);
    }

}
