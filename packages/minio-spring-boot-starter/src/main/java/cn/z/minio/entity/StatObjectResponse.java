package cn.z.minio.entity;


import cn.z.minio.MinioTemp;

import java.sql.Timestamp;
import java.util.Map;

/**
 * <h1>对象状态结果</h1>
 *
 * <p>
 * createDate 2022/03/29 16:13:16
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class StatObjectResponse extends GenericResponse {

    /**
     * ETag
     */
    private String etag;
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

    public StatObjectResponse(io.minio.StatObjectResponse response) {
        super(response);
        this.etag = response.etag();
        this.size = response.size();
        this.lastModifiedDate = MinioTemp.zonedDateTime2Timestamp(response.lastModified());
        if (response.retentionMode() != null) {
            this.retentionMode = response.retentionMode().name();
        }
        if (response.retentionRetainUntilDate() != null) {
            this.retentionRetainUntilDate = MinioTemp.zonedDateTime2Timestamp(response.retentionRetainUntilDate());
        }
        this.legalHold = response.legalHold().status();
        this.deleteMarker = response.deleteMarker();
        this.userMetadata = response.userMetadata();
        this.versionId = response.versionId();
        this.contentType = response.contentType();
    }

    public String getEtag() {
        return this.etag;
    }

    public long getSize() {
        return this.size;
    }

    public Timestamp getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public String getRetentionMode() {
        return this.retentionMode;
    }

    public Timestamp getRetentionRetainUntilDate() {
        return this.retentionRetainUntilDate;
    }

    public boolean isLegalHold() {
        return this.legalHold;
    }

    public boolean isDeleteMarker() {
        return this.deleteMarker;
    }

    public Map<String, String> getUserMetadata() {
        return this.userMetadata;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setRetentionMode(String retentionMode) {
        this.retentionMode = retentionMode;
    }

    public void setRetentionRetainUntilDate(Timestamp retentionRetainUntilDate) {
        this.retentionRetainUntilDate = retentionRetainUntilDate;
    }

    public void setLegalHold(boolean legalHold) {
        this.legalHold = legalHold;
    }

    public void setDeleteMarker(boolean deleteMarker) {
        this.deleteMarker = deleteMarker;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "StatObjectResponse{" +
                "etag='" + etag + '\'' +
                ", size=" + size +
                ", lastModifiedDate=" + lastModifiedDate +
                ", retentionMode='" + retentionMode + '\'' +
                ", retentionRetainUntilDate=" + retentionRetainUntilDate +
                ", legalHold=" + legalHold +
                ", deleteMarker=" + deleteMarker +
                ", userMetadata=" + userMetadata +
                ", versionId='" + versionId + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

}
