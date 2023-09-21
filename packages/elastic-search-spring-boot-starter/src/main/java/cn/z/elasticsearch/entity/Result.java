package cn.z.elasticsearch.entity;

/**
 * <h1>返回结果</h1>
 *
 * <p>
 * createDate 2023/09/11 10:49:37
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum Result {

    /**
     * 创建
     */
    Created("created"),
    /**
     * 更新
     */
    Updated("updated"),
    /**
     * 删除
     */
    Deleted("deleted"),
    /**
     * 未找到
     */
    NotFound("not_found"),
    /**
     * 没有操作
     */
    NoOp("noop");

    /**
     * 值
     */
    private final String jsonValue;

    Result(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public static Result toEnum(co.elastic.clients.elasticsearch._types.Result result) {
        switch (result.name()) {
            case "Created":
                return Result.Created;
            case "Updated":
                return Result.Updated;
            case "Deleted":
                return Result.Deleted;
            case "NotFound":
                return Result.NotFound;
            case "NoOp":
                return Result.NoOp;
            default:
                return null;
        }
    }

    public String getJsonValue() {
        return jsonValue;
    }

    @Override
    public String toString() {
        return "Result{" +
                "jsonValue='" + jsonValue + '\'' +
                '}';
    }

}
