package com.demo.util;

import cn.hutool.http.useragent.UserAgentUtil;
import com.demo.util.pojo.UserAgentInfo;

/**
 * <h1>UserAgent工具类</h1>
 *
 * <p>
 * createDate 2021/01/15 11:56:41
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class UserAgentUtils {

    /**
     * 获取UserAgent信息
     *
     * @param userAgentString userAgentString
     */
    public static UserAgentInfo getUserAgentInfo(String userAgentString) {
        return new UserAgentInfo(UserAgentUtil.parse(userAgentString));
    }

}
