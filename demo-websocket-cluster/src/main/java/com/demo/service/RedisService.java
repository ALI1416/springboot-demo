package com.demo.service;

import cn.z.redis.annotation.ModeEnum;
import cn.z.redis.annotation.Subscribe;
import cn.z.websocket.WebSocketTemp;
import com.demo.constant.RedisConstant;
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
     * 广播模式
     */
    @Subscribe(value = RedisConstant.WS_BROADCAST_PREFIX + "*", mode = ModeEnum.MATCH)
    public void broadcast(String msg, String topic) {
        String[] split = topic.split(":", -1);
        String wsTopic = split[2];
        log.info("广播模式:广播消息[{}]", msg);
        webSocketTemp.send(wsTopic, msg);
    }

    /**
     * 用户模式
     */
    @Subscribe(value = RedisConstant.WS_USER_PREFIX + "*", mode = ModeEnum.MATCH)
    public void sendToUser(String msg, String topic) {
        String[] split = topic.split(":", -1);
        String wsTopic = split[2];
        String wsUsername = split[3];
        log.info("用户模式:发送给用户[{}]消息[{}]", wsUsername, msg);
        webSocketTemp.send(wsTopic, wsUsername, msg);
    }

}
