package cn.z.mqtt.annotation;

/**
 * <h1>MQTT函数-主题</h1>
 *
 * <p>
 * createDate 2020/11/28 20:25:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@FunctionalInterface
public interface MqttFunctionTopic extends MqttFunction<String, Object> {

    /**
     * 执行
     */
    @Override
    Object run(String topic);

}
