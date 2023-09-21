package cn.z.elasticsearch.entity.search;

/**
 * <h1>群集统计信息</h1>
 *
 * <p>
 * createDate 2023/09/12 14:07:00
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ClusterStatistics {

    /**
     * 跳过
     */
    private int skipped;
    /**
     * 成功
     */
    private int successful;
    /**
     * 总共
     */
    private int total;

    public ClusterStatistics() {
    }

    public ClusterStatistics(co.elastic.clients.elasticsearch._types.ClusterStatistics statistics) {
        this.skipped = statistics.skipped();
        this.successful = statistics.successful();
        this.total = statistics.total();
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ClusterStatistics{" +
                "skipped=" + skipped +
                ", successful=" + successful +
                ", total=" + total +
                '}';
    }

}
