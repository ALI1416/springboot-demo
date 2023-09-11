package com.demo.tool.entity.elasticsearch;

/**
 * <h1>创建索引响应</h1>
 *
 * <p>
 * createDate 2023/09/11 14:02:06
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class CreateIndexResponse {

    /**
     * 索引名
     */
    private String index;
    /**
     * 分片确认
     */
    private boolean shardsAcknowledged;
    /**
     * 确认
     */
    private boolean acknowledged;

    public CreateIndexResponse() {
    }

    public CreateIndexResponse(co.elastic.clients.elasticsearch.indices.CreateIndexResponse response) {
        this.index = response.index();
        this.shardsAcknowledged = response.shardsAcknowledged();
        this.acknowledged = response.acknowledged();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isShardsAcknowledged() {
        return shardsAcknowledged;
    }

    public void setShardsAcknowledged(boolean shardsAcknowledged) {
        this.shardsAcknowledged = shardsAcknowledged;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    @Override
    public String toString() {
        return "CreateIndexResponse{" +
                "index='" + index + '\'' +
                ", shardsAcknowledged=" + shardsAcknowledged +
                ", acknowledged=" + acknowledged +
                '}';
    }

}
