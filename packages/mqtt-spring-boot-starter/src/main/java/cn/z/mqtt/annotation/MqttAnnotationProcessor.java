package cn.z.mqtt.annotation;

import cn.z.mqtt.MqttException;
import cn.z.mqtt.SslUtil;
import cn.z.mqtt.autoconfigure.MqttProperties;
import cn.z.mqtt.autoconfigure.MqttSslProperties;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.function.Function;

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
public class MqttAnnotationProcessor implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(MqttAnnotationProcessor.class);

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

    /**
     * MQTT客户端
     */
    private final MqttClient mqttClient;

    /**
     * 获取MQTT客户端
     */
    public MqttClient getMqttClient() {
        return mqttClient;
    }

    /**
     * 构造函数(自动注入)
     *
     * @param mqttProperties    MqttProperties
     * @param mqttSslProperties MqttSslProperties
     */
    public MqttAnnotationProcessor(MqttProperties mqttProperties, MqttSslProperties mqttSslProperties) throws org.eclipse.paho.client.mqttv3.MqttException, UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        mqttClient = new MqttClient(mqttProperties.getUri(), getRandomClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        if (mqttProperties.getUsername() != null && !mqttProperties.getUsername().isEmpty() && mqttProperties.getPassword() != null && !mqttProperties.getPassword().isEmpty()) {
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
        }
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        options.setCleanSession(mqttProperties.isCleanSession());
        options.setAutomaticReconnect(mqttProperties.isAutomaticReconnect());
        if (mqttSslProperties.isEnable()) {
            options.setHttpsHostnameVerificationEnabled(mqttSslProperties.isCheck());
            if (mqttSslProperties.isCaServerAutoSign()) {
                options.setSocketFactory(SslUtil.getSocketFactory());
            } else {
                InputStream caCrt;
                if (mqttSslProperties.getCaCrtResourcePath() != null) {
                    caCrt = new ClassPathResource(mqttSslProperties.getCaCrtResourcePath()).getInputStream();
                } else if (mqttSslProperties.getCaCrtLocalPath() != null) {
                    caCrt = new FileInputStream(mqttSslProperties.getCaCrtLocalPath());
                } else {
                    throw new MqttException("缺失CA证书！");
                }
                InputStream clientCrt;
                if (mqttSslProperties.getClientCrtResourcePath() != null) {
                    clientCrt = new ClassPathResource(mqttSslProperties.getClientCrtResourcePath()).getInputStream();
                } else if (mqttSslProperties.getClientCrtLocalPath() != null) {
                    clientCrt = new FileInputStream(mqttSslProperties.getClientCrtLocalPath());
                } else {
                    throw new MqttException("缺失客户端证书！");
                }
                InputStream clientKey;
                if (mqttSslProperties.getClientKeyResourcePath() != null) {
                    clientKey = new ClassPathResource(mqttSslProperties.getClientKeyResourcePath()).getInputStream();
                } else if (mqttSslProperties.getClientKeyLocalPath() != null) {
                    clientKey = new FileInputStream(mqttSslProperties.getClientKeyLocalPath());
                } else {
                    throw new MqttException("缺失客户端密钥！");
                }
                options.setSocketFactory(SslUtil.getSocketFactory(caCrt, clientCrt, clientKey));
            }
        }
        mqttClient.setCallback(new MqttCallbackExtended() {

            /**
             * 连接成功
             *
             * @param reconnect 重连
             * @param uri       URI
             */
            @Override
            public void connectComplete(boolean reconnect, String uri) {
                if (reconnect) {
                    log.info("MQTT重连成功");
                    // 重连后恢复订阅
                    subscribe();
                } else {
                    log.info("MQTT连接成功");
                }
            }

            /**
             * 失去连接
             *
             * @param cause Throwable
             */
            @Override
            public void connectionLost(Throwable cause) {
                log.error("MQTT失去连接", cause);
            }

            /**
             * 消息到达
             *
             * @param topic   主题
             * @param message MqttMessage
             */
            @Override
            public void messageArrived(String topic, MqttMessage message) {
            }

            /**
             * 消息发送成功
             *
             * @param token IMqttDeliveryToken
             */
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

        });
        mqttClient.connect(options);
    }

    /**
     * 随机数实例
     */
    private static final Random RANDOM = new Random();
    /**
     * 所有数字和字母
     */
    private static final char[] NUMBER_ALL_LETTER = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * 获取随机客户端ID
     */
    private String getRandomClientId() {
        StringBuilder sb = new StringBuilder("mqtt_");
        for (int i = 0; i < 5; i++) {
            sb.append(NUMBER_ALL_LETTER[RANDOM.nextInt(62)]);
        }
        return sb.toString();
    }

    /**
     * 订阅
     */
    private void subscribe() {
        if (!topicList.isEmpty()) {
            try {
                mqttClient.subscribeWithResponse( //
                        topicList.toArray(new String[0]), //
                        qosList.stream().mapToInt(Integer::intValue).toArray(), //
                        callbackList.toArray(new IMqttMessageListener[0]) //
                );
            } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
                throw new MqttException("订阅失败", e);
            }
        }
    }

    /**
     * ApplicationContext
     */
    private ApplicationContext applicationContext;

    /**
     * ApplicationContextAware
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * SmartInitializingSingleton
     */
    @Override
    public void afterSingletonsInstantiated() {
        if (applicationContext == null) {
            throw new MqttException("找不到 ApplicationContext");
        }
        // 所有Bean
        String[] beanNamesForTypeArray = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanNamesForType : beanNamesForTypeArray) {
            // 跳过@Lazy注解
            if (applicationContext.findAnnotationOnBean(beanNamesForType, Lazy.class) == null) {
                Object bean = applicationContext.getBean(beanNamesForType);
                // 含有@Subscribe注解的方法
                Map<Method, Subscribe> annotatedMethodMap = MethodIntrospector.selectMethods(
                        bean.getClass(),
                        (MethodIntrospector.MetadataLookup<Subscribe>) method ->
                                AnnotatedElementUtils.findMergedAnnotation(method, Subscribe.class));
                annotatedMethodMap.forEach((method, subscribe) -> processSubscribe(bean, method, subscribe));
            }
        }
        // 订阅
        subscribe();
    }

    /**
     * DisposableBean
     */
    @Override
    public void destroy() throws Exception {
        mqttClient.disconnect();
        mqttClient.close();
    }

    /**
     * 处理
     *
     * @param bean      Bean
     * @param method    Method
     * @param subscribe Subscribe
     */
    private void processSubscribe(Object bean, Method method, Subscribe subscribe) {
        topicList.add(subscribe.value());
        qosList.add(subscribe.qos());
        String[] subscribePartArray = subscribe.value().split("/", -1);
        // 订阅主题分段列表 位置,类型
        List<Map.Entry<Integer, Boolean>> partList = new ArrayList<>();
        for (int i = 0; i < subscribePartArray.length; i++) {
            if ("+".equals(subscribePartArray[i])) {
                partList.add(new AbstractMap.SimpleEntry<>(i, true));
            } else if ("#".equals(subscribePartArray[i])) {
                partList.add(new AbstractMap.SimpleEntry<>(i, false));
            }
        }
        callbackList.add(callback(bean, method, partList));
    }

    /**
     * 回调
     *
     * @param bean     Bean
     * @param method   Method
     * @param partList 订阅主题分段列表
     * @return IMqttMessageListener
     */
    private static IMqttMessageListener callback(Object bean, Method method, List<Map.Entry<Integer, Boolean>> partList) {
        Parameter[] parameters = method.getParameters();
        int parameterLength = parameters.length;
        boolean useAnnotation = false;
        Function[] functionArray = new Function[parameterLength];
        for (int i = 0; i < parameterLength; i++) {
            // 第三个及以后参数都必须带Header注解
            if (i == 2) {
                useAnnotation = true;
            }
            Parameter parameter = parameters[i];
            Header header = parameter.getAnnotation(Header.class);
            if (header == null) {
                // 没有用Header注解
                if (useAnnotation) {
                    throw new MqttException("方法 " + method + " 不合法");
                }
                notUseHeaderAnnotation(functionArray, parameter, i);
            } else {
                // 使用了Header注解，后面参数必须带Header注解
                useAnnotation = true;
                useHeaderAnnotation(functionArray, method, parameter, partList, header, i);
            }
        }
        Object[] objectArray = new Object[parameterLength];
        return (topic, message) -> {
            try {
                for (int i = 0; i < parameterLength; i++) {
                    Function function = functionArray[i];
                    if (function instanceof FunctionMessage) {
                        objectArray[i] = function.apply(message);
                    } else {
                        objectArray[i] = function.apply(topic);
                    }
                }
                method.invoke(bean, objectArray);
            } catch (Exception e) {
                log.error("方法 " + method + " 调用失败", e);
            }
        };
    }

    /**
     * 没有用Header注解
     *
     * @param functionArray Function[]
     * @param parameter     Parameter
     * @param index         位置
     */
    private static void notUseHeaderAnnotation(Function[] functionArray, Parameter parameter, int index) {
        if (index == 0) {
            addMsg(functionArray, parameter, index);
        } else {
            addTopic(functionArray, parameter, index, 10);
        }
    }

    /**
     * 使用了Header注解
     *
     * @param functionArray Function[]
     * @param method        Method
     * @param parameter     Parameter
     * @param partList      订阅主题分段列表
     * @param header        Header
     * @param index         位置
     */
    private static void useHeaderAnnotation(Function[] functionArray, Method method, Parameter parameter, List<Map.Entry<Integer, Boolean>> partList, Header header, int index) {
        switch (header.value()) {
            default:
            case MSG: {
                addMsg(functionArray, parameter, index);
                break;
            }
            case TOPIC: {
                addTopic(functionArray, parameter, index, header.radix());
                break;
            }
            case TOPIC_PART: {
                topicPartHandle(functionArray, method, parameter, partList, header.index(), index, header.radix());
                break;
            }
            case ID: {
                functionArray[index] = (FunctionMessage) MqttMessage::getId;
                break;
            }
            case QOS: {
                functionArray[index] = (FunctionMessage) MqttMessage::getQos;
                break;
            }
            case RETAIN: {
                functionArray[index] = (FunctionMessage) MqttMessage::isRetained;
                break;
            }
            case DUPLICATE: {
                functionArray[index] = (FunctionMessage) MqttMessage::isDuplicate;
                break;
            }
        }
    }

    /**
     * 主题片段处理
     *
     * @param functionArray Function[]
     * @param method        Method
     * @param parameter     Parameter
     * @param partList      订阅主题分段列表
     * @param partIndex     主题片段位置
     * @param index         位置
     * @param radix         基数
     */
    private static void topicPartHandle(Function[] functionArray, Method method, Parameter parameter, List<Map.Entry<Integer, Boolean>> partList, int partIndex, int index, int radix) {
        Map.Entry<Integer, Boolean> entry;
        try {
            entry = partList.get(partIndex);
        } catch (Exception e) {
            throw new MqttException("方法 " + method + " 的参数 " + parameter + " @Header注解的主题片段匹配位置的值 " + partIndex + " 超出最大值 " + (partList.size() - 1), e);
        }
        int topicPartIndex = entry.getKey();
        boolean isSingle = entry.getValue();
        boolean isArray = parameter.getType().isArray();
        if (isSingle) {
            if (!isArray) {
                functionArray[index] = (FunctionTopic) topic -> castString(parameter.getType(), topic.split("/", -1)[topicPartIndex], radix);
            } else {
                throw new MqttException("方法 " + method + " 的参数 " + parameter + " 不能是数组类型");
            }
        } else {
            if (isArray) {
                functionArray[index] = (FunctionTopic) topic -> {
                    String[] topicPart = topic.split("/", -1);
                    Class<?> parameterType = parameter.getType().getComponentType();
                    int arrayLength = topicPart.length - topicPartIndex;
                    Object topicArray = Array.newInstance(parameterType, arrayLength);
                    for (int i = 0; i < arrayLength; i++) {
                        Array.set(topicArray, i, castString(parameterType, topicPart[i + topicPartIndex], radix));
                    }
                    return topicArray;
                };
            } else {
                throw new MqttException("方法 " + method + " 的参数 " + parameter + " 必须是数组类型");
            }
        }
    }

    /**
     * 添加消息
     *
     * @param functionArray Function[]
     * @param parameter     Parameter
     * @param index         位置
     */
    private static void addMsg(Function[] functionArray, Parameter parameter, int index) {
        functionArray[index] = (FunctionMessage) msg -> castBytes(parameter.getType(), msg.getPayload());
    }

    /**
     * 添加主题
     *
     * @param functionArray Function[]
     * @param parameter     Parameter
     * @param index         位置
     * @param radix         基数
     */
    private static void addTopic(Function[] functionArray, Parameter parameter, int index, int radix) {
        functionArray[index] = (FunctionTopic) topic -> castString(parameter.getType(), topic, radix);
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
                throw new IndexOutOfBoundsException("byte类型只能接收 1 个字节，当前byte[] " + Arrays.toString(bytes) + " 为 " + bytes.length + " 个字节");
            }
            case "java.lang.Byte": {
                if (bytes.length == 0) {
                    return null;
                }
                if (bytes.length == 1) {
                    return bytes[0];
                }
                throw new IndexOutOfBoundsException("Byte类型只能接收 1 个字节，当前byte[] " + Arrays.toString(bytes) + " 为 " + bytes.length + " 个字节");
            }
            case "char": {
                String string = new String(bytes, StandardCharsets.UTF_8);
                if (string.length() == 1) {
                    return string.charAt(0);
                }
                throw new IndexOutOfBoundsException("char类型只能接收 1 个字符，当前字符串 " + string + " 为 " + string.length() + " 个字符");
            }
            case "java.lang.Character": {
                if (bytes.length == 0) {
                    return null;
                }
                String string = new String(bytes, StandardCharsets.UTF_8);
                if (string.length() == 1) {
                    return string.charAt(0);
                }
                throw new IndexOutOfBoundsException("Character类型只能接收 1 个字符，当前字符串 " + string + " 为 " + string.length() + " 个字符");
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
                    throw new MqttException("无法使用值 " + Arrays.toString(bytes) + " 实例化类 " + clazz + " 的入参为 byte[] 的构造方法", e);
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
     * @param radix  基数
     * @return Object
     */
    private static Object castString(Class<?> clazz, String string, int radix) {
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
                return Byte.parseByte(string, radix);
            }
            case "java.lang.Byte": {
                if (string.length() == 0) {
                    return null;
                }
                return Byte.parseByte(string, radix);
            }
            case "char": {
                if (string.length() == 1) {
                    return string.charAt(0);
                }
                throw new IndexOutOfBoundsException("char类型只能接收 1 个字符，当前字符串 " + string + " 为 " + string.length() + " 个字符");
            }
            case "java.lang.Character": {
                if (string.length() == 0) {
                    return null;
                }
                if (string.length() == 1) {
                    return string.charAt(0);
                }
                throw new IndexOutOfBoundsException("Character类型只能接收 1 个字符，当前字符串 " + string + " 为 " + string.length() + " 个字符");
            }
            case "short": {
                return Short.parseShort(string, radix);
            }
            case "java.lang.Short": {
                if (string.length() == 0) {
                    return null;
                }
                return Short.parseShort(string, radix);
            }
            case "int": {
                return Integer.parseInt(string, radix);
            }
            case "java.lang.Integer": {
                if (string.length() == 0) {
                    return null;
                }
                return Integer.parseInt(string, radix);
            }
            case "long": {
                return Long.parseLong(string, radix);
            }
            case "java.lang.Long": {
                if (string.length() == 0) {
                    return null;
                }
                return Long.parseLong(string, radix);
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
                    throw new MqttException("无法使用值 " + string + " 实例化类 " + clazz + " 的入参为 String 的构造方法", e);
                }
            }
        }
    }

}
