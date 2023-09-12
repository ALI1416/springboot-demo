package com.demo.tool.entity.elasticsearch.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>抓取配置</h1>
 *
 * <p>
 * createDate 2023/09/12 15:18:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class FetchProfile {

    /**
     * 类型
     */
    private String type;
    /**
     * 描述
     */
    private String description;
    /**
     * 纳秒
     */
    private long timeInNanos;
    /**
     * 抓取配置细分
     */
    private FetchProfileBreakdown breakdown;
    /**
     * 抓起配置调试
     */
    private FetchProfileDebug debug;
    /**
     * 抓取配置子节点
     */
    private List<FetchProfile> children;

    public FetchProfile() {
    }

    public FetchProfile(co.elastic.clients.elasticsearch.core.search.FetchProfile profile) {
        this.type = profile.type();
        this.description = profile.description();
        this.timeInNanos = profile.timeInNanos();
        this.breakdown = new FetchProfileBreakdown(profile.breakdown());
        if (profile.debug() != null) {
            this.debug = new FetchProfileDebug(profile.debug());
        }
        this.children = FetchProfile.toList(profile.children());
    }

    public static List<FetchProfile> toList(List<co.elastic.clients.elasticsearch.core.search.FetchProfile> profileList) {
        List<FetchProfile> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.FetchProfile profile : profileList) {
            list.add(new FetchProfile(profile));
        }
        return list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimeInNanos() {
        return timeInNanos;
    }

    public void setTimeInNanos(long timeInNanos) {
        this.timeInNanos = timeInNanos;
    }

    public FetchProfileBreakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(FetchProfileBreakdown breakdown) {
        this.breakdown = breakdown;
    }

    public FetchProfileDebug getDebug() {
        return debug;
    }

    public void setDebug(FetchProfileDebug debug) {
        this.debug = debug;
    }

    public List<FetchProfile> getChildren() {
        return children;
    }

    public void setChildren(List<FetchProfile> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "FetchProfile{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", timeInNanos=" + timeInNanos +
                ", breakdown=" + breakdown +
                ", debug=" + debug +
                ", children=" + children +
                '}';
    }

}
