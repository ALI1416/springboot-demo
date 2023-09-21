package cn.z.elasticsearch.entity.search;


import java.util.List;

/**
 * <h1>聚合配置调试</h1>
 *
 * <p>
 * createDate 2023/09/12 14:20:31
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AggregationProfileDebug {

    /**
     * 具有多值订单的分段
     */
    private Integer segmentsWithMultiValuedOrds;
    /**
     * 集合策略
     */
    private String collectionStrategy;
    /**
     * 具有单值订单的分段
     */
    private Integer segmentsWithSingleValuedOrds;
    /**
     * 总共桶数
     */
    private Integer totalBuckets;
    /**
     * 构建桶数
     */
    private Integer builtBuckets;
    /**
     * 结果策略
     */
    private String resultStrategy;
    /**
     * 使用拦截器
     */
    private Boolean hasFilter;
    /**
     * 代理
     */
    private String delegate;
    /**
     * 聚合配置代理调试
     */
    private AggregationProfileDebug delegateDebug;
    /**
     * 捕获字符
     */
    private Integer charsFetched;
    /**
     * 提取次数
     */
    private Integer extractCount;
    /**
     * 提取ns
     */
    private Integer extractNs;
    /**
     * 捕获值
     */
    private Integer valuesFetched;
    /**
     * 收集分析ns
     */
    private Integer collectAnalyzedNs;
    /**
     * 收集分析次数
     */
    private Integer collectAnalyzedCount;
    /**
     * 幸存桶数
     */
    private Integer survivingBuckets;
    /**
     * 使用普通收集器
     */
    private Integer ordinalsCollectorsUsed;
    /**
     * 普通收集器开销过高
     */
    private Integer ordinalsCollectorsOverheadTooHigh;
    /**
     * 使用字符串哈希收集器
     */
    private Integer stringHashingCollectorsUsed;
    /**
     * 使用数字收集器
     */
    private Integer numericCollectorsUsed;
    /**
     * 使用空收集器
     */
    private Integer emptyCollectorsUsed;
    /**
     * 延迟聚合器
     */
    private List<String> deferredAggregators;
    /**
     * 文档次数字段分段
     */
    private Integer segmentsWithDocCountField;
    /**
     * 已删除文档分段
     */
    private Integer segmentsWithDeletedDocs;
    /**
     * 聚合配置代理调试拦截器
     */
    private List<AggregationProfileDelegateDebugFilter> filters;
    /**
     * 分段次数
     */
    private Integer segmentsCounted;
    /**
     * 分段收集器
     */
    private Integer segmentsCollected;
    /**
     * map还原器
     */
    private String mapReducer;

    public AggregationProfileDebug() {
    }

    public AggregationProfileDebug(co.elastic.clients.elasticsearch.core.search.AggregationProfileDebug debug) {
        this.segmentsWithMultiValuedOrds = debug.segmentsWithMultiValuedOrds();
        this.collectionStrategy = debug.collectionStrategy();
        this.segmentsWithSingleValuedOrds = debug.segmentsWithSingleValuedOrds();
        this.totalBuckets = debug.totalBuckets();
        this.builtBuckets = debug.builtBuckets();
        this.resultStrategy = debug.resultStrategy();
        this.hasFilter = debug.hasFilter();
        this.delegate = debug.delegate();
        if (debug.delegateDebug() != null) {
            this.delegateDebug = new AggregationProfileDebug(debug.delegateDebug());
        }
        this.charsFetched = debug.charsFetched();
        this.extractCount = debug.extractCount();
        this.extractNs = debug.extractNs();
        this.valuesFetched = debug.valuesFetched();
        this.collectAnalyzedNs = debug.collectAnalyzedNs();
        this.collectAnalyzedCount = debug.collectAnalyzedCount();
        this.survivingBuckets = debug.survivingBuckets();
        this.ordinalsCollectorsUsed = debug.ordinalsCollectorsUsed();
        this.ordinalsCollectorsOverheadTooHigh = debug.ordinalsCollectorsOverheadTooHigh();
        this.numericCollectorsUsed = debug.numericCollectorsUsed();
        this.emptyCollectorsUsed = debug.emptyCollectorsUsed();
        this.deferredAggregators = debug.deferredAggregators();
        this.segmentsWithDocCountField = debug.segmentsWithDocCountField();
        this.segmentsWithDeletedDocs = debug.segmentsWithDeletedDocs();
        this.filters = AggregationProfileDelegateDebugFilter.toList(debug.filters());
        this.segmentsCounted = debug.segmentsCounted();
        this.segmentsCollected = debug.segmentsCollected();
        this.mapReducer = debug.mapReducer();
    }

    public Integer getSegmentsWithMultiValuedOrds() {
        return segmentsWithMultiValuedOrds;
    }

    public void setSegmentsWithMultiValuedOrds(Integer segmentsWithMultiValuedOrds) {
        this.segmentsWithMultiValuedOrds = segmentsWithMultiValuedOrds;
    }

    public String getCollectionStrategy() {
        return collectionStrategy;
    }

    public void setCollectionStrategy(String collectionStrategy) {
        this.collectionStrategy = collectionStrategy;
    }

    public Integer getSegmentsWithSingleValuedOrds() {
        return segmentsWithSingleValuedOrds;
    }

    public void setSegmentsWithSingleValuedOrds(Integer segmentsWithSingleValuedOrds) {
        this.segmentsWithSingleValuedOrds = segmentsWithSingleValuedOrds;
    }

    public Integer getTotalBuckets() {
        return totalBuckets;
    }

    public void setTotalBuckets(Integer totalBuckets) {
        this.totalBuckets = totalBuckets;
    }

    public Integer getBuiltBuckets() {
        return builtBuckets;
    }

    public void setBuiltBuckets(Integer builtBuckets) {
        this.builtBuckets = builtBuckets;
    }

    public String getResultStrategy() {
        return resultStrategy;
    }

    public void setResultStrategy(String resultStrategy) {
        this.resultStrategy = resultStrategy;
    }

    public Boolean getHasFilter() {
        return hasFilter;
    }

    public void setHasFilter(Boolean hasFilter) {
        this.hasFilter = hasFilter;
    }

    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }

    public AggregationProfileDebug getDelegateDebug() {
        return delegateDebug;
    }

    public void setDelegateDebug(AggregationProfileDebug delegateDebug) {
        this.delegateDebug = delegateDebug;
    }

    public Integer getCharsFetched() {
        return charsFetched;
    }

    public void setCharsFetched(Integer charsFetched) {
        this.charsFetched = charsFetched;
    }

    public Integer getExtractCount() {
        return extractCount;
    }

    public void setExtractCount(Integer extractCount) {
        this.extractCount = extractCount;
    }

    public Integer getExtractNs() {
        return extractNs;
    }

    public void setExtractNs(Integer extractNs) {
        this.extractNs = extractNs;
    }

    public Integer getValuesFetched() {
        return valuesFetched;
    }

    public void setValuesFetched(Integer valuesFetched) {
        this.valuesFetched = valuesFetched;
    }

    public Integer getCollectAnalyzedNs() {
        return collectAnalyzedNs;
    }

    public void setCollectAnalyzedNs(Integer collectAnalyzedNs) {
        this.collectAnalyzedNs = collectAnalyzedNs;
    }

    public Integer getCollectAnalyzedCount() {
        return collectAnalyzedCount;
    }

    public void setCollectAnalyzedCount(Integer collectAnalyzedCount) {
        this.collectAnalyzedCount = collectAnalyzedCount;
    }

    public Integer getSurvivingBuckets() {
        return survivingBuckets;
    }

    public void setSurvivingBuckets(Integer survivingBuckets) {
        this.survivingBuckets = survivingBuckets;
    }

    public Integer getOrdinalsCollectorsUsed() {
        return ordinalsCollectorsUsed;
    }

    public void setOrdinalsCollectorsUsed(Integer ordinalsCollectorsUsed) {
        this.ordinalsCollectorsUsed = ordinalsCollectorsUsed;
    }

    public Integer getOrdinalsCollectorsOverheadTooHigh() {
        return ordinalsCollectorsOverheadTooHigh;
    }

    public void setOrdinalsCollectorsOverheadTooHigh(Integer ordinalsCollectorsOverheadTooHigh) {
        this.ordinalsCollectorsOverheadTooHigh = ordinalsCollectorsOverheadTooHigh;
    }

    public Integer getStringHashingCollectorsUsed() {
        return stringHashingCollectorsUsed;
    }

    public void setStringHashingCollectorsUsed(Integer stringHashingCollectorsUsed) {
        this.stringHashingCollectorsUsed = stringHashingCollectorsUsed;
    }

    public Integer getNumericCollectorsUsed() {
        return numericCollectorsUsed;
    }

    public void setNumericCollectorsUsed(Integer numericCollectorsUsed) {
        this.numericCollectorsUsed = numericCollectorsUsed;
    }

    public Integer getEmptyCollectorsUsed() {
        return emptyCollectorsUsed;
    }

    public void setEmptyCollectorsUsed(Integer emptyCollectorsUsed) {
        this.emptyCollectorsUsed = emptyCollectorsUsed;
    }

    public List<String> getDeferredAggregators() {
        return deferredAggregators;
    }

    public void setDeferredAggregators(List<String> deferredAggregators) {
        this.deferredAggregators = deferredAggregators;
    }

    public Integer getSegmentsWithDocCountField() {
        return segmentsWithDocCountField;
    }

    public void setSegmentsWithDocCountField(Integer segmentsWithDocCountField) {
        this.segmentsWithDocCountField = segmentsWithDocCountField;
    }

    public Integer getSegmentsWithDeletedDocs() {
        return segmentsWithDeletedDocs;
    }

    public void setSegmentsWithDeletedDocs(Integer segmentsWithDeletedDocs) {
        this.segmentsWithDeletedDocs = segmentsWithDeletedDocs;
    }

    public List<AggregationProfileDelegateDebugFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<AggregationProfileDelegateDebugFilter> filters) {
        this.filters = filters;
    }

    public Integer getSegmentsCounted() {
        return segmentsCounted;
    }

    public void setSegmentsCounted(Integer segmentsCounted) {
        this.segmentsCounted = segmentsCounted;
    }

    public Integer getSegmentsCollected() {
        return segmentsCollected;
    }

    public void setSegmentsCollected(Integer segmentsCollected) {
        this.segmentsCollected = segmentsCollected;
    }

    public String getMapReducer() {
        return mapReducer;
    }

    public void setMapReducer(String mapReducer) {
        this.mapReducer = mapReducer;
    }

    @Override
    public String toString() {
        return "AggregationProfileDebug{" +
                "segmentsWithMultiValuedOrds=" + segmentsWithMultiValuedOrds +
                ", collectionStrategy='" + collectionStrategy + '\'' +
                ", segmentsWithSingleValuedOrds=" + segmentsWithSingleValuedOrds +
                ", totalBuckets=" + totalBuckets +
                ", builtBuckets=" + builtBuckets +
                ", resultStrategy='" + resultStrategy + '\'' +
                ", hasFilter=" + hasFilter +
                ", delegate='" + delegate + '\'' +
                ", delegateDebug=" + delegateDebug +
                ", charsFetched=" + charsFetched +
                ", extractCount=" + extractCount +
                ", extractNs=" + extractNs +
                ", valuesFetched=" + valuesFetched +
                ", collectAnalyzedNs=" + collectAnalyzedNs +
                ", collectAnalyzedCount=" + collectAnalyzedCount +
                ", survivingBuckets=" + survivingBuckets +
                ", ordinalsCollectorsUsed=" + ordinalsCollectorsUsed +
                ", ordinalsCollectorsOverheadTooHigh=" + ordinalsCollectorsOverheadTooHigh +
                ", stringHashingCollectorsUsed=" + stringHashingCollectorsUsed +
                ", numericCollectorsUsed=" + numericCollectorsUsed +
                ", emptyCollectorsUsed=" + emptyCollectorsUsed +
                ", deferredAggregators=" + deferredAggregators +
                ", segmentsWithDocCountField=" + segmentsWithDocCountField +
                ", segmentsWithDeletedDocs=" + segmentsWithDeletedDocs +
                ", filters=" + filters +
                ", segmentsCounted=" + segmentsCounted +
                ", segmentsCollected=" + segmentsCollected +
                ", mapReducer='" + mapReducer + '\'' +
                '}';
    }

}
