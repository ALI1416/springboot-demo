package cn.z.minio;

/**
 * <h1>Minio异常</h1>
 *
 * <p>
 * createDate 2023/09/15 15:17:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MinioException extends RuntimeException {

    /**
     * Minio异常
     */
    public MinioException() {
        super();
    }

    /**
     * Minio异常
     *
     * @param message 信息
     */
    public MinioException(String message) {
        super(message);
    }

    /**
     * Minio异常
     *
     * @param message 信息
     * @param cause   异常
     */
    public MinioException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Minio异常
     *
     * @param cause 异常
     */
    public MinioException(Throwable cause) {
        super(cause);
    }

}
