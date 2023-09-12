package com.demo.tool.entity.elasticsearch.analyze;

import co.elastic.clients.json.JsonData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>解释分析Token</h1>
 *
 * <p>
 * createDate 2023/09/12 17:10:36
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ExplainAnalyzeToken {

    /**
     * 属性
     */
    private Map<String, JsonData> attributes;
    /**
     * 字节长度
     */
    private String bytes;
    /**
     * 终点偏移
     */
    private long endOffset;
    /**
     * 关键词
     */
    private Boolean keyword;
    /**
     * 位置
     */
    private long position;
    /**
     * 位置长度
     */
    private long positionlength;
    /**
     * 起点偏移
     */
    private long startOffset;
    /**
     * 词频
     */
    private long termfrequency;
    /**
     * token
     */
    private String token;
    /**
     * 类型
     */
    private String type;

    public ExplainAnalyzeToken() {
    }

    public ExplainAnalyzeToken(co.elastic.clients.elasticsearch.indices.analyze.ExplainAnalyzeToken token) {
        this.attributes = token.attributes();
        this.bytes = token.bytes();
        this.endOffset = token.endOffset();
        this.keyword = token.keyword();
        this.position = token.position();
        this.positionlength = token.positionlength();
        this.startOffset = token.startOffset();
        this.termfrequency = token.termfrequency();
        this.token = token.token();
        this.type = token.type();
    }

    public static List<ExplainAnalyzeToken> toList(List<co.elastic.clients.elasticsearch.indices.analyze.ExplainAnalyzeToken> tokenList) {
        List<ExplainAnalyzeToken> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.indices.analyze.ExplainAnalyzeToken token : tokenList) {
            list.add(new ExplainAnalyzeToken(token));
        }
        return list;
    }

    public Map<String, JsonData> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, JsonData> attributes) {
        this.attributes = attributes;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(long endOffset) {
        this.endOffset = endOffset;
    }

    public Boolean getKeyword() {
        return keyword;
    }

    public void setKeyword(Boolean keyword) {
        this.keyword = keyword;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getPositionlength() {
        return positionlength;
    }

    public void setPositionlength(long positionlength) {
        this.positionlength = positionlength;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
    }

    public long getTermfrequency() {
        return termfrequency;
    }

    public void setTermfrequency(long termfrequency) {
        this.termfrequency = termfrequency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ExplainAnalyzeToken{" +
                "attributes=" + attributes +
                ", bytes='" + bytes + '\'' +
                ", endOffset=" + endOffset +
                ", keyword=" + keyword +
                ", position=" + position +
                ", positionlength=" + positionlength +
                ", startOffset=" + startOffset +
                ", termfrequency=" + termfrequency +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
