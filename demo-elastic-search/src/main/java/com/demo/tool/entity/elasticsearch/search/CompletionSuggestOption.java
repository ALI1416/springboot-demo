package com.demo.tool.entity.elasticsearch.search;

import co.elastic.clients.json.JsonData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>完成建议选项</h1>
 *
 * <p>
 * createDate 2023/09/12 16:05:07
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class CompletionSuggestOption<T> {

    /**
     * 核对匹配
     */
    private Boolean collateMatch;
    /**
     * 上下文
     */
    private Map<String, List<Context>> contexts;
    /**
     * 字段
     */
    private Map<String, JsonData> fields;
    /**
     * ID
     */
    private String id;
    /**
     * 索引
     */
    private String index;
    /**
     * 路由
     */
    private String routing;
    /**
     * 分数
     */
    private Double score;
    /**
     * 源
     */
    private T source;
    /**
     * 文本
     */
    private String text;

    public CompletionSuggestOption() {
    }

    public CompletionSuggestOption(co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption<T> option) {
        this.collateMatch = option.collateMatch();
        this.contexts = Context.toMap(option.contexts());
        this.fields = option.fields();
        this.id = option.id();
        this.index = option.index();
        this.routing = option.routing();
        this.score = option.score();
        this.source = option.source();
        this.text = option.text();
    }

    public static <T> List<CompletionSuggestOption<T>> toList(List<co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption<T>> optionList) {
        List<CompletionSuggestOption<T>> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption<T> option : optionList) {
            list.add(new CompletionSuggestOption<>(option));
        }
        return list;
    }

    public Boolean getCollateMatch() {
        return collateMatch;
    }

    public void setCollateMatch(Boolean collateMatch) {
        this.collateMatch = collateMatch;
    }

    public Map<String, List<Context>> getContexts() {
        return contexts;
    }

    public void setContexts(Map<String, List<Context>> contexts) {
        this.contexts = contexts;
    }

    public Map<String, JsonData> getFields() {
        return fields;
    }

    public void setFields(Map<String, JsonData> fields) {
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CompletionSuggestOption{" +
                "collateMatch=" + collateMatch +
                ", contexts=" + contexts +
                ", fields=" + fields +
                ", id='" + id + '\'' +
                ", index='" + index + '\'' +
                ", routing='" + routing + '\'' +
                ", score=" + score +
                ", source=" + source +
                ", text='" + text + '\'' +
                '}';
    }

}
