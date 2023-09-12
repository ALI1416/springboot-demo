package com.demo.tool.entity.elasticsearch.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>聚合配置代理调试拦截器</h1>
 *
 * <p>
 * createDate 2023/09/12 14:17:46
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AggregationProfileDelegateDebugFilter {

    /**
     * 结果来自元数据
     */
    private Integer resultsFromMetadata;
    /**
     * 查询
     */
    private String query;
    /**
     * 专门为
     */
    private String specializedFor;
    /**
     * 分段以恒定时间计数
     */
    private Integer segmentsCountedInConstantTime;

    public AggregationProfileDelegateDebugFilter() {
    }

    public AggregationProfileDelegateDebugFilter(co.elastic.clients.elasticsearch.core.search.AggregationProfileDelegateDebugFilter filter) {
        this.resultsFromMetadata = filter.resultsFromMetadata();
        this.query = filter.query();
        this.specializedFor = filter.specializedFor();
        this.segmentsCountedInConstantTime = filter.segmentsCountedInConstantTime();
    }

    public static List<AggregationProfileDelegateDebugFilter> toList(List<co.elastic.clients.elasticsearch.core.search.AggregationProfileDelegateDebugFilter> filterList) {
        List<AggregationProfileDelegateDebugFilter> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.AggregationProfileDelegateDebugFilter filter : filterList) {
            list.add(new AggregationProfileDelegateDebugFilter(filter));
        }
        return list;
    }

    public Integer getResultsFromMetadata() {
        return resultsFromMetadata;
    }

    public void setResultsFromMetadata(Integer resultsFromMetadata) {
        this.resultsFromMetadata = resultsFromMetadata;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSpecializedFor() {
        return specializedFor;
    }

    public void setSpecializedFor(String specializedFor) {
        this.specializedFor = specializedFor;
    }

    public Integer getSegmentsCountedInConstantTime() {
        return segmentsCountedInConstantTime;
    }

    public void setSegmentsCountedInConstantTime(Integer segmentsCountedInConstantTime) {
        this.segmentsCountedInConstantTime = segmentsCountedInConstantTime;
    }

    @Override
    public String toString() {
        return "AggregationProfileDelegateDebugFilter{" +
                "resultsFromMetadata=" + resultsFromMetadata +
                ", query='" + query + '\'' +
                ", specializedFor='" + specializedFor + '\'' +
                ", segmentsCountedInConstantTime=" + segmentsCountedInConstantTime +
                '}';
    }

}
