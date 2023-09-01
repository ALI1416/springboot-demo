package com.demo.tool.entity.minio;


import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

import java.io.FilterInputStream;

/**
 * <h1>获取对象返回</h1>
 *
 * <p>
 * createDate 2022/03/31 16:02:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class GetObjectResponse extends ToStringBase {

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

    public GetObjectResponse(io.minio.GetObjectResponse getObjectResponse) {
        this.response = new GenericResponse(getObjectResponse.headers(), getObjectResponse.bucket(),
                getObjectResponse.region(), getObjectResponse.object());
        this.file = getObjectResponse;
    }

}
