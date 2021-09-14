package com.demo.constant;

/**
 * <h1>普通常量</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Constant {

    /**
     * 部署网站{@value}
     */
    public final static String URL = "http://404z.cn:8080/";

    /**
     * QQ_APP_ID{@value}
     */
    public final static String QQ_APP_ID = "101925994";
    /**
     * QQ_APP_KEY{@value}
     */
    public final static String QQ_APP_KEY = "34ff276c1a1fbf0f32648796c04aaafb";
    /**
     * QQ_CALLBACK_URL{@value}
     */
    public final static String QQ_CALLBACK_URL = URL + "thirdLogin/qq/callback";

    /**
     * 分页-默认页码{@value}
     */
    public final static int PAGE_DEFAULT_PAGES = 1;
    /**
     * 分页-默认每页条数{@value}
     */
    public final static int PAGE_DEFAULT_ROWS = 5;
    /**
     * 分页-默认排序{@value}
     */
    public final static String PAGE_DEFAULT_ORDER_BY = "id desc";

    /**
     * 启用表备份{@value}
     */
    public final static boolean ENABLE_BAK = true;
    /**
     * 启用表日志{@value}
     */
    public final static boolean ENABLE_LOG = true;

}
