package com.demo.useragent;

/**
 * <h1>平台</h1>
 *
 * <p>
 * createDate 2023/08/17 18:21:19
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum Platform {

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
    /**
     * 是移动端
     */
    private final boolean mobile;

    Platform(String name, String regex, String versionRegex, boolean mobile) {
        this.name = name;
        this.regex = regex;
        this.versionRegex = versionRegex;
        this.mobile = mobile;
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

    public boolean isMobile() {
        return mobile;
    }

}
