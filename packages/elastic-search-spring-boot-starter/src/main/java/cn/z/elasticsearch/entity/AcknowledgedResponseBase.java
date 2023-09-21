package cn.z.elasticsearch.entity;

/**
 * <h1>确认响应基类</h1>
 *
 * <p>
 * createDate 2023/09/11 14:05:47
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public abstract class AcknowledgedResponseBase {

    /**
     * 确认
     */
    private boolean acknowledged;

    protected AcknowledgedResponseBase() {
    }

    protected AcknowledgedResponseBase(co.elastic.clients.elasticsearch._types.AcknowledgedResponseBase base) {
        this.acknowledged = base.acknowledged();
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    @Override
    public String toString() {
        return "AcknowledgedResponseBase{" +
                "acknowledged=" + acknowledged +
                '}';
    }

}
