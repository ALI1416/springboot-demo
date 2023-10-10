package com.demo.service;

import cn.z.redis.annotation.ModeEnum;
import cn.z.redis.annotation.Subscribe;
import cn.z.websocket.WebSocketTemp;
import com.demo.controller.WsController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <h1>RedisService</h1>
 *
 * <p>
 * createDate 2023/10/10 20:19:50
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
@Slf4j
public class RedisService {

    private final WebSocketTemp webSocketTemp;

    /**
     * Redis接收广播消息
     */
    @Subscribe(value = WsController.REDIS_WEBSOCKET_BROADCAST_PREFIX + "*", mode = ModeEnum.MATCH)
    public void sendToUser(String msg, String topic) {
        String user = topic.split(":", -1)[1];
        log.info("Redis接收到发给用户[{}]的消息[{}]", user, msg);
        webSocketTemp.send("/queue/sendToUser", user, msg);
    }

}
