package cn.z.elasticsearch.entity.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>详细说明</h1>
 *
 * <p>
 * createDate 2023/09/12 10:41:19
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ExplanationDetail {

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

    public ExplanationDetail() {
    }

    public ExplanationDetail(co.elastic.clients.elasticsearch.core.explain.ExplanationDetail detail) {
        this.description = detail.description();
        this.details = ExplanationDetail.toList(detail.details());
        this.value = detail.value();
    }

    public static List<ExplanationDetail> toList(List<co.elastic.clients.elasticsearch.core.explain.ExplanationDetail> detailList) {
        List<ExplanationDetail> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.explain.ExplanationDetail detail : detailList) {
            list.add(new ExplanationDetail(detail));
        }
        return list;
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
        return "ExplanationDetail{" +
                "description='" + description + '\'' +
                ", details=" + details +
                ", value=" + value +
                '}';
    }

}
