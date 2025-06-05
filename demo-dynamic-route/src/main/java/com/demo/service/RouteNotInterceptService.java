package com.demo.service;

import cn.z.redis.RedisTemp;
import cn.z.redis.annotation.Subscribe;
import com.demo.base.ServiceBase;
import com.demo.constant.RedisConstant;
import com.demo.dao.mysql.RouteNotInterceptDao;
import com.demo.entity.po.RouteNotIntercept;
import com.demo.entity.vo.RouteNotInterceptVo;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
public class RouteNotInterceptService extends ServiceBase {

    private final RouteNotInterceptDao routeNotInterceptDao;
    private final RedisTemp redisTemp;

    /**
     * 本地缓存
     */
    @Getter
    private final RouteNotInterceptVo localCache = new RouteNotInterceptVo();

    /**
     * 插入
     *
     * @param routeNotIntercept path,name,isMatch,needLogin,seq
     * @return ok:id,e:0
     */
    @Transactional
    public long insert(RouteNotInterceptVo routeNotIntercept) {
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
     * 获取所有
     *
     * @return List RouteNotInterceptVo
     */
    public List<RouteNotInterceptVo> findAll() {
        return routeNotInterceptDao.findAll();
    }

    /**
     * 刷新缓存
     */
    public void refreshCache() {
        redisTemp.broadcast(RedisConstant.UPDATE_ROUTE_NOT_INTERCEPT);
    }

    /**
     * 是"不拦截-匹配路径"
     *
     * @param urlList URL列表
     * @return 是否
     */
    public boolean isMatch(List<String> urlList) {
        List<String> matchList = localCache.getMatch();
        for (String url : urlList) {
            if (matchList.contains(url)) {
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
    public boolean isDirect(String url) {
        return localCache.getDirect().contains(url);
    }

    /**
     * 是"不拦截-匹配路径(需要登录)"
     *
     * @param urlList URL列表
     * @return 是否
     */
    public boolean isLoginMatch(List<String> urlList) {
        List<String> loginMatchList = localCache.getLoginMatch();
        for (String url : urlList) {
            if (loginMatchList.contains(url)) {
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
    public boolean isLoginDirect(String url) {
        return localCache.getLoginDirect().contains(url);
    }

    /**
     * 更新缓存
     */
    @Async
    @PostConstruct
    @Subscribe(RedisConstant.UPDATE_ROUTE_NOT_INTERCEPT)
    @Scheduled(cron = RedisConstant.UPDATE_CRON)
    public void updateCache() {
        // 获取"不拦截路径"
        List<RouteNotInterceptVo> notIntercept = routeNotInterceptDao.findAll();
        // 存在"不拦截路径"
        if (!notIntercept.isEmpty()) {
            // 创建"不拦截-匹配路径"
            localCache.setMatch(notIntercept.stream().filter(r -> (!r.getNeedLogin() && r.getIsMatch())).map(RouteNotIntercept::getPath).toList());
            // 创建"不拦截-直接路径"
            localCache.setDirect(notIntercept.stream().filter(r -> (!r.getNeedLogin() && !r.getIsMatch())).map(RouteNotIntercept::getPath).toList());
            // 创建"不拦截-匹配路径(需要登录)"
            localCache.setLoginMatch(notIntercept.stream().filter(r -> (r.getNeedLogin() && r.getIsMatch())).map(RouteNotIntercept::getPath).toList());
            // 创建"不拦截-直接路径(需要登录)"
            localCache.setLoginDirect(notIntercept.stream().filter(r -> (r.getNeedLogin() && !r.getIsMatch())).map(RouteNotIntercept::getPath).toList());
        } else {
            localCache.setMatch(Collections.emptyList());
            localCache.setDirect(Collections.emptyList());
            localCache.setLoginMatch(Collections.emptyList());
            localCache.setLoginDirect(Collections.emptyList());
        }
    }

}
