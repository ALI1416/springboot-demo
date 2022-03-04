package com.demo.hutool.util;

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>工具</h1>
 *
 * <p>
 * createDate 2022/03/04 11:24:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        strUtil();
        hexUtil();
        escapeUtil();
    }

    /**
     * 字符串工具
     */
    private static void strUtil() {
        log.info("是否包含空字符串(仅null和空字符串):" + StrUtil.hasEmpty(""));
        log.info("是否包含空字符串(包括不可见字符):" + StrUtil.hasBlank(StrUtil.TAB));
        log.info("去除后缀:" + StrUtil.removeSuffix("123.jpg", ".jpg"));
        log.info("去除前缀:" + StrUtil.removeSuffix("123.jpg", "123"));
        log.info("字符串拼接:" + StrUtil.format("{}爱{}，就像老鼠爱大米", "我", "你"));
    }

    /**
     * 16进制工具
     */
    private static void hexUtil() {
        String s = "字符串abc123";
        String hex = HexUtil.encodeHexStr(s);
        log.info("hex:" + hex);
        log.info("hex解码:" + HexUtil.decodeHexStr(hex));
    }

    /**
     * 转义和反转义工具类
     */
    private static void escapeUtil() {
        String s = "字符串abc123 *@-_+./";
        String escape = EscapeUtil.escape(s);
        log.info("转义:" + escape);
        log.info("反转义:" + EscapeUtil.unescape(escape));
    }

}
