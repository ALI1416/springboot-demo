package cn.z.tool.useragent;

import java.util.regex.Matcher;

/**
 * <h1>UserAgent</h1>
 *
 * <p>
 * 根据<a href="https://github.com/dromara/hutool">dromara/hutool</a>重构
 * </p>
 *
 * <p>
 * createDate 2023/08/18 17:44:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class UserAgent {

    /**
     * 引擎
     */
    private String engine;
    /**
     * 引擎
     */
    private String engineVersion;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 浏览器
     */
    private String browserVersion;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 操作系统
     */
    private String osVersion;
    /**
     * 平台
     */
    private String platform;
    /**
     * 是移动端
     */
    private Boolean isMobile;

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getMobile() {
        return isMobile;
    }

    public void setMobile(Boolean mobile) {
        isMobile = mobile;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "engine='" + engine + '\'' +
                ", engineVersion='" + engineVersion + '\'' +
                ", browser='" + browser + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", os='" + os + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", platform='" + platform + '\'' +
                ", isMobile=" + isMobile +
                '}';
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
        for (Engine engine : Engine.values()) {
            Matcher matcher = engine.getRegex().matcher(userAgentString);
            if (matcher.find()) {
                engineType = engine.getName();
                Matcher matcherVersion = engine.getVersionRegex().matcher(userAgentString);
                if (matcherVersion.find()) {
                    engineVersion = matcherVersion.group(matcherVersion.groupCount());
                }
                break;
            }
        }
        userAgent.setEngine(engineType);
        userAgent.setEngineVersion(engineVersion);
        // 浏览器
        String browserType = "";
        String browserVersion = "";
        boolean browserIsMobile = false;
        for (Browser browser : Browser.values()) {
            Matcher matcher = browser.getRegex().matcher(userAgentString);
            if (matcher.find()) {
                browserType = browser.getName();
                Matcher matcherVersion = browser.getVersionRegex().matcher(userAgentString);
                if (matcherVersion.find()) {
                    browserVersion = matcherVersion.group(matcherVersion.groupCount());
                }
                browserIsMobile = browser.isMobile();
                break;
            }
        }
        userAgent.setBrowser(browserType);
        userAgent.setBrowserVersion(browserVersion);
        // 操作系统
        String osType = "";
        String osVersion = "";
        for (Os os : Os.values()) {
            Matcher matcher = os.getRegex().matcher(userAgentString);
            if (matcher.find()) {
                osType = os.getName();
                Matcher matcherVersion = os.getVersionRegex().matcher(userAgentString);
                if (matcherVersion.find()) {
                    osVersion = matcherVersion.group(matcherVersion.groupCount());
                }
                break;
            }
        }
        userAgent.setOs(osType);
        userAgent.setOsVersion(osVersion);
        // 平台
        String platformType = "";
        boolean platformIsMobile = false;
        for (Platform platform : Platform.values()) {
            Matcher matcher = platform.getRegex().matcher(userAgentString);
            if (matcher.find()) {
                platformType = platform.getName();
                platformIsMobile = platform.isMobile();
                break;
            }
        }
        userAgent.setPlatform(platformType);
        // 是移动端
        userAgent.setMobile(browserIsMobile || platformIsMobile);
        return userAgent;
    }

}
