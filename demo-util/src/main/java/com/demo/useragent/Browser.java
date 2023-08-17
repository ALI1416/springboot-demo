package com.demo.useragent;

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

    WX_WORK("企业微信", "wxwork", "wxwork\\/([\\d\\w\\.\\-]+)", false),
    WE_CHAT("微信", "MicroMessenger", "MicroMessenger[\\/ ]([\\d\\w\\.\\-]+)", true),
    WE_CHAT_APP("微信小程序", "miniProgram", "miniProgram[\\/ ]([\\d\\w\\.\\-]+)", true),
    QQ("QQ", "MQQBrowser", "MQQBrowser\\/([\\d\\w\\.\\-]+)", false),
    DING_TALK("钉钉", "dingtalk-win", "DingTalk\\(([\\d\\w\\.\\-]+)\\)", false),
    DING_TALK_MOBILE("钉钉移动端", "DingTalk", "AliApp\\(DingTalk\\/([\\d\\w\\.\\-]+)\\)", true),
    ALIPAY("支付宝", "AlipayClient", "AliApp\\(AP\\/([\\d\\w\\.\\-]+)\\)", false),
    TAO_BAO("淘宝", "taobao", "AliApp\\(TB\\/([\\d\\w\\.\\-]+)\\)", false),
    UC("UC", "UC?Browser", "UC?Browser\\/([\\d\\w\\.\\-]+)", false),
    XIAO_MI("小米", "MiuiBrowser|mibrowser", "MiuiBrowser\\/([\\d\\w\\.\\-]+)", false),
    QUARK("夸克", "Quark", "Quark[\\/ ]([\\d\\w\\.\\-]+)", false),
    LENOVO("联想", "SLBrowser", "SLBrowser/([\\d\\w\\.\\-]+)", false),
    EDGE("Edge", "Edge|Edg", "(?:edge|Edg|EdgA)\\/([\\d\\w\\.\\-]+)", false),
    CHROME("谷歌", "chrome|(iphone.*crios.*safari)", "(?:Chrome|CriOS)\\/([\\d\\w\\.\\-]+)", false),
    FIREFOX("火狐", "firefox", "Firefox[\\/ ]([\\d\\w\\.\\-]+)", false),
    IE_MOBILE("IE移动端", "iemobile", "IEMobile[\\/ ]([\\d\\w\\.\\-]+)", true),
    ANDROID("安卓", "android", "version\\/([\\d\\w\\.\\-]+)", true),
    SAFARI("苹果", "safari", "version\\/([\\d\\w\\.\\-]+)", false),
    OPERA("欧朋", "opera", "Opera[\\/ ]([\\d\\w\\.\\-]+)", false),
    KONQUEROR("konqueror", "konqueror", "Thunderbird[\\/ ]([\\d\\w\\.\\-]+)", false),
    PS3("PS3", "playstation 3", "([\\d\\w\\.\\-]+)\\)\\s*$", false),
    PSP("PSP", "playstation portable", "([\\d\\w\\.\\-]+)\\)?\\s*$", true),
    LOTUS_NOTES("Lotus Notes", "lotus.notes", "Lotus-Notes\\/([\\w.]+)", false),
    THUNDERBIRD("Thunderbird", "thunderbird", "Thunderbird[\\/ ]([\\d\\w\\.\\-]+)", false),
    NETSCAPE("网景", "netscape", "Netscape[\\/ ]([\\d\\w\\.\\-]+)", false),
    SEA_MONKEY("Sea Monkey", "seamonkey", "Seamonkey[\\/ ]([\\d\\w\\.\\-]+)", false),
    OUTLOOK("Outlook", "microsoft.outlook", "Outlook[\\/ ]([\\d\\w\\.\\-]+)", false),
    EVOLUTION("evolution", "evolution", "Evolution[\\/ ]([\\d\\w\\.\\-]+)", false),
    IE("IE", "msie", "msie ([\\d\\w\\.\\-]+)", false),
    IE11("IE11", "rv:11", "rv:([\\d\\w\\.\\-]+)", false),
    YAMMER("Yammer", "AdobeAir", "([\\d\\w\\.\\-]+)\\/Yammer", false),
    YAMMER_MOBILE("Yammer移动端", "Yammer[\\s]+([\\d\\w\\.\\-]+)", "Yammer[\\s]+([\\d\\w\\.\\-]+)", true),
    BLACK_BERRY("黑莓", "BlackBerry", "BlackBerry[\\d]+\\/([\\d\\w\\.\\-]+", false),
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

    Browser(String name, String regex, String versionRegex, boolean mobile) {
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
