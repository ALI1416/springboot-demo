package com.demo.util.easyexcel;

import cn.z.spring.util.EasyExcelSpringUtils;
import com.alibaba.fastjson2.JSON;
import com.demo.entity.excel.UserExcel;
import com.demo.entity.pojo.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>EasyExcelController</h1>
 *
 * <p>
 * createDate 2023/08/25 10:14:03
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("easyExcel")
public class EasyExcelController {

    private final HttpServletResponse response;

    /**
     * 模板<br>
     * http://localhost:8080/easyExcel/template
     */
    @GetMapping("template")
    public void template() throws Exception {
        EasyExcelSpringUtils.download(response, "模板", null, UserExcel.class);
    }

    /**
     * 导出<br>
     * http://localhost:8080/easyExcel/exportExcel
     */
    @GetMapping("exportExcel")
    public void exportExcel() throws Exception {
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
        EasyExcelSpringUtils.download(response, "导出", exportList, UserExcel.class);
    }

    /**
     * 导入<br>
     * http://localhost:8080/easyExcel/importExcel
     */
    @PostMapping("importExcel")
    public Result<List<UserExcel>> importExcel(MultipartFile file) throws Exception {
        List<UserExcel> importList = new ArrayList<>();
        EasyExcelSpringUtils.upload(file, importList, UserExcel.class);
        log.info(JSON.toJSONString(importList));
        return Result.o(importList);
    }

}
