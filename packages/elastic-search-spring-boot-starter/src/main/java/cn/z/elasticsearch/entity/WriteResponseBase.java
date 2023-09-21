package cn.z.elasticsearch.entity;

/**
 * <h1>写结果基类</h1>
 *
 * <p>
 * createDate 2023/09/08 11:40:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public abstract class WriteResponseBase {

    /**
     * ID
     */
    private String id;
    /**
     * 索引名
     */
    private String index;
    /**
     * 主要
     */
    private long primaryTerm;
    /**
     * 结果
     */
    private Result result;
    /**
     * 序号
     */
    private long seqNo;
    /**
     * 分片统计
     */
    private ShardStatistics shards;
    /**
     * 版本号
     */
    private long version;
    /**
     * 强制刷新
     */
    private Boolean forcedRefresh;

    protected WriteResponseBase() {
    }

    protected WriteResponseBase(co.elastic.clients.elasticsearch._types.WriteResponseBase base) {
        this.id = base.id();
        this.index = base.index();
        this.primaryTerm = base.primaryTerm();
        this.result = Result.toEnum(base.result());
        this.seqNo = base.seqNo();
        if (base.shards() != null) {
            this.shards = new ShardStatistics(base.shards());
        }
        this.version = base.version();
        this.forcedRefresh = base.forcedRefresh();
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

    public long getPrimaryTerm() {
        return primaryTerm;
    }

    public void setPrimaryTerm(long primaryTerm) {
        this.primaryTerm = primaryTerm;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(long seqNo) {
        this.seqNo = seqNo;
    }

    public ShardStatistics getShards() {
        return shards;
    }

    public void setShards(ShardStatistics shards) {
        this.shards = shards;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Boolean getForcedRefresh() {
        return forcedRefresh;
    }

    public void setForcedRefresh(Boolean forcedRefresh) {
        this.forcedRefresh = forcedRefresh;
    }

    @Override
    public String toString() {
        return "WriteResponseBase{" +
                "id='" + id + '\'' +
                ", index='" + index + '\'' +
                ", primaryTerm=" + primaryTerm +
                ", result=" + result +
                ", seqNo=" + seqNo +
                ", shards=" + shards +
                ", version=" + version +
                ", forcedRefresh=" + forcedRefresh +
                '}';
    }

}
