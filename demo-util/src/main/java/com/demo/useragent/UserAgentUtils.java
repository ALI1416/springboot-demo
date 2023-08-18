package com.demo.useragent;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;

/**
 * <h1>UserAgent工具</h1>
 *
 * <p>
 * createDate 2023/08/17 16:08:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class UserAgentUtils {

    private UserAgentUtils() {
    }

    public static void main(String[] args) {
        log.info(parse("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " + //
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36").toString());
    }

    /**
     * 解析
     *
     * @param userAgentString UserAgent字符串
     * @return UserAgent
     */
    public static UserAgent parse(String userAgentString) {
        userAgentString = userAgentString.toLowerCase();
        UserAgent userAgent = new UserAgent();
        // 引擎
        String engineType = "";
        String engineVersion = "";
        String engineMajorVersion = "";
        for (Engine engine : Engine.values()) {
            Matcher matcherType = engine.getRegex().matcher(userAgentString);
            if (matcherType.find()) {
                engineType = engine.getName();
                Matcher matcherVersion = engine.getVersionRegex().matcher(userAgentString);
                if (matcherVersion.find()) {
                    engineVersion = matcherVersion.group(1);
                }
                break;
            }
        }
        if (!engineVersion.isEmpty()) {
            engineMajorVersion = engineVersion.split("\\.")[0];
        }
        userAgent.setEngine(engineType);
        userAgent.setEngineVersion(engineVersion);
        userAgent.setEngineMajorVersion(engineMajorVersion);
        // 浏览器
        // 操作系统
        // 平台
        // 是移动端
        return userAgent;
    }

}
