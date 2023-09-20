package cn.z.tool.useragent;

import java.util.regex.Pattern;

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

    IPHONE("iPhone", "iphone", true),
    IPOD("iPod", "ipod", true),
    IPAD("iPad", "ipad", true),
    ANDROID("Android", "android", true),
    GOOGLE_TV("GoogleTV", "googletv", true),
    HTC_FLYER("HTC Flyer", "htc_flyer", true),
    XIAO_MI("XiaoMi", "(xiaomi|mi)", true),
    SYMBIAN("Symbian", "symbian", true),
    BLACK_BERRY("Blackberry", "blackberry", true),
    WINDOWS_PHONE("Windows Phone", "windows (ce|phone|mobile)", true),
    WINDOWS("Windows", "windows", false),
    MAC("Mac", "(macintosh|darwin)", false),
    LINUX("Linux", "linux", false),
    WII("Wii", "wii", false),
    PLAYSTATION("Playstation", "playstation", false),
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
     * 是移动端
     */
    private final boolean mobile;

    Platform(String name, String regex, boolean mobile) {
        this.name = name;
        this.regex = Pattern.compile(regex);
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public Pattern getRegex() {
        return regex;
    }

    public boolean isMobile() {
        return mobile;
    }

}
