package com.demo.controller;

import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WsController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 广播模式
     */
    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")
    public Result broadcast(String msg) {
        System.out.println("接收消息：" + msg);
        return Result.o(msg);
    }

    /**
     * 订阅模式，只是在订阅的时候触发，可以理解为：访问——>返回数据
     */
    @SubscribeMapping("/subscribe/{id}")
    public Result subscribe(@DestinationVariable Long id) {
        return Result.o(id);
    }

    /**
     * 用户模式
     */
    @MessageMapping("/one")
    @SendToUser("/queue/one")
    public Result sendMsgByUser(String user, String msg) {
        simpMessagingTemplate.convertAndSendToUser(user, "/queue/one", msg);
        System.out.println("接收用户：" + user + "消息：" + msg);
        return Result.o();
    }

    @GetMapping("/sendMsgByAll")
    public Result sendMsgByAll(String msg) {
        simpMessagingTemplate.convertAndSend("/topic", msg);
        return Result.o();
    }

}
