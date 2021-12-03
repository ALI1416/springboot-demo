package com.demo.config;

import com.demo.interceptor.RouteInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h1>Web配置</h1>
 *
 * <p>
 * createDate 2020/12/06 10:00:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RouteInterceptor routeInterceptor;

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
