package com.demo.controller;


import com.demo.entity.pojo.Result;
import com.demo.tool.ElasticSearchTemp;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class IndexController {

    private final ElasticSearchTemp elasticSearchTemp;

    /**
     * 创建索引
     * http://localhost:8080/createIndex?index=a
     */
    @GetMapping("/createIndex")
    public Result<Boolean> createIndex(String index) {
        return Result.o(elasticSearchTemp.createIndex(index));
    }

}
