package cn.z.spring.tool;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * <h1>WebSocket消息转换器</h1>
 *
 * <p>
 * 根据<a href="https://github.com/alibaba/fastjson2/blob/2.0.42/extension-spring5/src/main/java/com/alibaba/fastjson2/support/spring/messaging/converter/MappingFastJsonMessageConverter.java">alibaba/fastjson2</a>重构
 * </p>
 *
 * <p>
 * createDate 2023/11/24 15:47:09
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class FastJsonMessageConverter extends AbstractMessageConverter {

    /**
     * 日期
     */
    private final String date;
    /**
     * 读特性
     */
    private final JSONReader.Feature[] readerFeature;
    /**
     * 写特性
     */
    private final JSONWriter.Feature[] writerFeature;

    /**
     * 构造函数
     *
     * @param date          日期
     * @param readerFeature 读特性
     * @param writerFeature 写特性
     */
    public FastJsonMessageConverter(String date, JSONReader.Feature[] readerFeature, JSONWriter.Feature[] writerFeature) {
        super(MediaType.APPLICATION_JSON);
        this.date = date;
        this.readerFeature = readerFeature;
        this.writerFeature = writerFeature;
    }

    /**
     * 支持转换的类
     *
     * @param clazz 类
     * @return 支持转换所有类
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 消息转对象
     *
     * @param message        消息
     * @param targetClass    目标类
     * @param conversionHint 额外对象
     * @return 对象
     */
    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Type type = getType(targetClass, conversionHint);
        Object payload = message.getPayload();
        if (payload instanceof byte[]) {
            // byte[]类型消息
            return JSON.parseObject((byte[]) payload, type, date, readerFeature);
        } else {
            // 字符串类型消息
            return JSON.parseObject((String) payload, type, date, readerFeature);
        }
    }

    /**
     * 对象转消息
     *
     * @param payload        对象
     * @param headers        消息头
     * @param conversionHint 额外对象
     * @return 消息
     */
    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        if (byte[].class == getSerializedPayloadClass()) {
            // 转为byte[]类型消息
            if (payload instanceof String) {
                // 字符串类型对象
                return ((String) payload).getBytes(StandardCharsets.UTF_8);
            } else {
                // byte[]类型消息
                return JSON.toJSONBytes(payload, date, writerFeature);
            }
        } else {
            // 转为字符串类型消息
            if (payload instanceof String) {
                // 字符串类型对象
                return payload;
            } else {
                // byte[]类型消息
                return JSON.toJSONString(payload, date, writerFeature);
            }
        }
    }

    /**
     * 获取类型
     *
     * @param targetClass    目标类
     * @param conversionHint 额外对象
     * @return 类型
     */
    private static Type getType(Class<?> targetClass, Object conversionHint) {
        if (conversionHint instanceof MethodParameter) {
            MethodParameter param = (MethodParameter) conversionHint;
            param = param.nestedIfOptional();
            if (Message.class.isAssignableFrom(param.getParameterType())) {
                param = param.nested();
            }
            Type genericParameterType = param.getNestedGenericParameterType();
            Class<?> contextClass = param.getContainingClass();
            return GenericTypeResolver.resolveType(genericParameterType, contextClass);
        }
        return targetClass;
    }

}
