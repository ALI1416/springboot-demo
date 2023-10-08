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

    /**
     * 路由-前缀{@value}
     */
    public static final String ROUTE_PREFIX = "route:";
    /**
     * 路由-所有"匹配路径"{@value}
     */
    public static final String ROUTE_MATCHER = ROUTE_PREFIX + "matcher";
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
    public static final String ROUTE_MATCHER_SUFFIX = ":matcher";
    /**
     * 路由-"直接路径"后缀{@value}
     */
    public static final String ROUTE_DIRECT_SUFFIX = ":direct";
    /**
     * 路由-失效时间(秒){@value}
     */
    public static final Integer ROUTE_EXPIRE = 2 * 60 * 60;

}
