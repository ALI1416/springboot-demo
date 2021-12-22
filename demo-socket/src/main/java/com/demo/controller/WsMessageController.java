package com.demo.controller;

import cn.z.id.Id;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.security.Principal;
import java.util.concurrent.ConcurrentHashMap;

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
public class WsMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final ConcurrentHashMap<Long, Session> SESSION_POOL = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        SESSION_POOL.put(Id.next(), session);
        log.info("连接建立");
    }

    @OnClose
    public void onClose() {
        // SESSION_POOL.remove();
        log.info("连接关闭");
    }

    public static ConcurrentHashMap<Long, Session> getSessionPool() {
        return SESSION_POOL;
    }

    /**
     * 广播模式
     */
    // @MessageMapping是服务器处理客户端发来的消息，目的地是/app/broadcast(/app前缀是隐含的，用户发给服务器都必须带/app)
    @OnOpen
    @MessageMapping("/broadcast")
    // @SendTo不加这个注解，默认转发给消息代理(加上前缀/topic，也是/topic/broadcast)。
    // 这个是重写消息代理的目的地为/topic/broadcast，即广播地址
    @SendTo("/topic/broadcast")
    public String broadcast(String msg, Principal principal) {
        log.info("broadcast接收到消息：" + msg);
        return msg;
    }

    /**
     * 广播模式，实现方法2
     */
    @MessageMapping("/broadcast2")
    public void broadcast2(String msg) {
        log.info("broadcast2接收到消息：" + msg);
        simpMessagingTemplate.convertAndSend("/topic/broadcast2", msg);
    }

    /**
     * 订阅模式
     * 用户发送请求，服务器直接返回数据，与HTTP类似，只不过这个是异步的
     */
    @SubscribeMapping("/subscribe/{id}")
    public Long subscribe(@DestinationVariable Long id) {
        log.info("subscribe接收到消息：" + id);
        return id;
    }

    /**
     * 用户模式
     */
    @MessageMapping("/one")
    // @SendToUser表示要将消息发送给指定的用户，会自动在消息目的地前补上"/user"前缀
    @SendToUser("/queue/one")
    public void sendByUser(String user, String msg) {
        simpMessagingTemplate.convertAndSendToUser(user, "/queue/one", msg);
        System.out.println("接收用户：" + user + "消息：" + msg);
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
