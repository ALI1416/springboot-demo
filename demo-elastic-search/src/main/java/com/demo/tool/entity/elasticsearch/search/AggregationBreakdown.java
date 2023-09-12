package com.demo.tool.entity.elasticsearch.search;

/**
 * <h1>聚合细分</h1>
 *
 * <p>
 * createDate 2023/09/12 14:10:44
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AggregationBreakdown {

    /**
     * 构建聚合
     */
    private long buildAggregation;
    /**
     * 构建聚合总数
     */
    private long buildAggregationCount;
    /**
     * 构建叶集合器
     */
    private long buildLeafCollector;
    /**
     * 构建叶集合器总数
     */
    private long buildLeafCollectorCount;
    /**
     * 集合
     */
    private long collect;
    /**
     * 集合总数
     */
    private long collectCount;
    /**
     * 初始化
     */
    private long initialize;
    /**
     * 初始化总数
     */
    private long initializeCount;
    /**
     * 后集合
     */
    private Long postCollection;
    /**
     * 后集合总数
     */
    private Long postCollectionCount;
    /**
     * 减少
     */
    private long reduce;
    /**
     * 减少总数
     */
    private long reduceCount;

    public AggregationBreakdown() {
    }

    public AggregationBreakdown(co.elastic.clients.elasticsearch.core.search.AggregationBreakdown breakdown) {
        this.buildAggregation = breakdown.buildAggregation();
        this.buildAggregationCount = breakdown.buildAggregationCount();
        this.buildLeafCollector = breakdown.buildLeafCollector();
        this.buildLeafCollectorCount = breakdown.buildLeafCollectorCount();
        this.collect = breakdown.collect();
        this.collectCount = breakdown.collectCount();
        this.initialize = breakdown.initialize();
        this.initializeCount = breakdown.initializeCount();
        this.postCollection = breakdown.postCollection();
        this.postCollectionCount = breakdown.postCollectionCount();
        this.reduce = breakdown.reduce();
        this.reduceCount = breakdown.reduceCount();
    }

    public long getBuildAggregation() {
        return buildAggregation;
    }

    public void setBuildAggregation(long buildAggregation) {
        this.buildAggregation = buildAggregation;
    }

    public long getBuildAggregationCount() {
        return buildAggregationCount;
    }

    public void setBuildAggregationCount(long buildAggregationCount) {
        this.buildAggregationCount = buildAggregationCount;
    }

    public long getBuildLeafCollector() {
        return buildLeafCollector;
    }

    public void setBuildLeafCollector(long buildLeafCollector) {
        this.buildLeafCollector = buildLeafCollector;
    }

    public long getBuildLeafCollectorCount() {
        return buildLeafCollectorCount;
    }

    public void setBuildLeafCollectorCount(long buildLeafCollectorCount) {
        this.buildLeafCollectorCount = buildLeafCollectorCount;
    }

    public long getCollect() {
        return collect;
    }

    public void setCollect(long collect) {
        this.collect = collect;
    }

    public long getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(long collectCount) {
        this.collectCount = collectCount;
    }

    public long getInitialize() {
        return initialize;
    }

    public void setInitialize(long initialize) {
        this.initialize = initialize;
    }

    public long getInitializeCount() {
        return initializeCount;
    }

    public void setInitializeCount(long initializeCount) {
        this.initializeCount = initializeCount;
    }

    public Long getPostCollection() {
        return postCollection;
    }

    public void setPostCollection(Long postCollection) {
        this.postCollection = postCollection;
    }

    public Long getPostCollectionCount() {
        return postCollectionCount;
    }

    public void setPostCollectionCount(Long postCollectionCount) {
        this.postCollectionCount = postCollectionCount;
    }

    public long getReduce() {
        return reduce;
    }

    public void setReduce(long reduce) {
        this.reduce = reduce;
    }

    public long getReduceCount() {
        return reduceCount;
    }

    public void setReduceCount(long reduceCount) {
        this.reduceCount = reduceCount;
    }

    @Override
    public String toString() {
        return "AggregationBreakdown{" +
                "buildAggregation=" + buildAggregation +
                ", buildAggregationCount=" + buildAggregationCount +
                ", buildLeafCollector=" + buildLeafCollector +
                ", buildLeafCollectorCount=" + buildLeafCollectorCount +
                ", collect=" + collect +
                ", collectCount=" + collectCount +
                ", initialize=" + initialize +
                ", initializeCount=" + initializeCount +
                ", postCollection=" + postCollection +
                ", postCollectionCount=" + postCollectionCount +
                ", reduce=" + reduce +
                ", reduceCount=" + reduceCount +
                '}';
    }

}
