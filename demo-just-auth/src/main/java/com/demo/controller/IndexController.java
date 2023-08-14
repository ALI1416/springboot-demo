package com.demo.controller;

import com.alibaba.fastjson2.JSON;
import com.demo.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Controller
@Slf4j
public class IndexController {

    /**
     * Request
     */
    private final AuthRequest request = new AuthQqRequest(//
            AuthConfig.builder()//
                    .clientId(Constant.QQ_APP_ID)//
                    .clientSecret(Constant.QQ_APP_KEY)//
                    .redirectUri(Constant.QQ_CALLBACK_URL)//
                    .build());

    /**
     * 授权地址
     */
    @GetMapping(value = {"", "/"})
    public String render() {
        String authorize = request.authorize(AuthStateUtils.createState());
        log.info(authorize);
        return "redirect:" + authorize;
    }

    /**
     * 回调地址
     */
    @GetMapping("thirdLogin/qq/callback")
    @ResponseBody
    public AuthResponse callback(AuthCallback callback) {
        AuthResponse response = request.login(callback);
        log.info(JSON.toJSONString(response));
        return response;
    }

}
