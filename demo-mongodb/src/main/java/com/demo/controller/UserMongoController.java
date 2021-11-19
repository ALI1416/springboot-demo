package com.demo.controller;

import com.demo.entity.mongo.UserMongo;
import com.demo.entity.pojo.Result;
import com.demo.service.UserMongoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>用户Mongo</h1>
 *
 * <p>
 * createDate 2021/03/27 19:51:39
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class UserMongoController {

    private final UserMongoService userMongoService;

    /**
     * 插入
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody UserMongo mongo) {
        return Result.o(userMongoService.insert(mongo));
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(@RequestBody UserMongo mongo) {
        return Result.o(userMongoService.update(mongo));
    }

    /**
     * 删除，通过id
     */
    @PostMapping("/deleteById")
    public Result deleteById(Long id) {
        return Result.o(userMongoService.deleteById(id));
    }

    /**
     * 查询，通过id
     */
    @PostMapping("/findById")
    public Result findById(Long id) {
        return Result.o(userMongoService.findById(id));
    }

    /**
     * 查询，通过name
     */
    @PostMapping("/findByName")
    public Result findByName(String name) {
        return Result.o(userMongoService.findByName(name));
    }

    /**
     * 关注+1
     */
    @PostMapping("/addFollowers")
    public Result addFollowers(Long id) {
        return Result.o(userMongoService.addFollowers(id));
    }


}
