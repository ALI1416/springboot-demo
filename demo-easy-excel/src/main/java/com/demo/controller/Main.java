package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.demo.entity.excel.UserExcel;
import com.demo.util.EeUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>EasyExcel</h1>
 *
 * <p>
 * createDate 2022/02/22 17:37:24
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        // 导出模板
        EeUtils.write("E:/模板.xlsx", UserExcel.class, null);
        // 导出内容
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
        EeUtils.write("E:/导出.xlsx", UserExcel.class, exportList);
        // 导入内容
        List<UserExcel> importList = new ArrayList<>();
        EeUtils.read("E:/导出.xlsx", UserExcel.class, importList);
        log.info(JSON.toJSONString(importList));
    }

}
