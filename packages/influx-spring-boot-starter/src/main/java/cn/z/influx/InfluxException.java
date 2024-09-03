package cn.z.influx;

/**
 * <h1>InfluxDB异常</h1>
 *
 * <p>
 * createDate 2024/09/03 15:17:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class InfluxException extends RuntimeException {

    /**
     * InfluxDB异常
     */
    public InfluxException() {
        super();
    }

    /**
     * InfluxDB异常
     *
     * @param message 信息
     */
    public InfluxException(String message) {
        super(message);
    }

    /**
     * InfluxDB异常
     *
     * @param message 信息
     * @param cause   异常
     */
    public InfluxException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * InfluxDB异常
     *
     * @param cause 异常
     */
    public InfluxException(Throwable cause) {
        super(cause);
    }

}
