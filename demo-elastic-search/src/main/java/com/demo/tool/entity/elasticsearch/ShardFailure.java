package com.demo.tool.entity.elasticsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>失败分片</h1>
 *
 * <p>
 * createDate 2023/09/11 10:51:16
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ShardFailure {

    /**
     * 索引名
     */
    private String index;
    /**
     * 节点
     */
    private String node;
    /**
     * 错误原因
     */
    private ErrorCause reason;
    /**
     * 分片
     */
    private int shard;
    /**
     * 状态
     */
    private String status;

    public ShardFailure() {
    }

    public ShardFailure(co.elastic.clients.elasticsearch._types.ShardFailure failure) {
        this.index = failure.index();
        this.node = failure.index();
        this.reason = new ErrorCause(failure.reason());
        this.shard = failure.shard();
        this.status = failure.status();
    }

    public static List<ShardFailure> toList(List<co.elastic.clients.elasticsearch._types.ShardFailure> failureList) {
        List<ShardFailure> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch._types.ShardFailure failure : failureList) {
            list.add(new ShardFailure(failure));
        }
        return list;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public ErrorCause getReason() {
        return reason;
    }

    public void setReason(ErrorCause reason) {
        this.reason = reason;
    }

    public int getShard() {
        return shard;
    }

    public void setShard(int shard) {
        this.shard = shard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShardFailure{" +
                "index='" + index + '\'' +
                ", node='" + node + '\'' +
                ", reason=" + reason +
                ", shard=" + shard +
                ", status='" + status + '\'' +
                '}';
    }

}
