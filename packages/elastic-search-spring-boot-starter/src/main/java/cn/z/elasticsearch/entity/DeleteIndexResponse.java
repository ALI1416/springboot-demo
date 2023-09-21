package cn.z.elasticsearch.entity;

/**
 * <h1>删除索引响应</h1>
 *
 * <p>
 * createDate 2023/09/11 14:07:41
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class DeleteIndexResponse extends IndicesResponseBase {

    public DeleteIndexResponse() {
    }

    public DeleteIndexResponse(co.elastic.clients.elasticsearch.indices.DeleteIndexResponse response) {
        super(response);
    }

}
