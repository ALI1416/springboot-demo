package com.demo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>消息转换器配置</h1>
 *
 * <p>
 * createDate 2021/01/11 19:53:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
public class HttpMessageConvertersConfig {

    /**
     * 替换成FastJson
     */
    @Bean
    public HttpMessageConverters fastJsonMessageConverters() {
        // 消息转换器对象
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        // FastJson消息转换对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // FastJson配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 序列化设置
        fastJsonConfig.setSerializerFeatures(//
                SerializerFeature.DisableCircularReferenceDetect, // 禁用对象循环引用：避免$ref
                SerializerFeature.WriteNonStringValueAsString// 非String转为String：防止long丢失精度
        );
        // 日期设置
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 编码设置
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        // MediaType配置
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.ALL);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(0, fastConverter);
        return new HttpMessageConverters(converters);
    }
}
