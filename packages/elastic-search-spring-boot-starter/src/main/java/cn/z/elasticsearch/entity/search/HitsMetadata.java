package cn.z.elasticsearch.entity.search;

import java.util.List;

/**
 * <h1>命中元数据</h1>
 *
 * <p>
 * createDate 2023/09/12 10:49:27
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class HitsMetadata<T> {

    /**
     * 总共命中
     */
    private TotalHits total;
    /**
     * 命中列表
     */
    private List<Hit<T>> hits;
    /**
     * 最大分数
     */
    private Double maxScore;

    public HitsMetadata() {
    }

    public HitsMetadata(co.elastic.clients.elasticsearch.core.search.HitsMetadata<T> metadata) {
        if (metadata.total() != null) {
            this.total = new TotalHits(metadata.total());
        }
        this.hits = Hit.toList(metadata.hits());
        this.maxScore = metadata.maxScore();
    }

    public TotalHits getTotal() {
        return total;
    }

    public void setTotal(TotalHits total) {
        this.total = total;
    }

    public List<Hit<T>> getHits() {
        return hits;
    }

    public void setHits(List<Hit<T>> hits) {
        this.hits = hits;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    @Override
    public String toString() {
        return "HitsMetadata{" +
                "total=" + total +
                ", hits=" + hits +
                ", maxScore=" + maxScore +
                '}';
    }

}
