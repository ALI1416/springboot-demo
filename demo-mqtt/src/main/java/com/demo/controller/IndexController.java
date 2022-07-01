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
@RestController
@AllArgsConstructor
public class IndexController {

    private final MqttSend mqttSend;
    private final MqttSend2 mqttSend2;

    /**
     * 发送
     */
    @GetMapping(value = {"", "/", "index"})
    public Result index() {
        mqttSend.send("2333");
        mqttSend2.send("666");
        return Result.o();
    }

}
