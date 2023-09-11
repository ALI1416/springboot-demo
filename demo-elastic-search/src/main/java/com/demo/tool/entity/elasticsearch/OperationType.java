package com.demo.tool.entity.elasticsearch;

/**
 * <h1>操作类型</h1>
 *
 * <p>
 * createDate 2023/09/11 15:33:09
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum OperationType {

    /**
     * 索引
     */
    Index("index"),
    /**
     * 创建
     */
    Create("create"),
    /**
     * 更新
     */
    Update("update"),
    /**
     * 删除
     */
    Delete("delete");

    /**
     * 值
     */
    private final String jsonValue;

    OperationType(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public static OperationType toEnum(co.elastic.clients.elasticsearch.core.bulk.OperationType type) {
        switch (type.name()) {
            case "Index":
                return OperationType.Index;
            case "Create":
                return OperationType.Create;
            case "Update":
                return OperationType.Update;
            case "Delete":
                return OperationType.Delete;
            default:
                return null;
        }
    }

    public String jsonValue() {
        return this.jsonValue;
    }

    public String getJsonValue() {
        return jsonValue;
    }

    @Override
    public String toString() {
        return "OperationType{" +
                "jsonValue='" + jsonValue + '\'' +
                '}';
    }

}
