package com.demo.service;

import cn.z.redis.RedisTemp;
import cn.z.redis.annotation.Subscribe;
import com.demo.constant.RedisConstant;
import com.demo.dao.mysql.RouteNotInterceptDao;
import com.demo.entity.po.RouteNotIntercept;
import com.demo.entity.vo.RouteNotInterceptVo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <h1>路由-不拦截</h1>
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
     * 不拦截-匹配路径(需要登录)
     */
    private List<String> notInterceptLoginMatch;
    /**
     * 不拦截-直接路径(需要登录)
     */
    private List<String> notInterceptLoginDirect;

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
     * @param routeNotIntercept path,name,isMatch,needLogin,seq
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
    public boolean delete(long id) {
        return routeNotInterceptDao.delete(id);
    }

    /**
     * 更新
     *
     * @param routeNotIntercept id(必须),path,name,isMatch,needLogin,seq(至少1个)
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
        redisTemp.broadcast(UPDATE_NOT_INTERCEPT);
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
     * 是"不拦截-匹配路径(需要登录)"
     *
     * @param urlList URL列表
     * @return 是否
     */
    public boolean isNotInterceptLoginMatch(List<String> urlList) {
        for (String url : urlList) {
            if (notInterceptLoginMatch.contains(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是"不拦截-直接路径(需要登录)"
     *
     * @param url URL
     * @return 是否
     */
    public boolean isNotInterceptLoginDirect(String url) {
        return notInterceptLoginDirect.contains(url);
    }

    /**
     * 更新"不拦截路径"
     */
    @Subscribe(UPDATE_NOT_INTERCEPT)
    @Scheduled(fixedDelay = RedisConstant.ROUTE_EXPIRE, timeUnit = TimeUnit.SECONDS)
    public void updateNotIntercept() {
        // 获取"不拦截路径"
        List<RouteNotInterceptVo> notIntercept = findAll();
        // 存在"不拦截路径"
        if (!notIntercept.isEmpty()) {
            // 创建"不拦截-匹配路径"
            notInterceptMatch = notIntercept.stream().filter(r -> (!r.getNeedLogin() && r.getIsMatch())).map(RouteNotIntercept::getPath).collect(Collectors.toList());
            // 创建"不拦截-直接路径"
            notInterceptDirect = notIntercept.stream().filter(r -> (!r.getNeedLogin() && !r.getIsMatch())).map(RouteNotIntercept::getPath).collect(Collectors.toList());
            // 创建"不拦截-匹配路径(需要登录)"
            notInterceptLoginMatch = notIntercept.stream().filter(r -> (r.getNeedLogin() && r.getIsMatch())).map(RouteNotIntercept::getPath).collect(Collectors.toList());
            // 创建"不拦截-直接路径(需要登录)"
            notInterceptLoginDirect = notIntercept.stream().filter(r -> (r.getNeedLogin() && !r.getIsMatch())).map(RouteNotIntercept::getPath).collect(Collectors.toList());
        }
    }

}
