package com.demo.tool.entity.minio;

import com.demo.tool.MinioTemp;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Map;

/**
 * <h1>对象状态返回</h1>
 *
 * <p>
 * createDate 2022/03/29 16:13:16
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class StatObjectResponse extends GenericResponse {

    /**
     * ETag
     */
    private String eTag;
    /**
     * 文件大小(字节)
     */
    private long size;
    /**
     * 上次修改时间
     */
    private Timestamp lastModifiedDate;
    /**
     * 保留模式
     */
    private String retentionMode;
    /**
     * 保留截止时间
     */
    private Timestamp retentionRetainUntilDate;
    /**
     * 合法保留
     */
    private boolean legalHold;
    /**
     * 删除标记
     */
    private boolean deleteMarker;
    /**
     * 用户元信息
     */
    private Map<String, String> userMetadata;
    /**
     * 版本Id
     */
    private String versionId;
    /**
     * 内容类型
     */
    private String contentType;

    public StatObjectResponse() {
    }

    public StatObjectResponse(io.minio.StatObjectResponse statObjectResponse) {
        super(statObjectResponse);
        this.eTag = statObjectResponse.etag();
        this.size = statObjectResponse.size();
        this.lastModifiedDate = MinioTemp.zonedDateTime2Timestamp(statObjectResponse.lastModified());
        if (statObjectResponse.retentionMode() != null) {
            this.retentionMode = statObjectResponse.retentionMode().name();
        }
        if (statObjectResponse.retentionRetainUntilDate() != null) {
            this.retentionRetainUntilDate =
                    MinioTemp.zonedDateTime2Timestamp(statObjectResponse.retentionRetainUntilDate());
        }
        this.legalHold = statObjectResponse.legalHold().status();
        this.deleteMarker = statObjectResponse.deleteMarker();
        this.userMetadata = statObjectResponse.userMetadata();
        this.versionId = statObjectResponse.versionId();
        this.contentType = statObjectResponse.contentType();
    }

}
