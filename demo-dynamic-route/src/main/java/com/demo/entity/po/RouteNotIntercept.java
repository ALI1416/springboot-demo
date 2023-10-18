package com.demo.entity.po;

import com.demo.base.EntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>路由-不拦截</h1>
 *
 * <p>
 * createDate 2021/11/26 09:36:03
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Schema(description = "路由-不拦截")
public class RouteNotIntercept extends EntityBase {

    /**
     * 路径
     */
    @Schema(description = "路径")
    private String path;
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;
    /**
     * 匹配模式
     */
    @Schema(description = "匹配模式")
    private Boolean isMatch;
    /**
     * 需要登录
     */
    @Schema(description = "需要登录")
    private Boolean needLogin;
    /**
     * 顺序
     */
    @Schema(description = "顺序")
    private Integer seq;

}
