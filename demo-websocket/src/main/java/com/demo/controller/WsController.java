package com.demo.controller;

import com.demo.entity.po.User;
import com.demo.tool.WebSocketTemp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@AllArgsConstructor
@Slf4j
public class WsController {

    private final WebSocketTemp webSocketTemp;

    /**
     * 广播模式
     */
    // @MessageMapping是服务器处理客户端发来的消息，目的地是/app/broadcast(/app前缀是隐含的，用户发给服务器都必须带/app)
    @MessageMapping("/broadcast")
    // @SendTo不加这个注解，默认转发给消息代理(加上前缀/topic，也是/topic/broadcast)。
    // 这个是重写消息代理的目的地为/topic/broadcast，即广播地址
    @SendTo("/topic/broadcast")
    public String broadcast(@RequestBody User user, Principal principal) {
        log.info("broadcast:接收到用户[{}]发来的广播消息[{}]", principal.getName(), user.getMsg());
        return user.getMsg();
    }

    /**
     * 广播模式，实现方法2
     */
    @MessageMapping("/broadcast2")
    public void broadcast2(@RequestBody User user, Principal principal) {
        log.info("broadcast2:接收到用户[{}]发来的广播消息[{}]", principal.getName(), user.getMsg());
        webSocketTemp.send("/topic/broadcast2", user.getMsg());
    }

    /**
     * 订阅模式<br>
     * 用户发送请求，服务器直接返回数据，与HTTP类似，只不过这个是异步的
     */
    @SubscribeMapping("/subscribe/{user}")
    public String subscribe(@DestinationVariable String user, Principal principal) {
        log.info("subscribe:接收到用户[{}]发来的订阅消息[{}]", principal.getName(), user);
        return user;
    }

    /**
     * 用户模式
     */
    @MessageMapping("/one")
    // @SendToUser表示要将消息发送给指定的用户，会自动在消息目的地前补上"/user"前缀
    @SendToUser("/queue/one")
    public void sendToUser(@RequestBody User user, Principal principal) {
        webSocketTemp.send("/queue/one", user.getUsername(), user.getMsg());
        log.info("sendToUser:接收到用户[{}]发给用户[{}]的消息[{}]", principal.getName(), user.getUsername(), user.getMsg());
    }

    /**
     * 捕获异常，并发送给用户
     */
    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public Exception handleExceptions(Exception e) {
        e.printStackTrace();
        return e;
    }

}
