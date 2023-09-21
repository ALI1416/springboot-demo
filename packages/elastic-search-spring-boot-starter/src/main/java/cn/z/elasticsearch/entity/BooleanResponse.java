package cn.z.elasticsearch.entity;

/**
 * <h1>布尔响应</h1>
 *
 * <p>
 * createDate 2023/09/11 11:40:10
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class BooleanResponse {

    /**
     * 值
     */
    private boolean value;

    public BooleanResponse() {
    }

    public BooleanResponse(co.elastic.clients.transport.endpoints.BooleanResponse response) {
        this.value = response.value();
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BooleanResponse{" +
                "value=" + value +
                '}';
    }

}
