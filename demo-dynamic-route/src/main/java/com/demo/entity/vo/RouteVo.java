package com.demo.entity.vo;

import com.demo.entity.po.Route;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h1>路由</h1>
 *
 * <p>
 * createDate 2021/11/26 10:14:02
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Schema(description = "路由")
public class RouteVo extends Route {

    /**
     * 子节点
     */
    @Schema(description = "子节点")
    private List<RouteVo> child;
    /**
     * 匹配路径
     */
    @Schema(description = "匹配路径")
    private List<RouteVo> match;
    /**
     * 直接路径
     */
    @Schema(description = "直接路径")
    private List<RouteVo> direct;
    /**
     * 匹配路径(仅路径)
     */
    @Schema(description = "匹配路径(仅路径)")
    private List<String> matchPath;
    /**
     * 直接路径(仅路径)
     */
    @Schema(description = "直接路径(仅路径)")
    private List<String> directPath;

}
