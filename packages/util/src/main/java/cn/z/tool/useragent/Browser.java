package cn.z.tool.useragent;

import java.util.regex.Pattern;

/**
 * <h1>浏览器</h1>
 *
 * <p>
 * createDate 2023/08/17 16:10:22
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum Browser {

    WE_CHAT_WORK("WeChat Work", "wxwork", "wxwork/([\\w.\\-]+)", false),
    WE_CHAT("WeChat", "micromessenger", "micromessenger/([\\w.\\-]+)", true),
    WE_CHAT_APP("WeChat App", "miniprogram", "miniprogram/([\\w.\\-]+)", true),
    QQ("QQ", "mqqbrowser", "mqqbrowser/([\\w.\\-]+)", false),
    DING_TALK("DingTalk", "dingtalk-win", "dingtalk\\(([\\w.\\-]+)\\)", false),
    DING_TALK_MOBILE("DingTalk Mobile", "dingtalk", "aliapp\\(dingtalk/([\\w.\\-]+)\\)", true),
    ALIPAY("Alipay", "alipayclient", "aliapp\\(ap/([\\w.\\-]+)\\)", true),
    TAO_BAO("TaoBao", "taobao", "aliapp\\(tb/([\\w.\\-]+)\\)", true),
    BAI_DU("BaiDu", "baidubrowser", "baidubrowser/([\\w.\\-]+)", false),
    UC("UC", "ubrowser", "ubrowser/([\\w.\\-]+)", false),
    XIAO_MI("XiaoMi", "miuibrowser", "miuibrowser/([\\w.\\-]+)", false),
    QUARK("Quark", "quark", "quark/([\\w.\\-]+)", false),
    LENOVO("Lenovo", "slbrowser", "slbrowser/([\\w.\\-]+)", false),
    EDGE("Edge", "(edge|edg)", "(edge|edg)/([\\w.\\-]+)", false),
    CHROME("Chrome", "(chrome|crios)", "(chrome|crios)/([\\w.\\-]+)", false),
    FIREFOX("Firefox", "firefox", "firefox/([\\w.\\-]+)", false),
    IE_MOBILE("IE Mobile", "iemobile", "iemobile/([\\w.\\-]+)", true),
    ANDROID("Android", "android", "version/([\\w.\\-]+)", true),
    SAFARI("Safari", "safari", "version/([\\w.\\-]+)", false),
    OPERA("Opera", "opera", "opera/([\\w.\\-]+)", false),
    KONQUEROR("Konqueror", "konqueror", "konqueror/([\\w.\\-]+)", false),
    PS3("PS3", "playstation 3", "([\\w.\\-]+)\\)", false),
    PSP("PSP", "playstation portable", "([\\w.\\-]+)\\)", true),
    LOTUS("Lotus", "lotus.notes", "lotus-notes/([\\w.]+)", false),
    THUNDERBIRD("Thunderbird", "thunderbird", "thunderbird/([\\w.\\-]+)", false),
    NETSCAPE("Netscape", "netscape", "netscape/([\\w.\\-]+)", false),
    SEA_MONKEY("SeaMonkey", "seamonkey", "seamonkey/([\\w.\\-]+)", false),
    OUTLOOK("Outlook", "microsoft.outlook", "outlook/([\\w.\\-]+)", false),
    EVOLUTION("Evolution", "evolution", "evolution/([\\w.\\-]+)", false),
    IE("IE", "msie", "msie ([\\w.\\-]+)", false),
    IE11("IE 11", "rv:11", "rv:([\\w.\\-]+)", false),
    YAMMER("Yammer", "adobeair", "([\\w.\\-]+)/yammer", false),
    YAMMER_MOBILE("Yammer Mobile", "yammer ([\\w.\\-]+)", "yammer ([\\w.\\-]+)", true),
    BLACK_BERRY("BlackBerry", "blackberry", "blackberry\\d+/([\\w.\\-]+)", false),
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
    /**
     * 是移动端
     */
    private final boolean mobile;

    Browser(String name, String regex, String versionRegex, boolean mobile) {
        this.name = name;
        this.regex = Pattern.compile(regex);
        this.versionRegex = Pattern.compile(versionRegex);
        this.mobile = mobile;
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

    public boolean isMobile() {
        return mobile;
    }

}
