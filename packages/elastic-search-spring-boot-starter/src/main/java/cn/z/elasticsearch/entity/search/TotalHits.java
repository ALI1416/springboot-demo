package cn.z.elasticsearch.entity.search;

/**
 * <h1>总共命中</h1>
 *
 * <p>
 * createDate 2023/09/12 10:37:38
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class TotalHits {

    /**
     * 总共命中关系
     */
    private TotalHitsRelation relation;
    /**
     * 值
     */
    private long value;

    public TotalHits() {
    }

    public TotalHits(co.elastic.clients.elasticsearch.core.search.TotalHits hits) {
        this.relation = TotalHitsRelation.toEnum(hits.relation());
        this.value = hits.value();
    }

    public TotalHitsRelation getRelation() {
        return relation;
    }

    public void setRelation(TotalHitsRelation relation) {
        this.relation = relation;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TotalHits{" +
                "relation=" + relation +
                ", value=" + value +
                '}';
    }

}
