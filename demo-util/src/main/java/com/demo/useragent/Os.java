package com.demo.useragent;

/**
 * <h1>操作系统</h1>
 *
 * <p>
 * createDate 2023/08/17 18:19:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum Os {

    ;

    /**
     * 名称
     */
    private final String name;
    /**
     * 匹配规则
     */
    private final String regex;
    /**
     * 版本号匹配规则
     */
    private final String versionRegex;

    Os(String name, String regex, String versionRegex) {
        this.name = name;
        this.regex = regex;
        this.versionRegex = versionRegex;
    }

    public String getName() {
        return name;
    }

    public String getRegex() {
        return regex;
    }

    public String getVersionRegex() {
        return versionRegex;
    }

}
