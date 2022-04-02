package com.demo.util.pojo.minio;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>错误返回</h1>
 *
 * <p>
 * createDate 2022/04/01 17:32:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class ErrorResponse extends ToStringBase {

    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 储存桶
     */
    private String bucket;
    /**
     * 对象名
     */
    private String name;
    /**
     * 源
     */
    private String resource;
    /**
     * 请求Id
     */
    private String requestId;
    /**
     * 主机Id
     */
    private String hostId;

    public ErrorResponse() {

    }

    public ErrorResponse(io.minio.messages.ErrorResponse errorResponse) {
        this.code = errorResponse.code();
        this.message = errorResponse.message();
        this.bucket = errorResponse.bucketName();
        this.name = errorResponse.objectName();
        this.resource = errorResponse.resource();
        this.requestId = errorResponse.requestId();
        this.hostId = errorResponse.hostId();
    }

}
