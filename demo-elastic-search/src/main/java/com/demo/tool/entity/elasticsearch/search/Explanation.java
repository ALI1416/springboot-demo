package com.demo.tool.entity.elasticsearch.search;


import java.util.List;

/**
 * <h1>说明</h1>
 *
 * <p>
 * createDate 2023/09/12 10:47:02
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Explanation {

    /**
     * 描述
     */
    private String description;
    /**
     * 详情
     */
    private List<ExplanationDetail> details;
    /**
     * 值
     */
    private float value;

    public Explanation() {
    }

    public Explanation(co.elastic.clients.elasticsearch.core.explain.Explanation explanation) {
        this.description = explanation.description();
        this.details = ExplanationDetail.toList(explanation.details());
        this.value = explanation.value();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExplanationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ExplanationDetail> details) {
        this.details = details;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Explanation{" +
                "description='" + description + '\'' +
                ", details=" + details +
                ", value=" + value +
                '}';
    }

}
