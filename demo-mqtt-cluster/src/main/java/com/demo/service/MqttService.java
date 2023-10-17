package com.demo.service;

import cn.z.mqtt.annotation.Subscribe;
import cn.z.redis.RedisTemp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <h1>MQTT服务</h1>
 *
 * <p>
 * createDate 2023/09/14 18:21:17
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@Slf4j
@AllArgsConstructor
public class MqttService {

    private static final String REDIS_MQTT_PREFIX = "mqtt:";
    private static final long REDIS_MQTT_TIMEOUT = 1L;
    private final RedisTemp redisTemp;

    /**
     * 接收消息
     */
    @Subscribe("receive")
    public void receive(String msg, String topic) {
        if (Boolean.TRUE.equals(redisTemp.setIfAbsent(REDIS_MQTT_PREFIX + topic, REDIS_MQTT_TIMEOUT))) {
            log.info("msg:{},topic:{}", msg, topic);
        }
    }

}
