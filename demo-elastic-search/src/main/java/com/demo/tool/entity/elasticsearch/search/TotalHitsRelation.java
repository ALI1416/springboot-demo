package com.demo.tool.entity.elasticsearch.search;

/**
 * <h1>总共命中关系</h1>
 *
 * <p>
 * createDate 2023/09/12 10:34:05
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum TotalHitsRelation {

    /**
     * 等于
     */
    Eq("eq"),
    /**
     * 大于等于
     */
    Gte("gte");

    /**
     * 值
     */
    private final String jsonValue;

    TotalHitsRelation(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public static TotalHitsRelation toEnum(co.elastic.clients.elasticsearch.core.search.TotalHitsRelation relation) {
        switch (relation.name()) {
            case "Eq":
                return TotalHitsRelation.Eq;
            case "Gte":
                return TotalHitsRelation.Gte;
            default:
                return null;
        }
    }

    public String getJsonValue() {
        return jsonValue;
    }

    @Override
    public String toString() {
        return "TotalHitsRelation{" +
                "jsonValue='" + jsonValue + '\'' +
                '}';
    }

}
