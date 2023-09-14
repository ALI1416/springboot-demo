package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.service.mqtt.MqttSend;
import com.demo.service.mqtt.MqttSend2;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
// @RestController
@AllArgsConstructor
public class IndexController {

    private final MqttSend mqttSend;
    private final MqttSend2 mqttSend2;

    /**
     * 默认主题和QoS
     */
    @GetMapping(value = {"", "/", "index"})
    public Result index() {
        mqttSend.send("默认主题和QoS");
        return Result.o();
    }

    /**
     * 默认QoS
     */
    @GetMapping("topic")
    public Result qos(String topic) {
        mqttSend2.send(topic, "默认QoS".getBytes());
        return Result.o();
    }

    /**
     * 默认主题
     */
    @GetMapping("qos")
    public Result topic(Integer qos) {
        mqttSend.send(qos, "默认主题".getBytes());
        return Result.o();
    }

    /**
     * 发送
     */
    @GetMapping("send")
    public Result send(String topic, Integer qos) {
        mqttSend2.send(topic, qos, "无默认");
        return Result.o();
    }

}
