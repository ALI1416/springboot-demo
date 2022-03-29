package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.util.MinioUtils;
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
public class IndexController {

    /**
     * 所有储存桶
     * http://localhost:8080/bucketAll
     * [{"createDate":"2022-03-25 17:04:03","name":"test"}]
     */
    @GetMapping("bucketAll")
    public Result bucketAll() {
        return Result.o(MinioUtils.bucketAll());
    }

    /**
     * 是否存在储存桶
     * http://localhost:8080/bucketExist?bucket=test
     * true
     */
    @GetMapping("bucketExist")
    public Result existBucket(String bucket) {
        return Result.o(MinioUtils.bucketExist(bucket));
    }

    /**
     * 创建储存桶
     * http://localhost:8080/bucketCreate?bucket=test
     * true
     */
    @GetMapping("bucketCreate")
    public Result createBucket(String bucket) {
        return Result.o(MinioUtils.bucketCreate(bucket));
    }

    /**
     * 删除储存桶
     * http://localhost:8080/bucketDelete?bucket=test
     * true
     */
    @GetMapping("bucketDelete")
    public Result bucketDelete(String bucket) {
        return Result.o(MinioUtils.bucketDelete(bucket));
    }

    /**
     * 所有对象(根目录)
     */
    @GetMapping("objectAll")
    public Result objectAll(String bucket) {
        return Result.o(MinioUtils.objectAll(bucket));
    }

    /**
     * 所有对象
     */
    @GetMapping("objectAll2")
    public Result objectAll(String bucket, String path) {
        return Result.o(MinioUtils.objectAll(bucket, path));
    }

    /**
     * 对象的元数据
     */
    @GetMapping("objectMetadata")
    public Result objectMetadata(String bucket, String path) {
        return Result.o(MinioUtils.objectMetadata(bucket, path));
    }

}
