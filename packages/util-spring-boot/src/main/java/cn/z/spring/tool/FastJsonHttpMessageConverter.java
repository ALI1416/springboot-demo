package cn.z.spring.tool;

import com.alibaba.fastjson2.*;
import com.alibaba.fastjson2.filter.Filter;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * <h1>Http消息转换器</h1>
 *
 * <p>
 * 根据<a href="https://github.com/alibaba/fastjson2/blob/2.0.42/extension-spring5/src/main/java/com/alibaba/fastjson2/support/spring/http/converter/FastJsonHttpMessageConverter.java">alibaba/fastjson2</a>重构
 * </p>
 *
 * <p>
 * createDate 2023/11/24 16:34:46
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object> {

    private static final Filter[] FILTERS = new Filter[0];
    private static final MediaType APPLICATION_JAVASCRIPT = new MediaType("application", "javascript");

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
    public FastJsonHttpMessageConverter(String date, JSONReader.Feature[] readerFeature, JSONWriter.Feature[] writerFeature) {
        super(StandardCharsets.UTF_8, MediaType.APPLICATION_JSON);
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
     * 可以读取类型
     *
     * @param type         类型
     * @param contextClass 上下文类
     * @param mediaType    媒体类型
     * @return 是否可以
     */
    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return super.canRead(contextClass, mediaType);
    }

    /**
     * 可以写入类型
     *
     * @param type      类型
     * @param clazz     类
     * @param mediaType 媒体类型
     * @return 是否可以
     */
    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return super.canWrite(clazz, mediaType);
    }

    /**
     * 消息转对象
     *
     * @param type         类型
     * @param contextClass 上下文类
     * @param inputMessage 消息
     * @return 对象
     */
    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return read(getType(type, contextClass), inputMessage);
    }

    /**
     * 对象转消息
     *
     * @param object        对象
     * @param type          类型
     * @param contentType   媒体类型
     * @param outputMessage 消息
     */
    @Override
    public void write(Object object, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.write(object, contentType, outputMessage);
    }

    /**
     * 消息转对象(内部)
     *
     * @param clazz        类
     * @param inputMessage 消息
     * @return 对象
     */
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return read(getType(clazz, null), inputMessage);
    }

    /**
     * 对象转消息(内部)
     *
     * @param object        对象
     * @param outputMessage 消息
     */
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
        HttpHeaders headers = outputMessage.getHeaders();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int contentLength;
            if (object instanceof String string) {
                byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
                contentLength = bytes.length;
                outputMessage.getBody().write(bytes, 0, bytes.length);
            } else if (object instanceof byte[] bytes) {
                contentLength = bytes.length;
                outputMessage.getBody().write(bytes, 0, bytes.length);
            } else {
                if (object instanceof JSONPObject) {
                    headers.setContentType(APPLICATION_JAVASCRIPT);
                }
                contentLength = JSON.writeTo(baos, object, date, FILTERS, writerFeature);
            }
            if (headers.getContentLength() < 0) {
                headers.setContentLength(contentLength);
            }
            baos.writeTo(outputMessage.getBody());
        } catch (JSONException e) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new HttpMessageNotWritableException("I/O error while writing output message", e);
        }
    }

    /**
     * 消息转对象
     *
     * @param type         类型
     * @param inputMessage 消息
     * @return 对象
     */
    private Object read(Type type, HttpInputMessage inputMessage) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            InputStream inputStream = inputMessage.getBody();
            byte[] buffer = new byte[1024 * 64];
            for (; ; ) {
                int length = inputStream.read(buffer);
                if (length == -1) {
                    break;
                }
                if (length > 0) {
                    baos.write(buffer, 0, length);
                }
            }
            return JSON.parseObject(baos.toByteArray(), type, date, FILTERS, readerFeature);
        } catch (JSONException e) {
            throw new HttpMessageNotReadableException("JSON parse error: " + e.getMessage(), e, inputMessage);
        } catch (IOException e) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", e, inputMessage);
        }
    }

    /**
     * 获取类型
     *
     * @param type         类型
     * @param contextClass 上下文类
     * @return 类型
     */
    private static Type getType(Type type, Class<?> contextClass) {
        return GenericTypeResolver.resolveType(type, contextClass);
    }

}
