package cn.z.minio.entity;

/**
 * <h1>错误结果</h1>
 *
 * <p>
 * createDate 2022/04/01 17:32:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ErrorResponse {

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

    public ErrorResponse(io.minio.messages.ErrorResponse response) {
        this.code = response.code();
        this.message = response.message();
        this.bucket = response.bucketName();
        this.name = response.objectName();
        this.resource = response.resource();
        this.requestId = response.requestId();
        this.hostId = response.hostId();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getName() {
        return this.name;
    }

    public String getResource() {
        return this.resource;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getHostId() {
        return this.hostId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", bucket='" + bucket + '\'' +
                ", name='" + name + '\'' +
                ", resource='" + resource + '\'' +
                ", requestId='" + requestId + '\'' +
                ", hostId='" + hostId + '\'' +
                '}';
    }

}
