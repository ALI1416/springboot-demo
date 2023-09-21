package cn.z.elasticsearch.entity.search;


import java.util.ArrayList;
import java.util.List;

/**
 * <h1>搜索配置</h1>
 *
 * <p>
 * createDate 2023/09/12 15:11:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class SearchProfile {

    /**
     * 采集器
     */
    private List<Collector> collector;
    /**
     * 查询配置
     */
    private List<QueryProfile> query;
    /**
     * 重写时间
     */
    private long rewriteTime;

    public SearchProfile() {
    }

    public SearchProfile(co.elastic.clients.elasticsearch.core.search.SearchProfile profile) {
        this.collector = Collector.toList(profile.collector());
        this.query = QueryProfile.toList(profile.query());
        this.rewriteTime = profile.rewriteTime();
    }

    public static List<SearchProfile> toList(List<co.elastic.clients.elasticsearch.core.search.SearchProfile> profileList) {
        List<SearchProfile> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.SearchProfile profile : profileList) {
            list.add(new SearchProfile(profile));
        }
        return list;
    }

    public List<Collector> getCollector() {
        return collector;
    }

    public void setCollector(List<Collector> collector) {
        this.collector = collector;
    }

    public List<QueryProfile> getQuery() {
        return query;
    }

    public void setQuery(List<QueryProfile> query) {
        this.query = query;
    }

    public long getRewriteTime() {
        return rewriteTime;
    }

    public void setRewriteTime(long rewriteTime) {
        this.rewriteTime = rewriteTime;
    }

    @Override
    public String toString() {
        return "SearchProfile{" +
                "collector=" + collector +
                ", query=" + query +
                ", rewriteTime=" + rewriteTime +
                '}';
    }

}
