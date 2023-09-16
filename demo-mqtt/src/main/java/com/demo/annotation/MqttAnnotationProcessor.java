package com.demo.annotation;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Configuration
@Slf4j
public class MqttAnnotationProcessor implements BeanPostProcessor {

    /**
     * MQTT缓存
     */
    private final MqttCache mqttCache;

    /**
     * 构造函数(自动注入)
     *
     * @param mqttCache MqttCache
     */
    public MqttAnnotationProcessor(MqttCache mqttCache) {
        this.mqttCache = mqttCache;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe != null) {
                mqttCache.addTopic(subscribe.value());
                mqttCache.addQos(subscribe.qos());
                String[] subscribePartArray = subscribe.value().split("/");
                // 订阅主题分段列表 位置 +true/#false
                List<Map.Entry<Integer, Boolean>> subscribePartList = new ArrayList<>();
                for (int i = 0; i < subscribePartArray.length; i++) {
                    if ("+".equals(subscribePartArray[i])) {
                        subscribePartList.add(new AbstractMap.SimpleEntry<>(i, true));
                    } else if ("#".equals(subscribePartArray[i])) {
                        subscribePartList.add(new AbstractMap.SimpleEntry<>(i, false));
                    }
                }
                mqttCache.addCallback(callback(bean, method, subscribePartList));
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
            throw new MqttException("方法 " + method + " 至少要有一个参数");
        }
        List<MqttFunction> mqttFunctionList = new ArrayList<>();
        boolean useAnnotation = false;
        for (int i = 0; i < parameters.length; i++) {
            // 第三个参数及以后都必须带注解
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
                try {
                    useAnnotation(mqttFunctionList, parameter, subscribePartList, header);
                } catch (Exception e) {
                    throw new MqttException(e + "\n方法 " + method + " 不合法");
                }
            }
        }
        return (topic, message) -> {
            List<Object> objectList = new ArrayList<>();
            for (MqttFunction mqttFunction : mqttFunctionList) {
                if (mqttFunction instanceof MqttFunctionMsg) {
                    objectList.add(mqttFunction.run(message));
                } else {
                    objectList.add(mqttFunction.run(topic));
                }
            }
            try {
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
     * @param parameter         Parameter
     * @param subscribePartList 订阅主题分段列表
     * @param header            Header
     */
    private static void useAnnotation(List<MqttFunction> mqttFunctionList, Parameter parameter, List<Map.Entry<Integer, Boolean>> subscribePartList, Header header) {
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
                topicPartHandle(mqttFunctionList, parameter, subscribePartList, header.index());
                break;
            }
            case ID: {
                MqttFunctionMsg mqttFunctionMsg = MqttMessage::getId;
                mqttFunctionList.add(mqttFunctionMsg);
                break;
            }
            case QOS: {
                MqttFunctionMsg mqttFunctionMsg = MqttMessage::getQos;
                mqttFunctionList.add(mqttFunctionMsg);
                break;
            }
            case RETAIN: {
                MqttFunctionMsg mqttFunctionMsg = MqttMessage::isRetained;
                mqttFunctionList.add(mqttFunctionMsg);
                break;
            }
            case DUPLICATE: {
                MqttFunctionMsg mqttFunctionMsg = MqttMessage::isDuplicate;
                mqttFunctionList.add(mqttFunctionMsg);
                break;
            }
        }
    }

    /**
     * 主题片段处理
     *
     * @param mqttFunctionList  List MqttFunction
     * @param parameter         Parameter
     * @param subscribePartList 订阅主题分段列表
     * @param index             主题片段位置
     */
    private static void topicPartHandle(List<MqttFunction> mqttFunctionList, Parameter parameter, List<Map.Entry<Integer, Boolean>> subscribePartList, int index) {
        Map.Entry<Integer, Boolean> entry = subscribePartList.get(index);
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
                throw new MqttException("参数 " + parameter + " 不能是数组类型");
            }
        } else {
            if (isArray) {
                MqttFunctionTopic mqttFunctionTopic = topic -> {
                    String[] topicPart = topic.split("/");
                    Object[] objectArray = new Object[topicPart.length - topicPartIndex];
                    for (int i = 0; i < objectArray.length; i++) {
                        objectArray[i] = castString(parameter.getType(), topicPart[i + topicPartIndex]);
                    }
                    return objectArray;
                };
                mqttFunctionList.add(mqttFunctionTopic);
            } else {
                throw new MqttException("参数 " + parameter + " 必须是数组类型");
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
        MqttFunctionMsg mqttFunctionMsg = msg -> castBytes(parameter.getType(), msg.getPayload());
        mqttFunctionList.add(mqttFunctionMsg);
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
     * 支持<code>byte[]</code>、<code>Void</code>、<code>String</code>、8种基本类型以及它的包装类型、自定义类型<br>
     * 其中<code>byte[]</code>原样返回<br>
     * <code>Void</code>返回null<br>
     * 自定义类型调用它的单参<code>byte[]</code>的构造函数并返回<br>
     * 其它类型，先转为UTF-8字符串，再转为对应类型<br>
     * 转换失败返回null
     * </p>
     *
     * @param clazz Class
     * @param bytes byte[]
     * @return Object
     */
    private static Object castBytes(Class<?> clazz, byte[] bytes) {
        try {
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
                case "boolean":
                case "java.lang.Boolean": {
                    return Boolean.parseBoolean(new String(bytes, StandardCharsets.UTF_8));
                }
                case "byte":
                case "java.lang.Byte": {
                    if (bytes.length == 1) {
                        return bytes[0];
                    }
                    throw new IndexOutOfBoundsException("byte或Byte类型只能接收一个字节");
                }
                case "char":
                case "java.lang.Character": {
                    String string = new String(bytes, StandardCharsets.UTF_8);
                    if (string.length() == 1) {
                        return string.charAt(0);
                    }
                    throw new IndexOutOfBoundsException("char或Character类型只能接收一个字符");
                }
                case "short":
                case "java.lang.Short": {
                    return Short.parseShort(new String(bytes, StandardCharsets.UTF_8));
                }
                case "int":
                case "java.lang.Integer": {
                    return Integer.parseInt(new String(bytes, StandardCharsets.UTF_8));
                }
                case "long":
                case "java.lang.Long": {
                    return Long.parseLong(new String(bytes, StandardCharsets.UTF_8));
                }
                case "float":
                case "java.lang.Float": {
                    return Float.parseFloat(new String(bytes, StandardCharsets.UTF_8));
                }
                case "double":
                case "java.lang.Double": {
                    return Double.parseDouble(new String(bytes, StandardCharsets.UTF_8));
                }
                default: {
                    return clazz.getConstructor(byte[].class).newInstance(bytes);
                }
            }
        } catch (Exception e) {
            log.error("byte[]转换为" + clazz.getTypeName() + "失败", e);
            return null;
        }
    }

    /**
     * String转换
     *
     * <p>
     * 支持<code>Void</code>、<code>String</code>、8种基本类型以及它的包装类型、自定义类型<br>
     * 其中<code>String</code>原样返回<br>
     * <code>Void</code>返回null<br>
     * 自定义类型调用它的单参<code>String</code>的构造函数并返回<br>
     * 转换失败返回null
     * </p>
     *
     * @param clazz  Class
     * @param string String
     * @return Object
     */
    private static Object castString(Class<?> clazz, String string) {
        try {
            switch (clazz.getTypeName()) {
                case "java.lang.String": {
                    return string;
                }
                case "java.lang.Void": {
                    return null;
                }
                case "boolean":
                case "java.lang.Boolean": {
                    return Boolean.parseBoolean(string);
                }
                case "byte":
                case "java.lang.Byte": {
                    return Byte.parseByte(string);
                }
                case "char":
                case "java.lang.Character": {
                    if (string.length() == 1) {
                        return string.charAt(0);
                    }
                    throw new IndexOutOfBoundsException("char或Character类型只能接收一个字符");
                }
                case "short":
                case "java.lang.Short": {
                    return Short.parseShort(string);
                }
                case "int":
                case "java.lang.Integer": {
                    return Integer.parseInt(string);
                }
                case "long":
                case "java.lang.Long": {
                    return Long.parseLong(string);
                }
                case "float":
                case "java.lang.Float": {
                    return Float.parseFloat(string);
                }
                case "double":
                case "java.lang.Double": {
                    return Double.parseDouble(string);
                }
                default: {
                    return clazz.getConstructor(String.class).newInstance(string);
                }
            }
        } catch (Exception e) {
            log.error("String转换为" + clazz.getTypeName() + "失败", e);
            return null;
        }
    }

}
