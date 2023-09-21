package cn.z.elasticsearch.entity.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>短语建议选项</h1>
 *
 * <p>
 * createDate 2023/09/12 16:18:36
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class PhraseSuggestOption {

    /**
     * 文本
     */
    private String text;
    /**
     * 分数
     */
    private double score;
    /**
     * 高亮
     */
    private String highlighted;
    /**
     * 核对匹配
     */
    private Boolean collateMatch;

    public PhraseSuggestOption() {
    }

    public PhraseSuggestOption(co.elastic.clients.elasticsearch.core.search.PhraseSuggestOption option) {
        this.text = option.text();
        this.score = option.score();
        this.highlighted = option.highlighted();
        this.collateMatch = option.collateMatch();
    }

    public static List<PhraseSuggestOption> toList(List<co.elastic.clients.elasticsearch.core.search.PhraseSuggestOption> optionList) {
        List<PhraseSuggestOption> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.PhraseSuggestOption option : optionList) {
            list.add(new PhraseSuggestOption(option));
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
        return "PhraseSuggestOption{" +
                "text='" + text + '\'' +
                ", score=" + score +
                ", highlighted='" + highlighted + '\'' +
                ", collateMatch=" + collateMatch +
                '}';
    }

}
