package com.demo.tool.entity.minio;

import okhttp3.Headers;

/**
 * <h1>通用结果</h1>
 *
 * <p>
 * createDate 2022/03/29 16:13:44
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class GenericResponse {

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

    public GenericResponse(io.minio.GenericResponse response) {
        this.headers = response.headers();
        this.bucket = response.bucket();
        this.region = response.region();
        this.name = response.object();
    }

    public GenericResponse(io.minio.GetObjectResponse response) {
        this.headers = response.headers();
        this.bucket = response.bucket();
        this.region = response.region();
        this.name = response.object();
    }

    public Headers getHeaders() {
        return this.headers;
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getRegion() {
        return this.region;
    }

    public String getName() {
        return this.name;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "headers=" + headers +
                ", bucket='" + bucket + '\'' +
                ", region='" + region + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
