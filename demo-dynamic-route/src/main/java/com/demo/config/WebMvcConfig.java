package com.demo.config;

import cn.z.spring.tool.FastJsonHttpMessageConverter;
import com.demo.constant.FormatConstant;
import com.demo.interceptor.RouteInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <h1>WebMvc配置</h1>
 *
 * <p>
 * createDate 2023/03/03 13:13:13
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final RouteInterceptor routeInterceptor;

    /**
     * 消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new FastJsonHttpMessageConverter(FormatConstant.DATE, FormatConstant.JSON_READER_FEATURE, FormatConstant.JSON_WRITER_FEATURE));
    }

    /**
     * 路由拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(routeInterceptor) // 路由拦截器
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns("/") // 排除首页
                .excludePathPatterns("/error") // 排除404
                .excludePathPatterns("/favicon.ico") // 排除图标
                .excludePathPatterns("/swagger-ui/**") // 排除swagger网页
                .excludePathPatterns("/v3/**") // 排除swagger接口
                .excludePathPatterns("/webjars/**") // 排除knife4j网页
                .excludePathPatterns("/doc.html") // 排除knife4j网页
        ;
    }

    /**
     * 全局跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有路径
                .allowCredentials(true) // 放行Cookie
                .allowedOriginPatterns("*") // 放行所有原始域
                .allowedMethods("*") // 放行全部请求方式
                .allowedHeaders("*") // 放行全部原始请求头部信息
                .exposedHeaders("*") // 暴露全部原始请求头部信息
        ;
    }

}
