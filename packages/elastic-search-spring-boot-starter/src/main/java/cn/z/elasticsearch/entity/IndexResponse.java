package cn.z.elasticsearch.entity;

/**
 * <h1>索引响应</h1>
 *
 * <p>
 * createDate 2023/09/08 11:43:17
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class IndexResponse extends WriteResponseBase {

    public IndexResponse(co.elastic.clients.elasticsearch.core.IndexResponse response) {
        super(response);
    }

}
