package cn.z.elasticsearch.entity.search;

import cn.z.elasticsearch.entity.ShardStatistics;
import co.elastic.clients.json.JsonData;

import java.util.List;
import java.util.Map;

/**
 * <h1>返回body</h1>
 *
 * <p>
 * createDate 2023/09/12 16:41:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ResponseBody<T> {

    /**
     * took
     */
    private long took;
    /**
     * 超时时间
     */
    private boolean timedOut;
    /**
     * 分片统计
     */
    private ShardStatistics shards;
    /**
     * 命中元数据
     */
    private HitsMetadata<T> hits;
    /**
     * 聚合
     */
    private Map<String, Aggregate> aggregations;
    /**
     * 群集统计信息
     */
    private ClusterStatistics clusters;
    /**
     * 字段
     */
    private Map<String, JsonData> fields;
    /**
     * 最大分数
     */
    private Double maxScore;
    /**
     * 数字减少阶段
     */
    private Long numReducePhases;
    /**
     * 配置
     */
    private Profile profile;
    /**
     * pit ID
     */
    private String pitId;
    /**
     * 滚动 ID
     */
    private String scrollId;
    /**
     * 建议
     */
    private Map<String, List<Suggestion<T>>> suggest;
    /**
     * 提前终止
     */
    private Boolean terminatedEarly;

    public ResponseBody() {
    }

    public ResponseBody(co.elastic.clients.elasticsearch.core.search.ResponseBody<T> body) {
        this.took = body.took();
        this.timedOut = body.timedOut();
        this.shards = new ShardStatistics(body.shards());
        this.hits = new HitsMetadata<>(body.hits());
        this.aggregations = Aggregate.toMap(body.aggregations());
        if (body.clusters() != null) {
            this.clusters = new ClusterStatistics(body.clusters());
        }
        this.fields = body.fields();
        this.maxScore = body.maxScore();
        this.numReducePhases = body.numReducePhases();
        if (body.profile() != null) {
            this.profile = new Profile(body.profile());
        }
        this.pitId = body.pitId();
        this.scrollId = body.scrollId();
        this.suggest = Suggestion.toMap(body.suggest());
        this.terminatedEarly = body.terminatedEarly();
    }

    public long getTook() {
        return took;
    }

    public void setTook(long took) {
        this.took = took;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    public ShardStatistics getShards() {
        return shards;
    }

    public void setShards(ShardStatistics shards) {
        this.shards = shards;
    }

    public HitsMetadata<T> getHits() {
        return hits;
    }

    public void setHits(HitsMetadata<T> hits) {
        this.hits = hits;
    }

    public Map<String, Aggregate> getAggregations() {
        return aggregations;
    }

    public void setAggregations(Map<String, Aggregate> aggregations) {
        this.aggregations = aggregations;
    }

    public ClusterStatistics getClusters() {
        return clusters;
    }

    public void setClusters(ClusterStatistics clusters) {
        this.clusters = clusters;
    }

    public Map<String, JsonData> getFields() {
        return fields;
    }

    public void setFields(Map<String, JsonData> fields) {
        this.fields = fields;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public Long getNumReducePhases() {
        return numReducePhases;
    }

    public void setNumReducePhases(Long numReducePhases) {
        this.numReducePhases = numReducePhases;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getPitId() {
        return pitId;
    }

    public void setPitId(String pitId) {
        this.pitId = pitId;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }

    public Map<String, List<Suggestion<T>>> getSuggest() {
        return suggest;
    }

    public void setSuggest(Map<String, List<Suggestion<T>>> suggest) {
        this.suggest = suggest;
    }

    public Boolean getTerminatedEarly() {
        return terminatedEarly;
    }

    public void setTerminatedEarly(Boolean terminatedEarly) {
        this.terminatedEarly = terminatedEarly;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "took=" + took +
                ", timedOut=" + timedOut +
                ", shards=" + shards +
                ", hits=" + hits +
                ", aggregations=" + aggregations +
                ", clusters=" + clusters +
                ", fields=" + fields +
                ", maxScore=" + maxScore +
                ", numReducePhases=" + numReducePhases +
                ", profile=" + profile +
                ", pitId='" + pitId + '\'' +
                ", scrollId='" + scrollId + '\'' +
                ", suggest=" + suggest +
                ", terminatedEarly=" + terminatedEarly +
                '}';
    }

}
