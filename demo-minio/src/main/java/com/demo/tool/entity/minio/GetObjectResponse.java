package com.demo.tool.entity.minio;


import java.io.FilterInputStream;

/**
 * <h1>获取对象结果</h1>
 *
 * <p>
 * createDate 2022/03/31 16:02:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class GetObjectResponse {

    /**
     * 通用返回
     */
    private GenericResponse response;
    /**
     * 文件
     */
    private FilterInputStream file;

    public GetObjectResponse() {
    }

    public GetObjectResponse(io.minio.GetObjectResponse response) {
        this.response = new GenericResponse(response);
        this.file = response;
    }

    public GenericResponse getResponse() {
        return this.response;
    }

    public FilterInputStream getFile() {
        return this.file;
    }

    public void setResponse(GenericResponse response) {
        this.response = response;
    }

    public void setFile(FilterInputStream file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "GetObjectResponse{" +
                "response=" + response +
                ", file=" + file +
                '}';
    }

}
