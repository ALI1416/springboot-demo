package cn.z.mqtt.annotation;

/**
 * <h1>MQTT头部枚举</h1>
 *
 * <p>
 * createDate 2023/09/15 10:58:45
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum HeaderEnum {

    /**
     * 消息
     */
    MSG,
    /**
     * 主题
     */
    TOPIC,
    /**
     * 主题片段
     */
    TOPIC_PART,
    /**
     * ID
     */
    ID,
    /**
     * QoS
     */
    QOS,
    /**
     * 保留
     */
    RETAIN,
    /**
     * 重复
     */
    DUPLICATE

}
