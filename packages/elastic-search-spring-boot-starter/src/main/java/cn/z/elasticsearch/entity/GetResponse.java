package cn.z.elasticsearch.entity;

/**
 * <h1>GetResponse</h1>
 *
 * <p>
 * createDate 2023/09/11 14:18:41
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class GetResponse<T> extends GetResult<T> {

    public GetResponse() {
    }

    public GetResponse(co.elastic.clients.elasticsearch.core.GetResponse<T> response) {
        super(response);
    }

}
