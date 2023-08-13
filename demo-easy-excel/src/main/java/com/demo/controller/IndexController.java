package com.demo.controller;

import com.alibaba.fastjson2.JSON;
import com.demo.entity.excel.UserExcel;
import com.demo.entity.pojo.Result;
import com.demo.util.EeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
@RestController
@AllArgsConstructor
@Slf4j
public class IndexController {

    private final HttpServletResponse response;

    /**
     * 模板
     */
    @GetMapping("template")
    public void template() {
        EeUtils.download(response, "模板", UserExcel.class, null);
    }

    /**
     * 导出
     */
    @GetMapping("exportExcel")
    public void exportExcel() {
        List<UserExcel> exportList = new ArrayList<>();
        UserExcel u1 = new UserExcel();
        u1.setAccount("account");
        u1.setPwd("account");
        u1.setName("name");
        u1.setGender("男");
        u1.setYear(1998);
        exportList.add(u1);
        UserExcel u2 = new UserExcel();
        u2.setAccount("aaa");
        u2.setPwd("bbb");
        u2.setName("ccc");
        u2.setGender("女");
        u2.setYear(2000);
        exportList.add(u2);
        EeUtils.download(response, "导出", UserExcel.class, exportList);
    }

    /**
     * 导入
     */
    @PostMapping("importExcel")
    public Result<List<UserExcel>> importExcel(MultipartFile file) {
        List<UserExcel> importList = new ArrayList<>();
        EeUtils.upload(file, UserExcel.class, importList);
        log.info(JSON.toJSONString(importList));
        return Result.o(importList);
    }

}
