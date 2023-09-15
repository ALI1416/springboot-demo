package com.demo.annotation;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
                mqttCache.addCallback(callback(bean, method));
            }
        }
        return bean;
    }

    /**
     * 回调
     *
     * @param object Object
     * @param method Method
     * @return IMqttMessageListener
     */
    public static IMqttMessageListener callback(Object object, Method method) {
        Parameter[] parameters = method.getParameters();
        List<MqttFunction> mqttFunctionList = new ArrayList<>();
        switch (parameters.length) {
            case 0: {
                throw new MqttException("方法 " + method + " 至少要有一个参数");
            }
            case 1: {
                Parameter parameter = parameters[0];
                Header header = parameter.getAnnotation(Header.class);
                if (header == null) {
                    // 没有注解：msg
                    Class<?> clazz = parameter.getType();
                    MqttFunctionMsg mqttFunctionMsg = msg -> castMsg(clazz, msg.getPayload());
                    mqttFunctionList.add(mqttFunctionMsg);
                } else {
                    // 注解
                }
                break;
            }
            case 2: {
                Parameter parameter0 = parameters[0];
                Parameter parameter1 = parameters[1];
                Header header0 = parameter0.getAnnotation(Header.class);
                Header header1 = parameter1.getAnnotation(Header.class);
                if (header0 == null && header1 == null) {
                    // 没有注解：msg、topic
                    Class<?> clazz = parameter0.getType();
                    MqttFunctionMsg mqttFunctionMsg = msg -> castMsg(clazz, msg.getPayload());
                    mqttFunctionList.add(mqttFunctionMsg);
                    MqttFunctionTopic mqttFunctionTopic = topic -> topic;
                    mqttFunctionList.add(mqttFunctionTopic);
                } else {
                    // 注解
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
                log.error("方法调用失败", e);
            }
        };
    }

    /**
     * 消息转换
     *
     * <p>
     * 支持<code>byte[]</code>、<code>Void</code>、<code>String</code>、8种基本类型以及它的包装类型、自定义类型<br>
     * 其中<code>byte[]</code>原样返回<br>
     * <code>Void</code>返回null<br>
     * 自定义类型调用它的单参<code>byte[]</code>的构造函数并返回<br>
     * 其它类型，先转为UTF-8字符串，再转为对应类型
     * </p>
     *
     * @param clazz Class
     * @param msg   消息
     * @return Object
     */
    public static Object castMsg(Class<?> clazz, byte[] msg) {
        try {
            switch (clazz.getTypeName()) {
                case "byte[]": {
                    return msg;
                }
                case "java.lang.Void": {
                    return null;
                }
                case "java.lang.String": {
                    return new String(msg, StandardCharsets.UTF_8);
                }
                case "int":
                case "java.lang.Integer": {
                    return Integer.parseInt(new String(msg, StandardCharsets.UTF_8));
                }
                case "long":
                case "java.lang.Long": {
                    return Long.parseLong(new String(msg, StandardCharsets.UTF_8));
                }
                default: {
                    return clazz.getConstructor(byte[].class).newInstance(msg);
                }
            }
        } catch (Exception e) {
            log.error("消息转换失败", e);
            return null;
        }
    }

}
