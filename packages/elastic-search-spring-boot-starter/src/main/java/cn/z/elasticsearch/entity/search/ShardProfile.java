package cn.z.elasticsearch.entity.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>分片配置</h1>
 *
 * <p>
 * createDate 2023/09/12 15:23:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ShardProfile {

    /**
     * 聚合配置
     */
    private List<AggregationProfile> aggregations;
    /**
     * ID
     */
    private String id;
    /**
     * 搜索配置
     */
    private List<SearchProfile> searches;
    /**
     * 抓取配置
     */
    private FetchProfile fetch;

    public ShardProfile() {
    }

    public ShardProfile(co.elastic.clients.elasticsearch.core.search.ShardProfile profile) {
        this.aggregations = AggregationProfile.toList(profile.aggregations());
        this.id = profile.id();
        this.searches = SearchProfile.toList(profile.searches());
        if (profile.fetch() != null) {
            this.fetch = new FetchProfile(profile.fetch());
        }
    }

    public static List<ShardProfile> toList(List<co.elastic.clients.elasticsearch.core.search.ShardProfile> profileList) {
        List<ShardProfile> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.ShardProfile profile : profileList) {
            list.add(new ShardProfile(profile));
        }
        return list;
    }

    public List<AggregationProfile> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<AggregationProfile> aggregations) {
        this.aggregations = aggregations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SearchProfile> getSearches() {
        return searches;
    }

    public void setSearches(List<SearchProfile> searches) {
        this.searches = searches;
    }

    public FetchProfile getFetch() {
        return fetch;
    }

    public void setFetch(FetchProfile fetch) {
        this.fetch = fetch;
    }

    @Override
    public String toString() {
        return "ShardProfile{" +
                "aggregations=" + aggregations +
                ", id='" + id + '\'' +
                ", searches=" + searches +
                ", fetch=" + fetch +
                '}';
    }

}
