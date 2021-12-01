package com.demo.config;

import com.demo.interceptor.RouteInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
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
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(routeInterceptor) // 路由拦截器
                .addPathPatterns("/**") // 拦截所有页面
                .excludePathPatterns("/") // 排除首页
                .excludePathPatterns("/error") // 排除404
                .excludePathPatterns("/favicon.ico") // 排除图标
        ;
    }

}
