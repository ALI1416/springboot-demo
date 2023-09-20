package cn.z.tool.useragent;

import java.util.regex.Pattern;

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

    WINDOWS_10("Windows 10", "windows nt 10\\.0", "windows nt (10\\.0)"),
    WINDOWS_8_1("Windows 8.1", "windows nt 6\\.3", "windows nt (6\\.3)"),
    WINDOWS_8("Windows 8", "windows nt 6\\.2", "windows nt (6\\.2)"),
    WINDOWS_7("Windows 7", "windows nt 6\\.1", "windows nt (6\\.1)"),
    WINDOWS_VISTA("Windows Vista", "windows nt 6\\.0", "windows nt (6\\.0)"),
    WINDOWS_2003("Windows 2003", "windows nt 5\\.2", "windows nt (5\\.2)"),
    WINDOWS_XP("Windows XP", "windows nt 5\\.1", "windows nt (5\\.1)"),
    WINDOWS_2000("Windows 2000", "windows nt 5\\.0", "windows nt (5\\.0)"),
    WINDOWS_PHONE("Windows Phone", "windows (ce|phone|mobile)( os)?", "windows (ce|phone|mobile)( os)? ([\\w.\\-]+)"),
    WINDOWS("Windows", "windows", "windows nt ([\\w.\\-]+)"),
    OS_X("OS X", "os x", "os x ([\\w.\\-]+)"),
    ANDROID("Android", "android", "android ([\\w.\\-]+)"),
    XIAO_MI("XiaoMi", "(xiaomi|mi)", "(xiaomi|mi) \\s+ "),
    LINUX("Linux", "linux", "linux/([\\w.\\-]+)"),
    WII("Wii", "wii", "wii libnup/([\\w.\\-]+)"),
    PS3("PS3", "playstation 3", "playstation 3; ([\\w.\\-]+)"),
    PSP("PSP", "playstation portable", "portable\\); ([\\w.\\-]+)"),
    IPOD("iPod", "ipod", "ipod .*os ([\\w.\\-]+)"),
    IPAD("iPad", "ipad", "ipad .*os ([\\w.\\-]+)"),
    IPHONE("iPhone", "iphone", "iphone .*os ([\\w.\\-]+)"),
    SYMBIAN("Symbian", "symbian", "symbian(os)?/([\\w.\\-]+)"),
    DARWIN("Darwin", "darwin", "darwin/([\\w.\\-]+)"),
    ADOBE_AIR("Adobe Air", "adobeair", "adobeair/([\\w.\\-]+)"),
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

    Os(String name, String regex, String versionRegex) {
        this.name = name;
        this.regex = Pattern.compile(regex);
        this.versionRegex = Pattern.compile(versionRegex);
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
