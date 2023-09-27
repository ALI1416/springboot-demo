package cn.z.mqtt.annotation;

import cn.z.mqtt.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <h1>MQTT注解处理</h1>
 *
 * <p>
 * createDate 2023/09/14 16:54:51
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class MqttAnnotationProcessor implements BeanPostProcessor {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MqttAnnotationProcessor.class);

    /**
     * MQTT存储
     */
    private final MqttStorage mqttStorage;

    /**
     * 构造函数(自动注入)
     *
     * @param mqttStorage MqttStorage
     */
    public MqttAnnotationProcessor(MqttStorage mqttStorage) {
        this.mqttStorage = mqttStorage;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe != null) {
                mqttStorage.addTopic(subscribe.value());
                mqttStorage.addQos(subscribe.qos());
                String[] subscribePartArray = subscribe.value().split("/", -1);
                // 订阅主题分段列表 位置 +:true #:false
                List<Map.Entry<Integer, Boolean>> subscribePartList = new ArrayList<>();
                for (int i = 0; i < subscribePartArray.length; i++) {
                    if ("+".equals(subscribePartArray[i])) {
                        subscribePartList.add(new AbstractMap.SimpleEntry<>(i, true));
                    } else if ("#".equals(subscribePartArray[i])) {
                        subscribePartList.add(new AbstractMap.SimpleEntry<>(i, false));
                    }
                }
                mqttStorage.addCallback(callback(bean, method, subscribePartList));
            }
        }
        return bean;
    }

    /**
     * 回调
     *
     * @param object            Object
     * @param method            Method
     * @param subscribePartList 订阅主题分段列表
     * @return IMqttMessageListener
     */
    private static IMqttMessageListener callback(Object object, Method method, List<Map.Entry<Integer, Boolean>> subscribePartList) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 0) {
            throw new MqttException("方法 " + method + " 至少要有 1 个参数");
        }
        List<MqttFunction> mqttFunctionList = new ArrayList<>();
        boolean useAnnotation = false;
        for (int i = 0; i < parameters.length; i++) {
            // 第三个及以后参数都必须带注解
            if (i == 2) {
                useAnnotation = true;
            }
            Parameter parameter = parameters[i];
            Header header = parameter.getAnnotation(Header.class);
            if (header == null) {
                // 没有用注解
                if (useAnnotation) {
                    throw new MqttException("方法 " + method + " 不合法");
                }
                notAnnotation(mqttFunctionList, parameter, i);
            } else {
                // 使用了注解，后面参数必须带注解
                useAnnotation = true;
                useAnnotation(mqttFunctionList, method, parameter, subscribePartList, header);
            }
        }
        return (topic, message) -> {
            List<Object> objectList = new ArrayList<>();
            try {
                for (MqttFunction mqttFunction : mqttFunctionList) {
                    if (mqttFunction instanceof MqttFunctionMessage) {
                        objectList.add(mqttFunction.run(message));
                    } else {
                        objectList.add(mqttFunction.run(topic));
                    }
                }
                method.invoke(object, objectList.toArray());
            } catch (Exception e) {
                log.error("方法 " + method + " 调用失败", e);
            }
        };
    }

    /**
     * 没有用注解
     *
     * @param mqttFunctionList List MqttFunction
     * @param parameter        Parameter
     * @param index            位置
     */
    private static void notAnnotation(List<MqttFunction> mqttFunctionList, Parameter parameter, int index) {
        if (index == 0) {
            addMsg(mqttFunctionList, parameter);
        } else {
            addTopic(mqttFunctionList, parameter);
        }
    }

    /**
     * 使用了注解
     *
     * @param mqttFunctionList  List MqttFunction
     * @param method            Method
     * @param parameter         Parameter
     * @param subscribePartList 订阅主题分段列表
     * @param header            Header
     */
    private static void useAnnotation(List<MqttFunction> mqttFunctionList, Method method, Parameter parameter, List<Map.Entry<Integer, Boolean>> subscribePartList, Header header) {
        switch (header.value()) {
            default:
            case MSG: {
                addMsg(mqttFunctionList, parameter);
                break;
            }
            case TOPIC: {
                addTopic(mqttFunctionList, parameter);
                break;
            }
            case TOPIC_PART: {
                topicPartHandle(mqttFunctionList, method, parameter, subscribePartList, header.index());
                break;
            }
            case ID: {
                MqttFunctionMessage mqttFunctionMessage = MqttMessage::getId;
                mqttFunctionList.add(mqttFunctionMessage);
                break;
            }
            case QOS: {
                MqttFunctionMessage mqttFunctionMessage = MqttMessage::getQos;
                mqttFunctionList.add(mqttFunctionMessage);
                break;
            }
            case RETAIN: {
                MqttFunctionMessage mqttFunctionMessage = MqttMessage::isRetained;
                mqttFunctionList.add(mqttFunctionMessage);
                break;
            }
            case DUPLICATE: {
                MqttFunctionMessage mqttFunctionMessage = MqttMessage::isDuplicate;
                mqttFunctionList.add(mqttFunctionMessage);
                break;
            }
        }
    }

    /**
     * 主题片段处理
     *
     * @param mqttFunctionList  List MqttFunction
     * @param method            Method
     * @param parameter         Parameter
     * @param subscribePartList 订阅主题分段列表
     * @param index             主题片段位置
     */
    private static void topicPartHandle(List<MqttFunction> mqttFunctionList, Method method, Parameter parameter, List<Map.Entry<Integer, Boolean>> subscribePartList, int index) {
        Map.Entry<Integer, Boolean> entry;
        try {
            entry = subscribePartList.get(index);
        } catch (Exception e) {
            throw new MqttException("方法 " + method + " 的参数 " + parameter + " @Header注解的主题片段匹配位置的值 " + index + " 超出最大值 " + (subscribePartList.size() - 1));
        }
        int topicPartIndex = entry.getKey();
        boolean isSingle = entry.getValue();
        boolean isArray = parameter.getType().isArray();
        if (isSingle) {
            if (!isArray) {
                MqttFunctionTopic mqttFunctionTopic = topic -> {
                    String[] topicPart = topic.split("/", -1);
                    return castString(parameter.getType(), topicPart[topicPartIndex]);
                };
                mqttFunctionList.add(mqttFunctionTopic);
            } else {
                throw new MqttException("方法 " + method + " 的参数 " + parameter + " 不能是数组类型");
            }
        } else {
            if (isArray) {
                MqttFunctionTopic mqttFunctionTopic = topic -> {
                    String[] topicPart = topic.split("/", -1);
                    Class<?> parameterType = parameter.getType().getComponentType();
                    int arrayLength = topicPart.length - topicPartIndex;
                    Object topicArray = Array.newInstance(parameterType, arrayLength);
                    for (int i = 0; i < arrayLength; i++) {
                        Array.set(topicArray, i, castString(parameterType, topicPart[i + topicPartIndex]));
                    }
                    return topicArray;
                };
                mqttFunctionList.add(mqttFunctionTopic);
            } else {
                throw new MqttException("方法 " + method + " 的参数 " + parameter + " 必须是数组类型");
            }
        }
    }

    /**
     * 添加消息
     *
     * @param mqttFunctionList List MqttFunction
     * @param parameter        Parameter
     */
    private static void addMsg(List<MqttFunction> mqttFunctionList, Parameter parameter) {
        MqttFunctionMessage mqttFunctionMessage = msg -> castBytes(parameter.getType(), msg.getPayload());
        mqttFunctionList.add(mqttFunctionMessage);
    }

    /**
     * 添加主题
     *
     * @param mqttFunctionList List MqttFunction
     * @param parameter        Parameter
     */
    private static void addTopic(List<MqttFunction> mqttFunctionList, Parameter parameter) {
        MqttFunctionTopic mqttFunctionTopic = topic -> castString(parameter.getType(), topic);
        mqttFunctionList.add(mqttFunctionTopic);
    }

    /**
     * byte[]转换
     *
     * <p>
     * <code>byte[]</code>类型：返回本身<br>
     * <code>String</code>类型：返回<code>UTF-8</code>字符串<br>
     * <code>Void</code>类型：返回<code>null</code><br>
     * 8种基本类型的包装类型：当参数<code>byte[]</code>的长度为<code>0</code>时返回<code>null</code><br>
     * 8种基本类型及包装类型：先转为<code>UTF-8</code>字符串，再转为对应类型<br>
     * 自定义类型：调用它的入参为<code>byte[]</code>的构造函数并返回
     * </p>
     *
     * @param clazz Class
     * @param bytes byte[]
     * @return Object
     */
    private static Object castBytes(Class<?> clazz, byte[] bytes) {
        switch (clazz.getTypeName()) {
            case "byte[]": {
                return bytes;
            }
            case "java.lang.Void": {
                return null;
            }
            case "java.lang.String": {
                return new String(bytes, StandardCharsets.UTF_8);
            }
            case "boolean": {
                return Boolean.parseBoolean(new String(bytes, StandardCharsets.UTF_8));
            }
            case "java.lang.Boolean": {
                if (bytes.length == 0) {
                    return null;
                }
                return Boolean.parseBoolean(new String(bytes, StandardCharsets.UTF_8));
            }
            case "byte": {
                if (bytes.length == 1) {
                    return bytes[0];
                }
                throw new IndexOutOfBoundsException("byte类型只能接收 1 个字节，当前为 " + bytes.length + " 字节");
            }
            case "java.lang.Byte": {
                if (bytes.length == 0) {
                    return null;
                }
                if (bytes.length == 1) {
                    return bytes[0];
                }
                throw new IndexOutOfBoundsException("Byte类型只能接收 1 个字节，当前为 " + bytes.length + " 字节");
            }
            case "char": {
                String string = new String(bytes, StandardCharsets.UTF_8);
                if (string.length() == 1) {
                    return string.charAt(0);
                }
                throw new IndexOutOfBoundsException("char类型只能接收 1 个字符，当前为 " + string.length() + " 字符");
            }
            case "java.lang.Character": {
                if (bytes.length == 0) {
                    return null;
                }
                String string = new String(bytes, StandardCharsets.UTF_8);
                if (string.length() == 1) {
                    return string.charAt(0);
                }
                throw new IndexOutOfBoundsException("Character类型只能接收 1 个字符，当前为 " + string.length() + " 字符");
            }
            case "short": {
                return Short.parseShort(new String(bytes, StandardCharsets.UTF_8));
            }
            case "java.lang.Short": {
                if (bytes.length == 0) {
                    return null;
                }
                return Short.parseShort(new String(bytes, StandardCharsets.UTF_8));
            }
            case "int": {
                return Integer.parseInt(new String(bytes, StandardCharsets.UTF_8));
            }
            case "java.lang.Integer": {
                if (bytes.length == 0) {
                    return null;
                }
                return Integer.parseInt(new String(bytes, StandardCharsets.UTF_8));
            }
            case "long": {
                return Long.parseLong(new String(bytes, StandardCharsets.UTF_8));
            }
            case "java.lang.Long": {
                if (bytes.length == 0) {
                    return null;
                }
                return Long.parseLong(new String(bytes, StandardCharsets.UTF_8));
            }
            case "float": {
                return Float.parseFloat(new String(bytes, StandardCharsets.UTF_8));
            }
            case "java.lang.Float": {
                if (bytes.length == 0) {
                    return null;
                }
                return Float.parseFloat(new String(bytes, StandardCharsets.UTF_8));
            }
            case "double": {
                return Double.parseDouble(new String(bytes, StandardCharsets.UTF_8));
            }
            case "java.lang.Double": {
                if (bytes.length == 0) {
                    return null;
                }
                return Double.parseDouble(new String(bytes, StandardCharsets.UTF_8));
            }
            default: {
                try {
                    return clazz.getConstructor(byte[].class).newInstance(bytes);
                } catch (Exception e) {
                    throw new MqttException("无法使用值 " + Arrays.toString(bytes) + " 实例化类 " + clazz + " 的入参为 byte[] 的构造方法");
                }
            }
        }
    }

    /**
     * String转换
     *
     * <p>
     * <code>String</code>类型：返回本身<br>
     * <code>Void</code>类型：返回<code>null</code><br>
     * 8种基本类型的包装类型：当参数<code>String</code>的长度为<code>0</code>时返回<code>null</code><br>
     * 8种基本类型及包装类型：先转为<code>UTF-8</code>字符串，再转为对应类型<br>
     * 自定义类型：调用它的入参为<code>String</code>的构造函数并返回
     * </p>
     *
     * @param clazz  Class
     * @param string String
     * @return Object
     */
    private static Object castString(Class<?> clazz, String string) {
        switch (clazz.getTypeName()) {
            case "java.lang.String": {
                return string;
            }
            case "java.lang.Void": {
                return null;
            }
            case "boolean": {
                return Boolean.parseBoolean(string);
            }
            case "java.lang.Boolean": {
                if (string.length() == 0) {
                    return null;
                }
                return Boolean.parseBoolean(string);
            }
            case "byte": {
                return Byte.parseByte(string);
            }
            case "java.lang.Byte": {
                if (string.length() == 0) {
                    return null;
                }
                return Byte.parseByte(string);
            }
            case "char": {
                if (string.length() == 1) {
                    return string.charAt(0);
                }
                throw new IndexOutOfBoundsException("char类型只能接收 1 个字符，当前为 " + string.length() + " 字符");
            }
            case "java.lang.Character": {
                if (string.length() == 0) {
                    return null;
                }
                if (string.length() == 1) {
                    return string.charAt(0);
                }
                throw new IndexOutOfBoundsException("Character类型只能接收 1 个字符，当前为 " + string.length() + " 字符");
            }
            case "short": {
                return Short.parseShort(string);
            }
            case "java.lang.Short": {
                if (string.length() == 0) {
                    return null;
                }
                return Short.parseShort(string);
            }
            case "int": {
                return Integer.parseInt(string);
            }
            case "java.lang.Integer": {
                if (string.length() == 0) {
                    return null;
                }
                return Integer.parseInt(string);
            }
            case "long": {
                return Long.parseLong(string);
            }
            case "java.lang.Long": {
                if (string.length() == 0) {
                    return null;
                }
                return Long.parseLong(string);
            }
            case "float": {
                return Float.parseFloat(string);
            }
            case "java.lang.Float": {
                if (string.length() == 0) {
                    return null;
                }
                return Float.parseFloat(string);
            }
            case "double": {
                return Double.parseDouble(string);
            }
            case "java.lang.Double": {
                if (string.length() == 0) {
                    return null;
                }
                return Double.parseDouble(string);
            }
            default: {
                try {
                    return clazz.getConstructor(String.class).newInstance(string);
                } catch (Exception e) {
                    throw new MqttException("无法使用值 " + string + " 实例化类 " + clazz + " 的入参为 String 的构造方法");
                }
            }
        }
    }

}
