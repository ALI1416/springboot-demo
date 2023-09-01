package com.demo.tool.entity.minio;

import lombok.Getter;
import lombok.Setter;

/**
 * <h1>对象写入返回</h1>
 *
 * <p>
 * createDate 2022/04/01 11:24:43
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class ObjectWriteResponse extends GenericResponse {

    /**
     * ETag
     */
    private String etag;
    /**
     * 版本Id
     */
    private String versionId;

    public ObjectWriteResponse() {

    }

    public ObjectWriteResponse(io.minio.ObjectWriteResponse objectWriteResponse) {
        super(objectWriteResponse);
        this.etag = objectWriteResponse.etag();
        this.versionId = objectWriteResponse.versionId();
    }

}
