package com.demo.tool.entity.elasticsearch;

import co.elastic.clients.json.JsonData;

import java.util.Map;

/**
 * <h1>获取结果</h1>
 *
 * <p>
 * createDate 2023/09/11 14:17:39
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class GetResult<T> {

    /**
     * 索引名
     */
    private String index;
    /**
     * 字段
     */
    private Map<String, JsonData> fields;
    /**
     * 是否找到
     */
    private boolean found;
    /**
     * ID
     */
    private String id;
    /**
     * 主要
     */
    private Long primaryTerm;
    /**
     * 路由
     */
    private String routing;
    /**
     * 序号
     */
    private Long seqNo;
    /**
     * 源
     */
    private T source;
    /**
     * 版本号
     */
    private Long version;

    public GetResult() {
    }

    public GetResult(co.elastic.clients.elasticsearch.core.get.GetResult<T> result) {
        this.index = result.index();
        this.fields = result.fields();
        this.found = result.found();
        this.id = result.id();
        this.primaryTerm = result.primaryTerm();
        this.routing = result.routing();
        this.seqNo = result.seqNo();
        this.source = result.source();
        this.version = result.version();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Map<String, JsonData> getFields() {
        return fields;
    }

    public void setFields(Map<String, JsonData> fields) {
        this.fields = fields;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPrimaryTerm() {
        return primaryTerm;
    }

    public void setPrimaryTerm(Long primaryTerm) {
        this.primaryTerm = primaryTerm;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GetResult{" +
                "index='" + index + '\'' +
                ", fields=" + fields +
                ", found=" + found +
                ", id='" + id + '\'' +
                ", primaryTerm=" + primaryTerm +
                ", routing='" + routing + '\'' +
                ", seqNo=" + seqNo +
                ", source=" + source +
                ", version=" + version +
                '}';
    }

}
