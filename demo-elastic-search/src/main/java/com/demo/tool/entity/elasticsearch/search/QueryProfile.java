package com.demo.tool.entity.elasticsearch.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>查询配置</h1>
 *
 * <p>
 * createDate 2023/09/12 15:06:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class QueryProfile {

    /**
     * 查询细分
     */
    private QueryBreakdown breakdown;
    /**
     * 描述
     */
    private String description;
    /**
     * 纳秒
     */
    private long timeInNanos;
    /**
     * 类型
     */
    private String type;
    /**
     * 查询配置子节点
     */
    private List<QueryProfile> children;

    public QueryProfile() {
    }

    public QueryProfile(co.elastic.clients.elasticsearch.core.search.QueryProfile profile) {
        this.breakdown = new QueryBreakdown(profile.breakdown());
        this.description = profile.description();
        this.timeInNanos = profile.timeInNanos();
        this.type = profile.type();
    }

    public static List<QueryProfile> toList(List<co.elastic.clients.elasticsearch.core.search.QueryProfile> profileList) {
        List<QueryProfile> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.QueryProfile profile : profileList) {
            list.add(new QueryProfile(profile));
        }
        return list;
    }

    public QueryBreakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(QueryBreakdown breakdown) {
        this.breakdown = breakdown;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QueryProfile> getChildren() {
        return children;
    }

    public void setChildren(List<QueryProfile> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "QueryProfile{" +
                "breakdown=" + breakdown +
                ", description='" + description + '\'' +
                ", timeInNanos=" + timeInNanos +
                ", type='" + type + '\'' +
                ", children=" + children +
                '}';
    }

}
