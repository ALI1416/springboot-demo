package cn.z.elasticsearch.entity.search;

import co.elastic.clients.json.JsonData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>命中</h1>
 *
 * <p>
 * createDate 2023/09/12 10:40:34
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Hit<T> {

    /**
     * 索引名
     */
    private String index;
    /**
     * ID
     */
    private String id;
    /**
     * 分数
     */
    private Double score;
    /**
     * 说明
     */
    private Explanation explanation;
    /**
     * 字段
     */
    private Map<String, JsonData> fields;
    /**
     * 高亮
     */
    private Map<String, List<String>> highlight;
    /**
     * 内部命中
     */
    private Map<String, InnerHitsResult> innerHits;
    /**
     *
     */
    private List<String> matchedQueries;
    /**
     * 匹配查询
     */
    private NestedIdentity nested;
    /**
     * 忽略
     */
    private List<String> ignored;
    /**
     * 忽略字段值
     */
    private Map<String, List<String>> ignoredFieldValues;
    /**
     * 分片
     */
    private String shard;
    /**
     * 节点
     */
    private String node;
    /**
     * 路由
     */
    private String routing;
    /**
     * 源
     */
    private T source;
    /**
     * 序号
     */
    private Long seqNo;
    /**
     * 主要
     */
    private Long primaryTerm;
    /**
     * 版本号
     */
    private Long version;
    /**
     * 排序
     */
    private List<FieldValue> sort;

    public Hit() {
    }

    public Hit(co.elastic.clients.elasticsearch.core.search.Hit<T> hit) {
        this.index = hit.index();
        this.id = hit.id();
        this.score = hit.score();
        if (hit.explanation() != null) {
            this.explanation = new Explanation(hit.explanation());
        }
        this.fields = hit.fields();
        this.highlight = hit.highlight();
        this.innerHits = InnerHitsResult.toMap(hit.innerHits());
        this.matchedQueries = hit.matchedQueries();
        if (hit.nested() != null) {
            this.nested = new NestedIdentity(hit.nested());
        }
        this.ignored = hit.ignored();
        this.ignoredFieldValues = hit.ignoredFieldValues();
        this.shard = hit.shard();
        this.node = hit.node();
        this.routing = hit.routing();
        this.source = hit.source();
        this.seqNo = hit.seqNo();
        this.primaryTerm = hit.primaryTerm();
        this.version = hit.version();
        this.sort = FieldValue.toList(hit.sort());
    }

    public static <T> List<Hit<T>> toList(List<co.elastic.clients.elasticsearch.core.search.Hit<T>> hitList) {
        List<Hit<T>> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.Hit<T> hit : hitList) {
            list.add(new Hit<>(hit));
        }
        return list;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Explanation getExplanation() {
        return explanation;
    }

    public void setExplanation(Explanation explanation) {
        this.explanation = explanation;
    }

    public Map<String, JsonData> getFields() {
        return fields;
    }

    public void setFields(Map<String, JsonData> fields) {
        this.fields = fields;
    }

    public Map<String, List<String>> getHighlight() {
        return highlight;
    }

    public void setHighlight(Map<String, List<String>> highlight) {
        this.highlight = highlight;
    }

    public Map<String, InnerHitsResult> getInnerHits() {
        return innerHits;
    }

    public void setInnerHits(Map<String, InnerHitsResult> innerHits) {
        this.innerHits = innerHits;
    }

    public List<String> getMatchedQueries() {
        return matchedQueries;
    }

    public void setMatchedQueries(List<String> matchedQueries) {
        this.matchedQueries = matchedQueries;
    }

    public NestedIdentity getNested() {
        return nested;
    }

    public void setNested(NestedIdentity nested) {
        this.nested = nested;
    }

    public List<String> getIgnored() {
        return ignored;
    }

    public void setIgnored(List<String> ignored) {
        this.ignored = ignored;
    }

    public Map<String, List<String>> getIgnoredFieldValues() {
        return ignoredFieldValues;
    }

    public void setIgnoredFieldValues(Map<String, List<String>> ignoredFieldValues) {
        this.ignoredFieldValues = ignoredFieldValues;
    }

    public String getShard() {
        return shard;
    }

    public void setShard(String shard) {
        this.shard = shard;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public Long getPrimaryTerm() {
        return primaryTerm;
    }

    public void setPrimaryTerm(Long primaryTerm) {
        this.primaryTerm = primaryTerm;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<FieldValue> getSort() {
        return sort;
    }

    public void setSort(List<FieldValue> sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "index='" + index + '\'' +
                ", id='" + id + '\'' +
                ", score=" + score +
                ", explanation=" + explanation +
                ", fields=" + fields +
                ", highlight=" + highlight +
                ", innerHits=" + innerHits +
                ", matchedQueries=" + matchedQueries +
                ", nested=" + nested +
                ", ignored=" + ignored +
                ", ignoredFieldValues=" + ignoredFieldValues +
                ", shard='" + shard + '\'' +
                ", node='" + node + '\'' +
                ", routing='" + routing + '\'' +
                ", source=" + source +
                ", seqNo=" + seqNo +
                ", primaryTerm=" + primaryTerm +
                ", version=" + version +
                ", sort=" + sort +
                '}';
    }

}
