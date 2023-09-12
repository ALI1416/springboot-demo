package com.demo.tool.entity.elasticsearch.analyze;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>字符拦截细节</h1>
 *
 * <p>
 * createDate 2023/09/12 17:40:49
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class CharFilterDetail {

    /**
     * 拦截的文本
     */
    private List<String> filteredText;
    /**
     * 名称
     */
    private String name;

    public CharFilterDetail() {
    }

    public CharFilterDetail(co.elastic.clients.elasticsearch.indices.analyze.CharFilterDetail detail) {
        this.filteredText = detail.filteredText();
        this.name = detail.name();
    }

    public static List<CharFilterDetail> toList(List<co.elastic.clients.elasticsearch.indices.analyze.CharFilterDetail> detailList) {
        List<CharFilterDetail> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.indices.analyze.CharFilterDetail detail : detailList) {
            list.add(new CharFilterDetail(detail));
        }
        return list;
    }

    public List<String> getFilteredText() {
        return filteredText;
    }

    public void setFilteredText(List<String> filteredText) {
        this.filteredText = filteredText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CharFilterDetail{" +
                "filteredText=" + filteredText +
                ", name='" + name + '\'' +
                '}';
    }

}
