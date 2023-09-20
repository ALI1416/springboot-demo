package cn.z.tool.useragent;

import java.util.regex.Pattern;

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
    CHROME("Chrome", "chrome"),
    OPERA("Opera", "opera"),
    PRESTO("Presto", "presto"),
    GECKO("Gecko", "gecko"),
    KHTML("KHTML", "khtml"),
    KONQUEROR("Konqueror", "konqueror"),
    MIDP("MIDP", "midp"),
    ;

    /**
     * 名称
     */
    private final String name;
    /**
     * 匹配规则
     */
    private final Pattern regex;
    /**
     * 版本号匹配规则
     */
    private final Pattern versionRegex;

    Engine(String name, String regex) {
        this.name = name;
        this.regex = Pattern.compile(regex);
        this.versionRegex = Pattern.compile(regex + "[/\\- ]([\\w.\\-]+)");
    }

    public String getName() {
        return name;
    }

    public Pattern getRegex() {
        return regex;
    }

    public Pattern getVersionRegex() {
        return versionRegex;
    }

}
