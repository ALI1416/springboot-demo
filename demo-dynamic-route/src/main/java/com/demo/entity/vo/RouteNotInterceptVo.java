package com.demo.entity.vo;

import com.demo.entity.po.RouteNotIntercept;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h1>路由-不拦截</h1>
 *
 * <p>
 * createDate 2021/12/01 10:13:49
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Schema(description = "路由-不拦截")
public class RouteNotInterceptVo extends RouteNotIntercept {

    /**
     * 匹配路径
     */
    @Schema(description = "匹配路径")
    private List<String> match;
    /**
     * 直接路径
     */
    @Schema(description = "直接路径")
    private List<String> direct;
    /**
     * 匹配路径(需要登录)
     */
    @Schema(description = "匹配路径(需要登录)")
    private List<String> loginMatch;
    /**
     * 直接路径(需要登录)
     */
    @Schema(description = "直接路径(需要登录)")
    private List<String> loginDirect;

}
