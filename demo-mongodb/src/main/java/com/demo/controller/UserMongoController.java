package com.demo.controller;

import com.demo.entity.po.UserMongo;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.PageRequestFix;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserMongoVo;
import com.demo.service.UserMongoService;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 通用JPA方法

    /**
     * 插入<br>
     * http://localhost:8080/insert<br>
     * {"name":"a","followers":10,"following":20}
     */
    @PostMapping("insert")
    public Result<Long> insert(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.insert(userMongo));
    }

    /**
     * 批量插入<br>
     * http://localhost:8080/batchInsert<br>
     * [{"name":"ab","followers":10,"following":20},{"name":"ac","followers":10,"following":20}]
     */
    @PostMapping("batchInsert")
    public Result<Boolean> batchInsert(@RequestBody List<UserMongoVo> userMongoList) {
        return Result.o(userMongoService.batchInsert(userMongoList));
    }

    /**
     * 插入或更新<br>
     * http://localhost:8080/save<br>
     * {"id":1,"name":"a","followers":10,"following":20}
     */
    @PutMapping("save")
    public Result<Boolean> save(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.save(userMongo));
    }

    /**
     * 批量插入或更新<br>
     * http://localhost:8080/batchSave<br>
     * [{"id":2,"name":"a","followers":10,"following":20},{"id":3,"name":"a","followers":10,"following":20}]
     */
    @PutMapping("batchSave")
    public Result<Boolean> batchSave(@RequestBody List<UserMongoVo> userMongoList) {
        return Result.o(userMongoService.batchSave(userMongoList));
    }

    /**
     * 删除<br>
     * http://localhost:8080/delete?id=1
     */
    @DeleteMapping("delete")
    public Result<Boolean> delete(long id) {
        return Result.o(userMongoService.delete(id));
    }

    /**
     * 批量删除<br>
     * http://localhost:8080/batchDelete<br>
     * [1,2,3]
     */
    @DeleteMapping("batchDelete")
    public Result<Boolean> batchDelete(@RequestBody List<Long> idList) {
        return Result.o(userMongoService.batchDelete(idList));
    }

    /**
     * 全部删除
     */
    @DeleteMapping("deleteAll")
    public Result<Boolean> deleteAll() {
        return Result.o(userMongoService.deleteAll());
    }

    /**
     * 是否存在id<br>
     * http://localhost:8080/existId?id=1
     */
    @GetMapping("existId")
    public Result<Boolean> existId(long id) {
        return Result.o(userMongoService.existId(id));
    }

    /**
     * 是否存在<br>
     * http://localhost:8080/exist<br>
     * {"name":"a"}
     */
    @PostMapping("exist")
    public Result<Boolean> exist(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.exist(userMongo));
    }

    /**
     * 记录总数<br>
     * http://localhost:8080/countAll
     */
    @GetMapping("countAll")
    public Result<Long> countAll() {
        return Result.o(userMongoService.countAll());
    }

    /**
     * 记录总数<br>
     * http://localhost:8080/count<br>
     * {"name":"a"}
     */
    @PostMapping("count")
    public Result<Long> count(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.count(userMongo));
    }

    /**
     * 查询，通过id<br>
     * http://localhost:8080/findById?id=1
     */
    @GetMapping("findById")
    public Result<UserMongo> findById(long id) {
        return Result.o(userMongoService.findById(id));
    }

    /**
     * 查询第一个<br>
     * http://localhost:8080/findOne<br>
     * {"name":"a"}
     */
    @PostMapping("findOne")
    public Result<UserMongo> findOne(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findOne(userMongo));
    }

    /**
     * 查询多个，通过id<br>
     * http://localhost:8080/findByIdList<br>
     * [1,2,3]
     */
    @PostMapping("findByIdList")
    public Result<List<UserMongo>> findByIdList(@RequestBody List<Long> idList) {
        return Result.o(userMongoService.findByIdList(idList));
    }

    /**
     * 查询所有<br>
     * http://localhost:8080/findAll
     */
    @GetMapping("findAll")
    public Result<List<UserMongo>> findAll() {
        return Result.o(userMongoService.findAll());
    }

    /**
     * 排序查询<br>
     * http://localhost:8080/findSort<br>
     * {"orderBy":"name desc"}<br>
     */
    @PostMapping("findSort")
    public Result<List<UserMongo>> findSort(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findSort(userMongo));
    }

    /**
     * 查询所有，分页<br>
     * http://localhost:8080/findPage?page=1&size=1<br>
     * PageInfo
     */
    // @GetMapping("findPage")
    // public Result<PageInfo<UserMongo>> findList(int page, int size) {
    //     return Result.o(userMongoService.findPage(pageRequestFix));
    // }

    /**
     * 分页查询<br>
     * http://localhost:8080/findPage<br>
     * {"pages":"1","rows":"2","orderBy":"name desc"}<br>
     * PageInfo
     */
    @PostMapping("findPage")
    public Result<PageInfo<UserMongo>> findPage(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findPage(userMongo));
    }

    /**
     * 分页查询<br>
     * http://localhost:8080/findPage2<br>
     * {"pages":"1","rows":"10","date":"2022-01-05","dateEnd":"2022-01-06"}<br>
     * PageInfo
     */
    @PostMapping("findPage2")
    public Result<PageInfo<UserMongo>> findPage2(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findPage2(userMongo));
    }

    /**
     * 排序查询<br>
     * http://localhost:8080/findSort<br>
     * {"orderBy":"name desc"}<br>
     * List
     */
    @PostMapping("findSort3")
    public Result<List<UserMongo>> findSort3(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findSort(userMongo));
    }

    /**
     * 排序查询2<br>
     * http://localhost:8080/findSort2<br>
     * {"orderBy":"name desc","date":"2022-01-05","dateEnd":"2022-01-06"}<br>
     * PageInfo
     */
    // @PostMapping("findSort2")
    // public Result<PageInfo<UserMongo>> findSort2(@RequestBody UserMongoVo userMongo) {
    //     return Result.o(userMongoService.findSort2(userMongo));
    // }

    /**
     * 查询所有，根据Example和分页<br>
     * http://localhost:8080/findListPage?page=1&size=1<br>
     * {"name":"a"}<br>
     * PageInfo
     */
    // @PostMapping("findListExamplePage")
    // public Result<PageInfo<UserMongo>> findList(@RequestBody UserMongo userMongo, int page, int size) {
    //     Example<UserMongo> example = Example.of(userMongo);
    //     PageRequestFix pageRequestFix = PageRequestFix.of(page, size);
    //     return Result.o(userMongoService.findList(example, pageRequestFix));
    // }

    /**
     * 根据名字查询并分页<br>
     * http://localhost:8080/findByName?name=a&page=1&size=1<br>
     * PageInfo
     */
    @GetMapping("findByName")
    public Result<PageInfo<UserMongo>> findByName(String name, int page, int size) {
        PageRequestFix pageRequestFix = PageRequestFix.of(page, size);
        return Result.o(userMongoService.findByName(name, pageRequestFix));
    }

    /**
     * 关注+1
     * http://localhost:8080/addFollowers?id=1<br>
     * UpdateResult
     */
    @GetMapping("addFollowers")
    public Result<UpdateResult> addFollowers(long id) {
        return Result.o(userMongoService.addFollowers(id));
    }

}
