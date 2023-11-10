package com.demo.controller;

import cn.z.mqtt.MqttTemp;
import com.demo.entity.pojo.Result;
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
@RestController
@AllArgsConstructor
public class IndexController {

    private final MqttTemp mqttTemp;

    /**
     * http://localhost:8080 <br>
     * 发送(QoS=0 不保留)
     */
    @GetMapping
    public Result<Boolean> index() {
        return Result.o(mqttTemp.send("topic", "发送(QoS=0 不保留)"));
    }

    /**
     * http://localhost:8080/topic?topic=all <br>
     * 发送(QoS=0 不保留)
     */
    @GetMapping("topic")
    public Result<Boolean> topic(String topic) {
        return Result.o(mqttTemp.send(topic, "发送(QoS=0 不保留)"));
    }

    /**
     * http://localhost:8080/topicAndQos?topic=all&qos=2 <br>
     * 发送(不保留)
     */
    @GetMapping("topicAndQos")
    public Result<Boolean> topicAndQos(String topic, int qos) {
        return Result.o(mqttTemp.send(topic, "发送(不保留)", qos));
    }

    /**
     * http://localhost:8080/topicAndQosAndRetain?topic=all&qos=2&retain=true <br>
     * 发送
     */
    @GetMapping("topicAndQosAndRetain")
    public Result<Boolean> topicAndQosAndRetain(String topic, int qos, boolean retain) {
        return Result.o(mqttTemp.send(topic, "发送", qos, retain));
    }

}