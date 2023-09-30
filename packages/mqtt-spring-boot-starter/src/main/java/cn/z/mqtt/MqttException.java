package cn.z.mqtt;

/**
 * <h1>MQTT异常</h1>
 *
 * <p>
 * createDate 2023/09/15 15:17:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MqttException extends RuntimeException {

    /**
     * MQTT异常
     */
    public MqttException() {
        super();
    }

    /**
     * MQTT异常
     *
     * @param message 信息
     */
    public MqttException(String message) {
        super(message);
    }

    /**
     * MQTT异常
     *
     * @param message 信息
     * @param cause   异常
     */
    public MqttException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * MQTT异常
     *
     * @param cause 异常
     */
    public MqttException(Throwable cause) {
        super(cause);
    }

}
