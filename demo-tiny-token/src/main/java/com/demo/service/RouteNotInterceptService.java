package com.demo.service;

import cn.z.redis.RedisTemp;
import cn.z.redis.annotation.Subscribe;
import com.demo.dao.mysql.RouteNotInterceptDao;
import com.demo.entity.po.RouteNotIntercept;
import com.demo.entity.vo.RouteNotInterceptVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>路由-不拦截Service</h1>
 *
 * <p>
 * createDate 2021/12/08 10:28:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class RouteNotInterceptService {

    private final RouteNotInterceptDao routeNotInterceptDao;
    private final RedisTemp redisTemp;

    /**
     * 更新"不拦截路径"键名
     */
    private static final String UPDATE_NOT_INTERCEPT = "updateNotIntercept";
    /**
     * 不拦截-匹配路径
     */
    private List<String> notInterceptMatch;
    /**
     * 不拦截-直接路径
     */
    private List<String> notInterceptDirect;

    /**
     * 查询所有
     *
     * @return List RouteNotInterceptVo
     */
    public List<RouteNotInterceptVo> findAll() {
        return routeNotInterceptDao.findAll();
    }

    /**
     * 插入
     *
     * @param routeNotIntercept path,name,isMatch,seq
     * @return ok:id,e:0
     */
    @Transactional
    public Long insert(RouteNotInterceptVo routeNotIntercept) {
        return routeNotInterceptDao.insert(routeNotIntercept);
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional
    public boolean delete(Long id) {
        return routeNotInterceptDao.delete(id);
    }

    /**
     * 更新
     *
     * @param routeNotIntercept id(必须),path,name,isMatch,seq(至少1个)
     * @return 是否成功
     */
    @Transactional
    public boolean update(RouteNotInterceptVo routeNotIntercept) {
        return routeNotInterceptDao.update(routeNotIntercept);
    }

    /**
     * 刷新
     */
    public void refresh() {
        redisTemp.broadcast(UPDATE_NOT_INTERCEPT, UPDATE_NOT_INTERCEPT);
    }

    /**
     * 是"不拦截-匹配路径"
     *
     * @param urlList URL列表
     * @return 是否
     */
    public boolean isNotInterceptMatch(List<String> urlList) {
        for (String url : urlList) {
            if (notInterceptMatch.contains(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是"不拦截-直接路径"
     *
     * @param url URL
     * @return 是否
     */
    public boolean isNotInterceptDirect(String url) {
        return notInterceptDirect.contains(url);
    }

    /**
     * 更新"不拦截路径"
     */
    @Subscribe(UPDATE_NOT_INTERCEPT)
    public void updateNotIntercept() {
        // 获取"不拦截路径"
        List<RouteNotInterceptVo> notIntercept = findAll();
        // 存在"不拦截路径"
        if (!notIntercept.isEmpty()) {
            // 创建"不拦截-匹配路径"
            notInterceptMatch = notIntercept.stream().filter(r -> r.getIsMatch() == 1).map(RouteNotIntercept::getPath).collect(Collectors.toList());
            // 创建"不拦截-直接路径"
            notInterceptDirect = notIntercept.stream().filter(r -> r.getIsMatch() == 0).map(RouteNotIntercept::getPath).collect(Collectors.toList());
        }
    }

}
