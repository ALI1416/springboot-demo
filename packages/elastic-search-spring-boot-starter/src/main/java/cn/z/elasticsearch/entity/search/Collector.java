package cn.z.elasticsearch.entity.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>采集器</h1>
 *
 * <p>
 * createDate 2023/09/12 14:45:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Collector {

    /**
     * 名称
     */
    private String name;
    /**
     * 原因
     */
    private String reason;
    /**
     * 纳秒
     */
    private long timeInNanos;
    /**
     * 采集器子节点
     */
    private List<Collector> children;

    public Collector() {
    }

    public Collector(co.elastic.clients.elasticsearch.core.search.Collector collector) {
        this.name = collector.name();
        this.reason = collector.reason();
        this.timeInNanos = collector.timeInNanos();
        this.children = Collector.toList(collector.children());
    }

    public static List<Collector> toList(List<co.elastic.clients.elasticsearch.core.search.Collector> collectorList) {
        List<Collector> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.Collector collector : collectorList) {
            list.add(new Collector(collector));
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getTimeInNanos() {
        return timeInNanos;
    }

    public void setTimeInNanos(long timeInNanos) {
        this.timeInNanos = timeInNanos;
    }

    public List<Collector> getChildren() {
        return children;
    }

    public void setChildren(List<Collector> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Collector{" +
                "name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                ", timeInNanos=" + timeInNanos +
                ", children=" + children +
                '}';
    }

}
