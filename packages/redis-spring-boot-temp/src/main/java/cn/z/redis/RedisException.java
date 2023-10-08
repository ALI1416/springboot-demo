package cn.z.redis;

/**
 * <h1>Redis异常</h1>
 *
 * <p>
 * createDate 2023/09/15 15:17:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class RedisException extends RuntimeException {

    /**
     * Redis异常
     */
    public RedisException() {
        super();
    }

    /**
     * Redis异常
     *
     * @param message 信息
     */
    public RedisException(String message) {
        super(message);
    }

    /**
     * Redis异常
     *
     * @param message 信息
     * @param cause   异常
     */
    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Redis异常
     *
     * @param cause 异常
     */
    public RedisException(Throwable cause) {
        super(cause);
    }

}
