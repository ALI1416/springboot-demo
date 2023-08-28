package com.demo.util.easyexcel;

import com.alibaba.fastjson2.JSON;
import com.demo.entity.excel.UserExcel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>EasyExcel</h1>
 *
 * <p>
 * createDate 2023/08/25 10:12:56
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        // 导出模板
        EasyExcelUtils.write("E:/模板.xlsx", null, UserExcel.class);
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
        EasyExcelUtils.write("E:/导出.xlsx", exportList, UserExcel.class);
        // 导入内容
        List<UserExcel> importList = new ArrayList<>();
        EasyExcelUtils.read("E:/导出.xlsx", importList, UserExcel.class);
        log.info(JSON.toJSONString(importList));
    }

}
