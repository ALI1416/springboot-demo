package cn.z.elasticsearch;

/**
 * <h1>ElasticSearch异常</h1>
 *
 * <p>
 * createDate 2023/11/10 11:30:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ElasticSearchException extends RuntimeException {

    /**
     * ElasticSearch异常
     */
    public ElasticSearchException() {
        super();
    }

    /**
     * ElasticSearch异常
     *
     * @param message 信息
     */
    public ElasticSearchException(String message) {
        super(message);
    }

    /**
     * ElasticSearch异常
     *
     * @param message 信息
     * @param cause   异常
     */
    public ElasticSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ElasticSearch异常
     *
     * @param cause 异常
     */
    public ElasticSearchException(Throwable cause) {
        super(cause);
    }

}
