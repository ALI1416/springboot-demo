package com.demo.service;

import cn.z.redis.RedisTemp;
import cn.z.redis.annotation.Subscribe;
import com.demo.base.ServiceBase;
import com.demo.constant.RedisConstant;
import com.demo.dao.mysql.RouteNotInterceptDao;
import com.demo.entity.po.RouteNotIntercept;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.vo.RouteNotInterceptVo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
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
public class RouteNotInterceptService extends ServiceBase {

    private final RouteNotInterceptDao routeNotInterceptDao;
    private final RedisTemp redisTemp;

    /**
     * 本地缓存
     */
    private final RouteNotInterceptVo localCache = new RouteNotInterceptVo();
    /**
     * 匹配路径
     */
    private List<String> match;
    /**
     * 直接路径
     */
    private List<String> direct;
    /**
     * 匹配路径(需要登录)
     */
    private List<String> loginMatch;
    /**
     * 直接路径(需要登录)
     */
    private List<String> loginDirect;

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
     * 获取本地缓存
     *
     * @return RouteNotInterceptVo
     */
    public RouteNotInterceptVo getLocalCache() {
        return localCache;
    }

    /**
     * 获取所有
     *
     * @return PageInfo RouteNotInterceptVo
     */
    public PageInfo<RouteNotInterceptVo> findAll(Integer pages, Integer rows, String orderBy) {
        return pagination(routeNotInterceptDao::findAll, pages, rows, orderBy);
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
        for (String url : urlList) {
            if (match.contains(url)) {
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
        return direct.contains(url);
    }

    /**
     * 是"不拦截-匹配路径(需要登录)"
     *
     * @param urlList URL列表
     * @return 是否
     */
    public boolean isLoginMatch(List<String> urlList) {
        for (String url : urlList) {
            if (loginMatch.contains(url)) {
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
        return loginDirect.contains(url);
    }

    /**
     * 更新缓存
     */
    @PostConstruct
    @Subscribe(RedisConstant.UPDATE_ROUTE_NOT_INTERCEPT)
    @Scheduled(cron = RedisConstant.UPDATE_CRON)
    public void updateCache() {
        // 获取"不拦截路径"
        List<RouteNotInterceptVo> notIntercept = routeNotInterceptDao.findAll();
        // 存在"不拦截路径"
        if (!notIntercept.isEmpty()) {
            // 创建"不拦截-匹配路径"
            match = notIntercept.stream().filter(r -> (!r.getNeedLogin() && r.getIsMatch())).map(RouteNotIntercept::getPath).collect(Collectors.toList());
            // 创建"不拦截-直接路径"
            direct = notIntercept.stream().filter(r -> (!r.getNeedLogin() && !r.getIsMatch())).map(RouteNotIntercept::getPath).collect(Collectors.toList());
            // 创建"不拦截-匹配路径(需要登录)"
            loginMatch = notIntercept.stream().filter(r -> (r.getNeedLogin() && r.getIsMatch())).map(RouteNotIntercept::getPath).collect(Collectors.toList());
            // 创建"不拦截-直接路径(需要登录)"
            loginDirect = notIntercept.stream().filter(r -> (r.getNeedLogin() && !r.getIsMatch())).map(RouteNotIntercept::getPath).collect(Collectors.toList());
            // 更新本地缓存
            localCache.setMatch(match);
            localCache.setDirect(direct);
            localCache.setLoginMatch(loginMatch);
            localCache.setLoginDirect(loginDirect);
        }
    }

}
