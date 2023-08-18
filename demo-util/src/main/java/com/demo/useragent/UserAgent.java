package com.demo.useragent;

/**
 * <h1>UserAgent</h1>
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
     * 引擎
     */
    private String engineMajorVersion;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 浏览器
     */
    private String browserVersion;
    /**
     * 浏览器
     */
    private String browserMajorVersion;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 操作系统
     */
    private String osVersion;
    /**
     * 操作系统
     */
    private String osMajorVersion;
    /**
     * 平台
     */
    private String platform;
    /**
     * 是移动端
     */
    private boolean mobile;

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

    public String getEngineMajorVersion() {
        return engineMajorVersion;
    }

    public void setEngineMajorVersion(String engineMajorVersion) {
        this.engineMajorVersion = engineMajorVersion;
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

    public String getBrowserMajorVersion() {
        return browserMajorVersion;
    }

    public void setBrowserMajorVersion(String browserMajorVersion) {
        this.browserMajorVersion = browserMajorVersion;
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

    public String getOsMajorVersion() {
        return osMajorVersion;
    }

    public void setOsMajorVersion(String osMajorVersion) {
        this.osMajorVersion = osMajorVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "engine='" + engine + '\'' +
                ", engineVersion='" + engineVersion + '\'' +
                ", engineMajorVersion='" + engineMajorVersion + '\'' +
                ", browser='" + browser + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", browserMajorVersion='" + browserMajorVersion + '\'' +
                ", os='" + os + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", osMajorVersion='" + osMajorVersion + '\'' +
                ", platform='" + platform + '\'' +
                ", mobile=" + mobile +
                '}';
    }

}
