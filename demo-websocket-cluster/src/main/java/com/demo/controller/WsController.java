package com.demo.controller;

import cn.z.redis.RedisTemp;
import com.demo.constant.RedisConstant;
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

    private final RedisTemp redisTemp;

    /**
     * 广播模式
     */
    @MessageMapping("/broadcast")
    public void broadcast(String msg, Principal principal) {
        String data = "广播模式:用户[" + principal.getName() + "]发送广播消息[" + msg + "]";
        log.info(data);
        redisTemp.broadcast(RedisConstant.WS_BROADCAST_PREFIX + "/topic/broadcast", data);
    }

    /**
     * 用户模式
     */
    @MessageMapping("/sendToUser/{user}")
    public void sendToUser(@DestinationVariable String user, String msg, Principal principal) {
        String data = "用户模式:用户[" + principal.getName() + "]发送给用户[" + user + "]消息[" + msg + "]";
        log.info(data);
        redisTemp.broadcast(RedisConstant.WS_USER_PREFIX + "/queue/sendToUser:" + user, data);
    }

}
