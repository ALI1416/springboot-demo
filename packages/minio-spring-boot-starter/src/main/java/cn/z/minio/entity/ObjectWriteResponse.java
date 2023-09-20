package cn.z.minio.entity;

/**
 * <h1>对象写入结果</h1>
 *
 * <p>
 * createDate 2022/04/01 11:24:43
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
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

    public ObjectWriteResponse(io.minio.ObjectWriteResponse response) {
        super(response);
        this.etag = response.etag().substring(1, response.etag().length() - 1);
        this.versionId = response.versionId();
    }

    public String getEtag() {
        return this.etag;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public String toString() {
        return "ObjectWriteResponse{" +
                "etag='" + etag + '\'' +
                ", versionId='" + versionId + '\'' +
                '}';
    }

}
