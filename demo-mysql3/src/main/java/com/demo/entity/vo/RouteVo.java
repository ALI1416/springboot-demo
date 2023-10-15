package com.demo.entity.vo;

import com.demo.entity.po.Route;
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
public class RouteVo extends Route {

    /**
     * 所有子节点
     */
    private List<RouteVo> children;
    /**
     * 是可以匹配的路径
     */
    private List<RouteVo> matcher;
    /**
     * 是不可匹配的路径(仅路径)
     */
    private List<String> directPath;
    /**
     * 是可以匹配的路径(仅路径)
     */
    private List<String> matcherPath;
    /**
     * 是不可匹配的路径
     */
    private List<RouteVo> direct;

}
