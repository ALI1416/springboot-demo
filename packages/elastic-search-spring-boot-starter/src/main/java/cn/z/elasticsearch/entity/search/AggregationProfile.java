package cn.z.elasticsearch.entity.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>聚合配置</h1>
 *
 * <p>
 * createDate 2023/09/12 14:40:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AggregationProfile {

    /**
     * 聚合细分
     */
    private AggregationBreakdown breakdown;
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
     * 聚合配置调试
     */
    private AggregationProfileDebug debug;
    /**
     * 聚合配置调试子节点
     */
    private List<AggregationProfile> children;

    public AggregationProfile() {
    }

    public AggregationProfile(co.elastic.clients.elasticsearch.core.search.AggregationProfile profile) {
        this.breakdown = new AggregationBreakdown(profile.breakdown());
        this.timeInNanos = profile.timeInNanos();
        this.type = profile.type();
        if (profile.debug() != null) {
            this.debug = new AggregationProfileDebug(profile.debug());
        }
        this.children = AggregationProfile.toList(profile.children());
    }

    public static List<AggregationProfile> toList(List<co.elastic.clients.elasticsearch.core.search.AggregationProfile> profileList) {
        List<AggregationProfile> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.AggregationProfile profile : profileList) {
            list.add(new AggregationProfile(profile));
        }
        return list;
    }

    public AggregationBreakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(AggregationBreakdown breakdown) {
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

    public AggregationProfileDebug getDebug() {
        return debug;
    }

    public void setDebug(AggregationProfileDebug debug) {
        this.debug = debug;
    }

    public List<AggregationProfile> getChildren() {
        return children;
    }

    public void setChildren(List<AggregationProfile> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "AggregationProfile{" +
                "breakdown=" + breakdown +
                ", description='" + description + '\'' +
                ", timeInNanos=" + timeInNanos +
                ", type='" + type + '\'' +
                ", debug=" + debug +
                ", children=" + children +
                '}';
    }

}
