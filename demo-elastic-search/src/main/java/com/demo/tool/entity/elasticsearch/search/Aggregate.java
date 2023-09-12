package com.demo.tool.entity.elasticsearch.search;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>聚合</h1>
 *
 * <p>
 * createDate 2023/09/12 11:29:13
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Aggregate {

    /**
     * 类型
     */
    public enum Kind {

        /**
         * 邻接矩阵
         */
        AdjacencyMatrix("adjacency_matrix"),
        /**
         * 自动日期直方图
         */
        AutoDateHistogram("auto_date_histogram"),
        /**
         * 平均数
         */
        Avg("avg"),
        /**
         * 箱式图
         */
        BoxPlot("box_plot"),
        /**
         * 桶度量值
         */
        BucketMetricValue("bucket_metric_value"),
        /**
         * 基数
         */
        Cardinality("cardinality"),
        /**
         * 子节点
         */
        Children("children"),
        /**
         * 复合的
         */
        Composite("composite"),
        /**
         * 简单长值
         */
        SimpleLongValue("simple_long_value"),
        /**
         * 日期柱状图
         */
        DateHistogram("date_histogram"),
        /**
         * 日期范围
         */
        DateRange("date_range"),
        /**
         * 导数
         */
        Derivative("derivative"),
        /**
         * d条款
         */
        Dterms("dterms"),
        /**
         * 扩展统计数据
         */
        ExtendedStats("extended_stats"),
        /**
         * 扩展统计桶
         */
        ExtendedStatsBucket("extended_stats_bucket"),
        /**
         * 过滤器
         */
        Filter("filter"),
        /**
         * 多个过滤器
         */
        Filters("filters"),
        /**
         * 频繁项集
         */
        FrequentItemSets("frequent_item_sets"),
        /**
         * 地理边界
         */
        GeoBounds("geo_bounds"),
        /**
         * 地理中心
         */
        GeoCentroid("geo_centroid"),
        /**
         * 地理距离
         */
        GeoDistance("geo_distance"),
        /**
         * 地理散列网格
         */
        GeohashGrid("geohash_grid"),
        /**
         * 地理hex网格
         */
        GeohexGrid("geohex_grid"),
        /**
         * 地质界线
         */
        GeoLine("geo_line"),
        /**
         * 地理位网格
         */
        GeotileGrid("geotile_grid"),
        /**
         * 全局
         */
        Global("global"),
        /**
         * hdr百分比排名
         */
        HdrPercentileRanks("hdr_percentile_ranks"),
        /**
         * hdr百分比
         */
        HdrPercentiles("hdr_percentiles"),
        /**
         * 直方图
         */
        Histogram("histogram"),
        /**
         * 推断
         */
        Inference("inference"),
        /**
         * IP前缀
         */
        IpPrefix("ip_prefix"),
        /**
         * IP范围
         */
        IpRange("ip_range"),
        /**
         * lrare条款
         */
        Lrareterms("lrareterms"),
        /**
         * l条款
         */
        Lterms("lterms"),
        /**
         * 矩阵统计
         */
        MatrixStats("matrix_stats"),
        /**
         * 最大值
         */
        Max("max"),
        /**
         * 介质绝对偏差
         */
        MedianAbsoluteDeviation("median_absolute_deviation"),
        /**
         * 最小值
         */
        Min("min"),
        /**
         * 丢失
         */
        Missing("missing"),
        /**
         * 多个条款
         */
        MultiTerms("multi_terms"),
        /**
         * 嵌套
         */
        Nested("nested"),
        /**
         * 父节点
         */
        Parent("parent"),
        /**
         * 百分位数桶
         */
        PercentilesBucket("percentiles_bucket"),
        /**
         * 范围
         */
        Range("range"),
        /**
         * 比率
         */
        Rate("rate"),
        /**
         * 反向嵌套
         */
        ReverseNested("reverse_nested"),
        /**
         * 采样器
         */
        Sampler("sampler"),
        /**
         * 脚本化度量
         */
        ScriptedMetric("scripted_metric"),
        /**
         * sigl条款
         */
        Siglterms("siglterms"),
        /**
         * sigl条款
         */
        Sigsterms("siglterms"),
        /**
         * 简单值
         */
        SimpleValue("simple_value"),
        /**
         * 统计数据
         */
        Stats("stats"),
        /**
         * 统计数据捅
         */
        StatsBucket("stats_bucket"),
        /**
         * srare条款
         */
        Srareterms("srareterms"),
        /**
         * 字符串统计数据
         */
        StringStats("string_stats"),
        /**
         * s条款
         */
        Sterms("sterms"),
        /**
         * 总和
         */
        Sum("sum"),
        /**
         * t摘要百分比排名
         */
        TdigestPercentileRanks("tdigest_percentile_ranks"),
        /**
         * t摘要百分比
         */
        TdigestPercentiles("tdigest_percentiles"),
        /**
         * t测试
         */
        TTest("t_test"),
        /**
         * 头部命中
         */
        TopHits("top_hits"),
        /**
         * 头部指标
         */
        TopMetrics("top_metrics"),
        /**
         * umrare条款
         */
        Umrareterms("umrareterms"),
        /**
         * 未映射采样器
         */
        UnmappedSampler("unmapped_sampler"),
        /**
         * umsig条款
         */
        Umsigterms("umsigterms"),
        /**
         * um条款
         */
        Umterms("umterms"),
        /**
         * 值计数
         */
        ValueCount("value_count"),
        /**
         * 可变宽度直方图
         */
        VariableWidthHistogram("variable_width_histogram"),
        /**
         * 加权平均值
         */
        WeightedAvg("weighted_avg"),
        /**
         * 自定义
         */
        _Custom(null);

        private final String jsonValue;

        Kind(String jsonValue) {
            this.jsonValue = jsonValue;
        }

        public static Kind toEnum(co.elastic.clients.elasticsearch._types.aggregations.Aggregate.Kind kind) {
            switch (kind.name()) {
                case "AdjacencyMatrix":
                    return Kind.AdjacencyMatrix;
                case "AutoDateHistogram":
                    return Kind.AutoDateHistogram;
                case "Avg":
                    return Kind.Avg;
                case "BoxPlot":
                    return Kind.BoxPlot;
                case "BucketMetricValue":
                    return Kind.BucketMetricValue;
                case "Cardinality":
                    return Kind.Cardinality;
                case "Children":
                    return Kind.Children;
                case "Composite":
                    return Kind.Composite;
                case "SimpleLongValue":
                    return Kind.SimpleLongValue;
                case "DateHistogram":
                    return Kind.DateHistogram;
                case "DateRange":
                    return Kind.DateRange;
                case "Derivative":
                    return Kind.Derivative;
                case "Dterms":
                    return Kind.Dterms;
                case "ExtendedStats":
                    return Kind.ExtendedStats;
                case "ExtendedStatsBucket":
                    return Kind.ExtendedStatsBucket;
                case "Filter":
                    return Kind.Filter;
                case "Filters":
                    return Kind.Filters;
                case "FrequentItemSets":
                    return Kind.FrequentItemSets;
                case "GeoBounds":
                    return Kind.GeoBounds;
                case "GeoCentroid":
                    return Kind.GeoCentroid;
                case "GeoDistance":
                    return Kind.GeoDistance;
                case "GeohashGrid":
                    return Kind.GeohashGrid;
                case "GeohexGrid":
                    return Kind.GeohexGrid;
                case "GeoLine":
                    return Kind.GeoLine;
                case "GeotileGrid":
                    return Kind.GeotileGrid;
                case "Global":
                    return Kind.Global;
                case "HdrPercentileRanks":
                    return Kind.HdrPercentileRanks;
                case "HdrPercentiles":
                    return Kind.HdrPercentiles;
                case "Histogram":
                    return Kind.Histogram;
                case "Inference":
                    return Kind.Inference;
                case "IpPrefix":
                    return Kind.IpPrefix;
                case "IpRange":
                    return Kind.IpRange;
                case "Lrareterms":
                    return Kind.Lrareterms;
                case "Lterms":
                    return Kind.Lterms;
                case "MatrixStats":
                    return Kind.MatrixStats;
                case "Max":
                    return Kind.Max;
                case "MedianAbsoluteDeviation":
                    return Kind.MedianAbsoluteDeviation;
                case "Min":
                    return Kind.Min;
                case "Missing":
                    return Kind.Missing;
                case "MultiTerms":
                    return Kind.MultiTerms;
                case "Nested":
                    return Kind.Nested;
                case "Parent":
                    return Kind.Parent;
                case "PercentilesBucket":
                    return Kind.PercentilesBucket;
                case "Range":
                    return Kind.Range;
                case "Rate":
                    return Kind.Rate;
                case "ReverseNested":
                    return Kind.ReverseNested;
                case "Sampler":
                    return Kind.Sampler;
                case "ScriptedMetric":
                    return Kind.ScriptedMetric;
                case "Siglterms":
                    return Kind.Siglterms;
                case "Sigsterms":
                    return Kind.Sigsterms;
                case "SimpleValue":
                    return Kind.SimpleValue;
                case "Stats":
                    return Kind.Stats;
                case "StatsBucket":
                    return Kind.StatsBucket;
                case "Srareterms":
                    return Kind.Srareterms;
                case "StringStats":
                    return Kind.StringStats;
                case "Sterms":
                    return Kind.Sterms;
                case "Sum":
                    return Kind.Sum;
                case "TdigestPercentileRanks":
                    return Kind.TdigestPercentileRanks;
                case "TdigestPercentiles":
                    return Kind.TdigestPercentiles;
                case "TTest":
                    return Kind.TTest;
                case "TopHits":
                    return Kind.TopHits;
                case "TopMetrics":
                    return Kind.TopMetrics;
                case "Umrareterms":
                    return Kind.Umrareterms;
                case "UnmappedSampler":
                    return Kind.UnmappedSampler;
                case "Umsigterms":
                    return Kind.Umsigterms;
                case "Umterms":
                    return Kind.Umterms;
                case "ValueCount":
                    return Kind.ValueCount;
                case "VariableWidthHistogram":
                    return Kind.VariableWidthHistogram;
                case "WeightedAvg":
                    return Kind.WeightedAvg;
                case "_Custom":
                    return Kind._Custom;
                default:
                    return null;
            }
        }

        public String getJsonValue() {
            return jsonValue;
        }

        @Override
        public String toString() {
            return "Aggregate.Kind{" +
                    "jsonValue='" + jsonValue + '\'' +
                    '}';
        }

    }

    /**
     * 类型
     */
    private Aggregate.Kind kind;
    /**
     * 值
     */
    private Object value;

    public Aggregate() {
    }

    public Aggregate(co.elastic.clients.elasticsearch._types.aggregations.Aggregate aggregate) {
        this.kind = Aggregate.Kind.toEnum(aggregate._kind());
        this.value = aggregate._get();
    }

    public static Map<String, Aggregate> toMap(Map<String, co.elastic.clients.elasticsearch._types.aggregations.Aggregate> aggregateMap) {
        Map<String, Aggregate> map = new HashMap<>();
        aggregateMap.forEach((key, value) -> map.put(key, new Aggregate(value)));
        return map;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Aggregate{" +
                "kind=" + kind +
                ", value=" + value +
                '}';
    }

}
