package com.demo.useragent;

/**
 * <h1>引擎</h1>
 *
 * <p>
 * createDate 2023/08/17 17:33:19
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum Engine {

    TRIDENT("Trident", "trident"),
    WEBKIT("Webkit", "webkit"),
    CHROME("谷歌", "chrome"),
    OPERA("欧朋", "opera"),
    PRESTO("Presto", "presto"),
    GECKO("Gecko", "gecko"),
    KHTML("KHTML", "khtml"),
    KONQUEROR("Konqueror", "konqueror"),
    MIDP("MIDP", "MIDP"),
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

    Engine(String name, String regex) {
        this.name = name;
        this.regex = regex;
        this.versionRegex = regex + "[/\\- ]([\\d\\w.\\-]+)";
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
