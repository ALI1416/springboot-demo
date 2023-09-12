package com.demo.tool.entity.elasticsearch.search;

/**
 * <h1>搜索响应</h1>
 *
 * <p>
 * createDate 2023/09/12 16:57:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class SearchResponse<T> extends ResponseBody<T> {

    public SearchResponse() {
    }

    public SearchResponse(co.elastic.clients.elasticsearch.core.SearchResponse<T> response) {
        super(response);
    }

}
