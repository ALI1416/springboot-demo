package com.demo.tool.entity.elasticsearch;

import co.elastic.clients.json.JsonData;

import java.util.Map;

/**
 * <h1>行获取</h1>
 *
 * <p>
 * createDate 2023/09/11 15:38:31
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class InlineGet<T> {

    /**
     * 源信息
     */
    private Map<String, JsonData> metadata;
    /**
     * 字段
     */
    private Map<String, JsonData> fields;
    /**
     * 是否找到
     */
    private boolean found;
    /**
     * 序号
     */
    private Long seqNo;
    /**
     * 主要
     */
    private Long primaryTerm;
    /**
     * 路由
     */
    private String routing;
    /**
     * 源
     */
    private T source;

    public InlineGet() {
    }

    public InlineGet(co.elastic.clients.elasticsearch._types.InlineGet<T> get) {
        this.metadata = get.metadata();
        this.fields = get.fields();
        this.found = get.found();
        this.seqNo = get.seqNo();
        this.primaryTerm = get.primaryTerm();
        this.routing = get.routing();
        this.source = get.source();
    }

    public Map<String, JsonData> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, JsonData> metadata) {
        this.metadata = metadata;
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

    @Override
    public String toString() {
        return "InlineGet{" +
                "metadata=" + metadata +
                ", fields=" + fields +
                ", found=" + found +
                ", seqNo=" + seqNo +
                ", primaryTerm=" + primaryTerm +
                ", routing='" + routing + '\'' +
                ", source=" + source +
                '}';
    }

}
