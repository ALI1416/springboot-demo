package com.demo.entity.vo;

import com.demo.entity.po.Route2;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h1>Route2Vo</h1>
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
public class Route2Vo extends Route2 {

    /**
     * 所有子节点
     */
    private List<Route2Vo> children;
    /**
     * 是可以匹配的路径
     */
    private List<Route2Vo> matcher;
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
    private List<Route2Vo> direct;
    /**
     * 删除该节点时。true：删除子节点；false：不删除，移动子节点到该节点的父节点
     */
    private Boolean deleteChildren;
    /**
     * 移动到哪个id下方
     */
    private Long moveId;

}
