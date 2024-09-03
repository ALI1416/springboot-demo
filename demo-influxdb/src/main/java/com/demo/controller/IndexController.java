package com.demo.controller;

import cn.z.influx.InfluxTemp;
import com.demo.entity.pojo.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2023/11/04 16:42:19
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class IndexController {

    private final InfluxTemp influxTemp;

    /**
     * 获取数据库名<br>
     * http://localhost:8080/getDbName
     */
    @GetMapping("getDbName")
    public Result getDbName() {
        return Result.o(influxTemp.bucketAll());
    }

}
