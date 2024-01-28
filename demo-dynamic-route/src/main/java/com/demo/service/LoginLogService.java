package com.demo.service;

import cn.z.clock.Clock;
import cn.z.id.Id;
import cn.z.ip2region.Ip2Region;
import cn.z.ip2region.Region;
import cn.z.mongo.entity.Pageable;
import cn.z.tool.useragent.UserAgent;
import com.demo.base.ServiceBase;
import com.demo.dao.mongo.LoginLogDao;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.vo.LoginLogVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * <h1>登录日志</h1>
 *
 * <p>
 * createDate 2023/11/04 17:28:27
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class LoginLogService extends ServiceBase {

    private final LoginLogDao loginLogDao;

    /**
     * 插入
     *
     * @param createId 登录用户id
     * @param token    token
     * @param request  HttpServletRequest
     * @return ok:T,e:null
     */
    public LoginLogVo insert(Long createId, String token, HttpServletRequest request) {
        LoginLogVo loginLog = new LoginLogVo();
        loginLog.setId(Id.next());
        loginLog.setToken(token);
        loginLog.setCreateId(createId);
        loginLog.setCreateTime(Clock.timestamp());
        String ip = request.getHeader("X-Real-IP");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            loginLog.setIp(ip);
            try {
                loginLog.setIpInfo(Ip2Region.parse(ip));
            } catch (Exception ignored) {
            }
        }
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            loginLog.setUserAgent(userAgent);
            try {
                loginLog.setUserAgentInfo(UserAgent.parse(userAgent));
            } catch (Exception ignored) {
            }
        }
        return loginLogDao.insert(loginLog);
    }

    private static Criteria buildCriteria(LoginLogVo loginLog) {
        Criteria criteria = new Criteria();
        if (loginLog.getToken() != null) {
            criteria.and("token").is(loginLog.getToken());
        }
        if (loginLog.getCreateId() != null) {
            criteria.and("createId").is(loginLog.getCreateId());
        }
        if (loginLog.getIp() != null) {
            criteria.and("ip").is(loginLog.getIp());
        }
        if (loginLog.getIpInfo() != null) {
            Region ipInfo = loginLog.getIpInfo();
            if (ipInfo.getCountry() != null) {
                criteria.and("ipInfo.country").is(ipInfo.getCountry());
            }
            if (ipInfo.getProvince() != null) {
                criteria.and("ipInfo.province").is(ipInfo.getProvince());
            }
            if (ipInfo.getCity() != null) {
                criteria.and("ipInfo.city").is(ipInfo.getCity());
            }
            if (ipInfo.getIsp() != null) {
                criteria.and("ipInfo.isp").is(ipInfo.getIsp());
            }
        }
        if (loginLog.getUserAgent() != null) {
            criteria.and("userAgent").is(loginLog.getUserAgent());
        }
        if (loginLog.getUserAgentInfo() != null) {
            UserAgent userAgentInfo = loginLog.getUserAgentInfo();
            if (userAgentInfo.getEngine() != null) {
                criteria.and("userAgentInfo.engine").is(userAgentInfo.getEngine());
            }
            if (userAgentInfo.getEngineVersion() != null) {
                criteria.and("userAgentInfo.engineVersion").is(userAgentInfo.getEngineVersion());
            }
            if (userAgentInfo.getBrowser() != null) {
                criteria.and("userAgentInfo.browser").is(userAgentInfo.getBrowser());
            }
            if (userAgentInfo.getBrowserVersion() != null) {
                criteria.and("userAgentInfo.browserVersion").is(userAgentInfo.getBrowserVersion());
            }
            if (userAgentInfo.getOs() != null) {
                criteria.and("userAgentInfo.os").is(userAgentInfo.getOs());
            }
            if (userAgentInfo.getOsVersion() != null) {
                criteria.and("userAgentInfo.osVersion").is(userAgentInfo.getOsVersion());
            }
            if (userAgentInfo.getPlatform() != null) {
                criteria.and("userAgentInfo.platform").is(userAgentInfo.getPlatform());
            }
            if (userAgentInfo.getMobile() != null) {
                criteria.and("userAgentInfo.isMobile").is(userAgentInfo.getMobile());
            }
        }
        buildRange(criteria, "createTime", loginLog.getCreateTime(), loginLog.getCreateTimeEnd(), loginLog.getCreateTimeNot());
        return criteria;
    }

    /**
     * 分页查询
     *
     * @param loginLog LoginLogVo
     * @return PageInfo LoginLogVo
     */
    public PageInfo<LoginLogVo> findPage(LoginLogVo loginLog) {
        Query query = Query.query(buildCriteria(loginLog));
        Pageable pageable = buildPage(loginLog.getPages(), loginLog.getRows(), loginLog.getOrderBy());
        return new PageInfo<>(loginLogDao.findPage(query, pageable));
    }

}
