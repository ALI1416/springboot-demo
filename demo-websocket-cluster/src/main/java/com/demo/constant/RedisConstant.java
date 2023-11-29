package com.demo.constant;

/**
 * <h1>Redis常量</h1>
 *
 * <p>
 * createDate 2021/11/30 09:22:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class RedisConstant {

    private RedisConstant() {
    }

    /* WebSocket */
    // ws: WebSocket
    //   broadcast: 广播模式
    //     [主题]
    //   user: 用户模式
    //     [主题]:
    //       [用户名]

    /**
     * WebSocket前缀{@value}
     */
    public static final String WS_PREFIX = "ws:";
    /**
     * WebSocket广播模式前缀{@value}
     */
    public static final String WS_BROADCAST_PREFIX = WS_PREFIX + "broadcast:";
    /**
     * WebSocket用户模式前缀{@value}
     */
    public static final String WS_USER_PREFIX = WS_PREFIX + "user:";

}
