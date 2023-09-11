package com.demo.tool.entity.elasticsearch;

import java.util.List;

/**
 * <h1>分片统计</h1>
 *
 * <p>
 * createDate 2023/09/11 10:50:32
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ShardStatistics {

    /**
     * 字段名
     */
    private Number failed;
    /**
     * 成功
     */
    private Number successful;
    /**
     * 总共
     */
    private Number total;
    /**
     * 失败
     */
    private List<ShardFailure> failures;
    /**
     * 跳过
     */
    private Number skipped;

    public ShardStatistics() {
    }

    public ShardStatistics(co.elastic.clients.elasticsearch._types.ShardStatistics statistics) {
        this.failed = statistics.failed();
        this.successful = statistics.successful();
        this.total = statistics.total();
        this.failures = ShardFailure.toList(statistics.failures());
        this.skipped = statistics.skipped();
    }

    public Number getFailed() {
        return failed;
    }

    public void setFailed(Number failed) {
        this.failed = failed;
    }

    public Number getSuccessful() {
        return successful;
    }

    public void setSuccessful(Number successful) {
        this.successful = successful;
    }

    public Number getTotal() {
        return total;
    }

    public void setTotal(Number total) {
        this.total = total;
    }

    public List<ShardFailure> getFailures() {
        return failures;
    }

    public void setFailures(List<ShardFailure> failures) {
        this.failures = failures;
    }

    public Number getSkipped() {
        return skipped;
    }

    public void setSkipped(Number skipped) {
        this.skipped = skipped;
    }

    @Override
    public String toString() {
        return "ShardStatistics{" +
                "failed=" + failed +
                ", successful=" + successful +
                ", total=" + total +
                ", failures=" + failures +
                ", skipped=" + skipped +
                '}';
    }

}
