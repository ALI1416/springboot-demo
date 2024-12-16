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
     * 发送(QoS=0 不保留)<br>
     * http://localhost:8080
     */
    @GetMapping
    public Result index() {
        mqttTemp.send("topic", "发送(QoS=0 不保留)");
        return Result.o();
    }

    /**
     * 发送(QoS=0 不保留)<br>
     * http://localhost:8080/topic?topic=all
     */
    @GetMapping("topic")
    public Result topic(String topic) {
        mqttTemp.send("a+", "发送(QoS=0 不保留)");
        return Result.o();
    }

    /**
     * 发送(不保留)<br>
     * http://localhost:8080/topicAndQos?topic=all&qos=2
     */
    @GetMapping("topicAndQos")
    public Result topicAndQos(String topic, int qos) {
        mqttTemp.send(topic, "发送(不保留)", qos);
        return Result.o();
    }

    /**
     * 发送<br>
     * http://localhost:8080/topicAndQosAndRetain?topic=all&qos=2&retain=true
     */
    @GetMapping("topicAndQosAndRetain")
    public Result topicAndQosAndRetain(String topic, int qos, boolean retain) {
        mqttTemp.send(topic, "发送", qos, retain);
        return Result.o();
    }

}