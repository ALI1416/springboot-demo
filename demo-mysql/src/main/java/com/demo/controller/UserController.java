package com.demo.controller;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.demo.base.ControllerBase;
import com.demo.service.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>UserController</h1>
 *
 * <p>
 * createDate 2021/09/13 10:51:44
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController extends ControllerBase {

    private final HttpServletRequest request;
    private final UserDao userService;

    public static void main(String[] args) {
        // UserAgent parse = UserAgentUtil.parse("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML,
        // like Gecko) Chrome/86.0.4240.198 Safari/537.36");
        // UserAgent parse = UserAgentUtil.parse("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N)
        // AppleWebKit/537" +
        //         ".36 (KHTML, like Gecko) Chrome/86.0.4240.198 Mobile Safari/537.36");
        UserAgent parse = UserAgentUtil.parse("哈哈哈");
        System.out.println(parse.getBrowser() + " " + parse.getVersion());
        System.out.println(parse.getPlatform() + " " + parse.getOsVersion());
        System.out.println(parse.isMobile());
    }
}
