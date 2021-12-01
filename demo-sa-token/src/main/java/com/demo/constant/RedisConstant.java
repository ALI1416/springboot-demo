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
     * 路由前缀{@value}
     */
    public static final String ROUTE_PREFIX = "route:";
    /**
     * 路由-不拦截{@value}
     */
    public static final String ROUTE_NOT_INTERCEPT = ROUTE_PREFIX + "not-intercept";
    /**
     * 路由-所有"匹配路径"{@value}
     */
    public static final String ROUTE_MATCHER = ROUTE_PREFIX + "matcher";
    /**
     * 路由-所有"不可匹配路径"{@value}
     */
    public static final String ROUTE_DIRECT = ROUTE_PREFIX + "direct";
    /**
     * 路由-用户前缀{@value}
     */
    public static final String ROUTE_USER_PREFIX = ROUTE_PREFIX + "user:";
    /**
     * 路由-角色前缀{@value}
     */
    public static final String ROUTE_ROLE_PREFIX = ROUTE_PREFIX + "role:";
    /**
     * 路由-"匹配路径"后缀{@value}
     */
    public static final String ROUTE_MATCHER_SUFFIX = ":matcher";
    /**
     * 路由-"不可匹配路径"后缀{@value}
     */
    public static final String ROUTE_DIRECT_SUFFIX = ":direct";
    /**
     * 路由失效时间(秒){@value}
     */
    public static final Integer ROUTE_EXPIRE = 2 * 60 * 60;

}
