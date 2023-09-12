package com.demo.tool.entity.elasticsearch.analyze;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>分析Token</h1>
 *
 * <p>
 * createDate 2023/09/12 17:52:23
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AnalyzeToken {

    /**
     * 结束偏移
     */
    private long endOffset;
    /**
     * 位置
     */
    private long position;
    /**
     * 位置长度
     */
    private Long positionlength;
    /**
     * 其实偏移
     */
    private long startOffset;
    /**
     * token
     */
    private String token;
    /**
     * 类型
     */
    private String type;

    public AnalyzeToken() {
    }

    public AnalyzeToken(co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken token) {
        this.endOffset = token.endOffset();
        this.position = token.position();
        this.positionlength = token.positionlength();
        this.startOffset = token.startOffset();
        this.token = token.token();
        this.type = token.type();
    }

    public static List<AnalyzeToken> toList(List<co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken> tokenList) {
        List<AnalyzeToken> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken token : tokenList) {
            list.add(new AnalyzeToken(token));
        }
        return list;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(long endOffset) {
        this.endOffset = endOffset;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public Long getPositionlength() {
        return positionlength;
    }

    public void setPositionlength(Long positionlength) {
        this.positionlength = positionlength;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
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
        return "AnalyzeToken{" +
                "endOffset=" + endOffset +
                ", position=" + position +
                ", positionlength=" + positionlength +
                ", startOffset=" + startOffset +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
