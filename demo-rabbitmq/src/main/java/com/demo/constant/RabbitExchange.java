package com.demo.constant;

/**
 * <h1>Rabbit交换机</h1>
 *
 * <p>
 * createDate 2023/09/19 11:47:21
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class RabbitExchange {

    private RabbitExchange() {
    }

    /**
     * 广播
     */
    public static final String BROADCAST = "broadcast";
    /**
     * 路由
     */
    public static final String ROUTE = "route";
    /**
     * 动态路由
     */
    public static final String DYNAMIC_ROUTE = "dynamicRoute";

}
