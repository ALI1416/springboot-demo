package com.demo.constant;

/**
 * <h1>RedisConstant</h1>
 *
 * <p>
 * createDate 2021/11/30 09:22:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class RedisConstant {

    /**
     * 路由字段名{@value}
     */
    public static final String ROUTE = "route";
    /**
     * 路由-可匹配路径字段名{@value}
     */
    public static final String MATCHER = "matcher";
    /**
     * 路由-不可匹配路径字段名{@value}
     */
    public static final String DIRECT = "direct";
    /**
     * 路由失效时间(秒){@value}
     */
    public static final Integer ROUTE_EXPIRE = 2 * 60 * 60;

    /**
     * 角色字段名{@value}
     */
    public static final String ROLE = "role";
    /**
     * 角色失效时间(秒){@value}
     */
    public static final Integer ROLE_EXPIRE = 2 * 60 * 60;

}
