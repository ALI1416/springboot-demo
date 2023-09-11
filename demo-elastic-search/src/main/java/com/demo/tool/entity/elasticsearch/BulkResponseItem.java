package com.demo.tool.entity.elasticsearch;

import co.elastic.clients.json.JsonData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>批量响应项</h1>
 *
 * <p>
 * createDate 2023/09/11 15:22:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class BulkResponseItem {

    /**
     * 操作类型
     */
    private OperationType operationType;
    /**
     * ID
     */
    private String id;
    /**
     * 索引名
     */
    private String index;
    /**
     * 状态
     */
    private int status;
    /**
     * 错误原因
     */
    private ErrorCause error;
    /**
     * 主要
     */
    private Long primaryTerm;
    /**
     * 结果
     */
    private String result;
    /**
     * 序号
     */
    private Long seqNo;
    /**
     * 分片状态
     */
    private ShardStatistics shards;
    /**
     * 版本号
     */
    private Long version;
    /**
     * 强制刷新
     */
    private Boolean forcedRefresh;
    /**
     * 数据
     */
    private InlineGet<Map<String, JsonData>> get;

    public BulkResponseItem() {
    }

    public BulkResponseItem(co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem item) {
        this.operationType = OperationType.toEnum(item.operationType());
        this.id = item.id();
        this.index = item.index();
        this.status = item.status();
        if (item.error() != null) {
            this.error = new ErrorCause(item.error());
        }
        this.primaryTerm = item.primaryTerm();
        this.result = item.result();
        this.seqNo = item.seqNo();
        if (item.shards() != null) {
            this.shards = new ShardStatistics(item.shards());
        }
        this.version = item.version();
        this.forcedRefresh = item.forcedRefresh();
        if (item.get() != null) {
            this.get = new InlineGet<>(item.get());
        }
    }

    public static List<BulkResponseItem> toList(List<co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem> itemList) {
        List<BulkResponseItem> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem item : itemList) {
            list.add(new BulkResponseItem(item));
        }
        return list;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ErrorCause getError() {
        return error;
    }

    public void setError(ErrorCause error) {
        this.error = error;
    }

    public Long getPrimaryTerm() {
        return primaryTerm;
    }

    public void setPrimaryTerm(Long primaryTerm) {
        this.primaryTerm = primaryTerm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public ShardStatistics getShards() {
        return shards;
    }

    public void setShards(ShardStatistics shards) {
        this.shards = shards;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getForcedRefresh() {
        return forcedRefresh;
    }

    public void setForcedRefresh(Boolean forcedRefresh) {
        this.forcedRefresh = forcedRefresh;
    }

    public InlineGet<Map<String, JsonData>> getGet() {
        return get;
    }

    public void setGet(InlineGet<Map<String, JsonData>> get) {
        this.get = get;
    }

    @Override
    public String toString() {
        return "BulkResponseItem{" +
                "operationType=" + operationType +
                ", id='" + id + '\'' +
                ", index='" + index + '\'' +
                ", status=" + status +
                ", error=" + error +
                ", primaryTerm=" + primaryTerm +
                ", result='" + result + '\'' +
                ", seqNo=" + seqNo +
                ", shards=" + shards +
                ", version=" + version +
                ", forcedRefresh=" + forcedRefresh +
                ", get=" + get +
                '}';
    }

}
