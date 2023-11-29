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

    /* 路由 */
    // route: 路由
    //   match 所有"匹配路径"
    //   direct 所有"直接路径"
    //   user: 用户
    //     [用户ID]
    //       :match 用户"匹配路径"
    //       :direct 用户"直接路径"
    //   role: 角色
    //     [角色ID]
    //       :match 角色"匹配路径"
    //       :direct 角色"直接路径"

    /**
     * 路由-前缀{@value}
     */
    public static final String ROUTE_PREFIX = "route:";
    /**
     * 路由-所有"匹配路径"{@value}
     */
    public static final String ROUTE_MATCH = ROUTE_PREFIX + "match";
    /**
     * 路由-所有"直接路径"{@value}
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
    public static final String ROUTE_MATCH_SUFFIX = ":match";
    /**
     * 路由-"直接路径"后缀{@value}
     */
    public static final String ROUTE_DIRECT_SUFFIX = ":direct";
    /**
     * 路由-失效时间(秒)(2小时){@value}
     */
    public static final int ROUTE_EXPIRE = 2 * 60 * 60;

    /* 更新 */
    // update: 更新
    //   routeNotIntercept 路由不拦截

    /**
     * 更新前缀{@value}
     */
    public static final String UPDATE_PREFIX = "update:";
    /**
     * 路由不拦截-更新{@value}
     */
    public static final String UPDATE_ROUTE_NOT_INTERCEPT = UPDATE_PREFIX + "routeNotIntercept";
    /**
     * 更新cron(2小时){@value}
     */
    public static final String UPDATE_CRON = "0 0 0/2 * * *";

}
