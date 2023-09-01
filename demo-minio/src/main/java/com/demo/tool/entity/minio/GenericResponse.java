package com.demo.tool.entity.minio;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Headers;

/**
 * <h1>通用返回</h1>
 *
 * <p>
 * createDate 2022/03/29 16:13:44
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class GenericResponse extends ToStringBase {

    /**
     * 消息头
     */
    private Headers headers;
    /**
     * 储存桶
     */
    private String bucket;
    /**
     * 区域
     */
    private String region;
    /**
     * 名称
     */
    private String name;

    public GenericResponse() {

    }

    public GenericResponse(io.minio.GenericResponse genericResponse) {
        this.headers = genericResponse.headers();
        this.bucket = genericResponse.bucket();
        this.region = genericResponse.region();
        this.name = genericResponse.object();
    }

    public GenericResponse(Headers headers, String bucket, String region, String name) {
        this.headers = headers;
        this.bucket = bucket;
        this.region = region;
        this.name = name;
    }

}
