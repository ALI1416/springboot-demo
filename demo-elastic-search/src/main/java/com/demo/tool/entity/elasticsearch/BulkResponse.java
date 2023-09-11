package com.demo.tool.entity.elasticsearch;

import java.util.List;

/**
 * <h1>批量结果</h1>
 *
 * <p>
 * createDate 2023/09/11 15:19:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class BulkResponse {

    /**
     * 发生错误
     */
    private boolean errors;
    /**
     * 项
     */
    private List<BulkResponseItem> items;
    /**
     * took
     */
    private long took;
    /**
     * ingestTook
     */
    private Long ingestTook;

    public BulkResponse() {
    }

    public BulkResponse(co.elastic.clients.elasticsearch.core.BulkResponse response) {
        this.errors = response.errors();
        this.items = BulkResponseItem.toList(response.items());
        this.took = response.took();
        this.ingestTook = response.ingestTook();
    }

    public boolean isErrors() {
        return errors;
    }

    public void setErrors(boolean errors) {
        this.errors = errors;
    }

    public List<BulkResponseItem> getItems() {
        return items;
    }

    public void setItems(List<BulkResponseItem> items) {
        this.items = items;
    }

    public long getTook() {
        return took;
    }

    public void setTook(long took) {
        this.took = took;
    }

    public Long getIngestTook() {
        return ingestTook;
    }

    public void setIngestTook(Long ingestTook) {
        this.ingestTook = ingestTook;
    }

    @Override
    public String toString() {
        return "BulkResponse{" +
                "errors=" + errors +
                ", items=" + items +
                ", took=" + took +
                ", ingestTook=" + ingestTook +
                '}';
    }

}
