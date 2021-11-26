package com.demo.interceptor;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>权限和角色拦截器</h1>
 *
 * <p>
 * createDate 2021/11/24 15:51:30
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Configuration
public class PermissionAndRoleInterceptor implements StpInterface {

    /**
     * 权限拦截
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = new ArrayList<>();
        for (String roleId : getRoleList(loginId, loginType)) {
            SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleId);
            List<String> list = roleSession.get(SaSession.PERMISSION_LIST, () -> {
                List<String> permission = new ArrayList<>();
                permission.add("/getPermissionList");
                permission.add("/**/getLoginIdAsLong");
                permission.add("/getLoginIdAsLong/**");
                permission.add("/a/**/getLoginIdAsLong");
                return permission;
            });
            permissionList.addAll(list);
        }
        return permissionList;
    }

    /**
     * 角色拦截
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的角色码集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get(SaSession.ROLE_LIST, () -> {
            List<String> role = new ArrayList<>();
            role.add("root");
            role.add("admin");
            role.add("user");
            role.add("guest");
            return role;
        });
    }

}
