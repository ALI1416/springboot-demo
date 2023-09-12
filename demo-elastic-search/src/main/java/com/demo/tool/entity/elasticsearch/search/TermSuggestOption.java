package com.demo.tool.entity.elasticsearch.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>条款建议选项</h1>
 *
 * <p>
 * createDate 2023/09/12 16:25:19
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class TermSuggestOption {

    /**
     * 文本
     */
    private String text;
    /**
     * 分数
     */
    private double score;
    /**
     * 频率
     */
    private long freq;
    /**
     * 高亮
     */
    private String highlighted;
    /**
     * 核对匹配
     */
    private Boolean collateMatch;

    public TermSuggestOption(co.elastic.clients.elasticsearch.core.search.TermSuggestOption option) {
        this.text = option.text();
        this.score = option.score();
        this.freq = option.freq();
        this.highlighted = option.highlighted();
        this.collateMatch = option.collateMatch();
    }

    public static List<TermSuggestOption> toList(List<co.elastic.clients.elasticsearch.core.search.TermSuggestOption> optionList) {
        List<TermSuggestOption> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.TermSuggestOption option : optionList) {
            list.add(new TermSuggestOption(option));
        }
        return list;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getFreq() {
        return freq;
    }

    public void setFreq(long freq) {
        this.freq = freq;
    }

    public String getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(String highlighted) {
        this.highlighted = highlighted;
    }

    public Boolean getCollateMatch() {
        return collateMatch;
    }

    public void setCollateMatch(Boolean collateMatch) {
        this.collateMatch = collateMatch;
    }

    @Override
    public String toString() {
        return "TermSuggestOption{" +
                "text='" + text + '\'' +
                ", score=" + score +
                ", freq=" + freq +
                ", highlighted='" + highlighted + '\'' +
                ", collateMatch=" + collateMatch +
                '}';
    }

}
