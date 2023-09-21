package cn.z.elasticsearch.entity;


/**
 * <h1>索引响应基类</h1>
 *
 * <p>
 * createDate 2023/09/11 14:06:46
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public abstract class IndicesResponseBase extends AcknowledgedResponseBase {

    /**
     * 分片
     */
    private ShardStatistics shards;

    protected IndicesResponseBase() {
    }

    protected IndicesResponseBase(co.elastic.clients.elasticsearch._types.IndicesResponseBase base) {
        super(base);
        if (base.shards() != null) {
            this.shards = new ShardStatistics(base.shards());
        }
    }

    public ShardStatistics getShards() {
        return shards;
    }

    public void setShards(ShardStatistics shards) {
        this.shards = shards;
    }

    @Override
    public String toString() {
        return "IndicesResponseBase{" +
                "shards=" + shards +
                '}';
    }

}
