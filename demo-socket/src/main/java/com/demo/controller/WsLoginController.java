package com.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>WsLoginController</h1>
 *
 * <p>
 * createDate 2021/12/16 16:12:35
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
// @Component
// @ServerEndpoint("/ws/user/queue/{nickname}")
@Slf4j
public class WsLoginController {

    private static final ConcurrentHashMap<String, Session> SESSION_POOL = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "nickname") String nickname) {
        SESSION_POOL.put(nickname, session);
        log.info("连接建立");
    }

    @OnClose
    public void onClose(@PathParam(value = "nickname") String nickname) {
        SESSION_POOL.remove(nickname);
        log.info("连接关闭");
    }

    public static ConcurrentHashMap<String, Session> getSessionPool() {
        return SESSION_POOL;
    }


}
