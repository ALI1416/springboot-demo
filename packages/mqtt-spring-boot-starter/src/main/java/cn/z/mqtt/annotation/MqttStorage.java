package cn.z.mqtt.annotation;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>MQTT存储</h1>
 *
 * <p>
 * createDate 2023/09/15 14:16:01
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MqttStorage {

    /**
     * 主题
     */
    private final List<String> topicList = new ArrayList<>();
    /**
     * QoS
     */
    private final List<Integer> qosList = new ArrayList<>();
    /**
     * 回调
     */
    private final List<IMqttMessageListener> callbackList = new ArrayList<>();

    public void addTopic(String topic) {
        topicList.add(topic);
    }

    public void addQos(int qos) {
        qosList.add(qos);
    }

    public void addCallback(IMqttMessageListener callback) {
        callbackList.add(callback);
    }

    public String[] getTopicArray() {
        return topicList.toArray(new String[0]);
    }

    public int[] getQosArray() {
        return qosList.stream().mapToInt(Integer::intValue).toArray();
    }

    public IMqttMessageListener[] getCallbackArray() {
        return callbackList.toArray(new IMqttMessageListener[0]);
    }

}
