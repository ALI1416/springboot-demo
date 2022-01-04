package com.demo.controller;

import cn.z.clock.Clock;
import com.demo.entity.mongo.UserMongo;
import com.demo.entity.pojo.Result;
import com.demo.service.UserMongoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>UserMongo</h1>
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
     * <h3>插入一个，id已存在会失败</h3>
     * POST /insert<br>
     * body JSON {"id":1,"name":"a","followers":10,"following":20}<br>
     * 返回 true<br>
     * 再次插入 返回 false
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody UserMongo userMongo) {
        userMongo.setDate(Clock.timestamp());
        return Result.o(userMongoService.insert(userMongo));
    }

    /**
     * <h3>插入多个，id存在会失败，后面不再插入</h3>
     * POST /insertList<br>
     * body JSON [{"id":2,"name":"a","followers":10,"following":20},{"id":3,"name":"a","followers":10,"following":20}
     * ]<br>
     * 返回 true<br>
     * body JSON [{"id":4,"name":"a","followers":10,"following":20},{"id":2,"name":"a","followers":10,"following":20}
     * ,{"id":5,"name":"a","followers":10,"following":20}]<br>
     * 返回 false 只插入了id=4<br>
     */
    @PostMapping("/insertList")
    public Result insertList(@RequestBody List<UserMongo> userMongoList) {
        return Result.o(userMongoService.insertList(userMongoList));
    }

    /**
     * <h3>保存一个，id已存在会更新，不存在会插入</h3>
     * POST /save<br>
     * body JSON {"id":1,"name":"a","followers":10,"following":20}<br>
     * id=1已存在会更新，不存在会插入
     */
    @PostMapping("/save")
    public Result save(@RequestBody UserMongo userMongo) {
        userMongoService.save(userMongo);
        return Result.o();
    }

    /**
     * <h3>保存多个，id已存在会更新，不存在会插入</h3>
     * POST /saveList<br>
     * body JSON [{"id":2,"name":"a","followers":10,"following":20},{"id":3,"name":"a","followers":10,"following":20}
     * ]<br>
     * 已存在会更新，不存在会插入
     */
    @PostMapping("/saveList")
    public Result saveList(@RequestBody List<UserMongo> userMongoList) {
        userMongoService.saveList(userMongoList);
        return Result.o();
    }

    /**
     * <h3>是否存在id</h3>
     * POST /existsById?id=1<br>
     * id=1存在true不存在false
     */
    @PostMapping("/existsById")
    public Result existsById(Long id) {
        return Result.o(userMongoService.existsById(id));
    }

    /**
     * <h3>是否存在</h3>
     * POST /exists<br>
     * body JSON {"name":"a"}<br>
     * name=a存在true不存在false
     */
    @PostMapping("/exists")
    public Result exists(@RequestBody UserMongo userMongo) {
        Example<UserMongo> example = Example.of(userMongo);
        return Result.o(userMongoService.exists(example));
    }

    /**
     * <h3>记录总数</h3>
     * POST /countAll<br>
     * 返回记录总数
     */
    @PostMapping("/countAll")
    public Result countAll() {
        return Result.o(userMongoService.countAll());
    }

    /**
     * <h3>记录总数</h3>
     * POST /count<br>
     * body JSON {"name":"a"}<br>
     * name=a的记录总数
     */
    @PostMapping("/count")
    public Result count(@RequestBody UserMongo userMongo) {
        Example<UserMongo> example = Example.of(userMongo);
        return Result.o(userMongoService.count(example));
    }

    /**
     * <h3>删除，通过id，不存在不会报错</h3>
     * POST /deleteById?id=1
     */
    @PostMapping("/deleteById")
    public Result deleteById(Long id) {
        userMongoService.deleteById(id);
        return Result.o();
    }

    /**
     * <h3>删除，通过实体里的id，不存在不会报错</h3>
     * POST /delete<br>
     * body JSON {"id":1}
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody UserMongo userMongo) {
        userMongoService.delete(userMongo);
        return Result.o();
    }

    /**
     * <h3>删除多个，通过id，不存在不会报错</h3>
     * POST /deleteListById<br>
     * body JSON [1,2,3]
     */
    @PostMapping("/deleteListById")
    public Result deleteListById(@RequestBody List<Long> ids) {
        userMongoService.deleteListById(ids);
        return Result.o();
    }

    /**
     * <h3>删除多个，通过实体里的id，不存在不会报错</h3>
     * POST /deleteList<br>
     * body JSON [{"id":1},{"id":2},{"id":3}]
     */
    @PostMapping("/deleteList")
    public Result deleteList(@RequestBody List<UserMongo> userMongoList) {
        userMongoService.deleteList(userMongoList);
        return Result.o();
    }

    /**
     * <h3>删除所有</h3>
     */
    @PostMapping("/deleteAll")
    public Result deleteAll() {
        userMongoService.deleteAll();
        return Result.o();
    }

    /**
     * <h3>查找，通过id</h3>
     * POST /findById?id=1<br>
     * 存在:实体;不存在:null
     */
    @PostMapping("/findById")
    public Result findById(Long id) {
        return Result.o(userMongoService.findById(id));
    }

    /**
     * <h3>查找第一个</h3>
     * POST /findOne<br>
     * body JSON {"name":"a"}<br>
     * 存在:实体;不存在:null
     */
    @PostMapping("/findOne")
    public Result findOne(@RequestBody UserMongo userMongo) {
        Example<UserMongo> example = Example.of(userMongo);
        return Result.o(userMongoService.findOne(example));
    }

    /**
     * <h3>查找多个，通过id</h3>
     * POST /findListById<br>
     * body JSON [1,2,3]<br>
     * 存在:[实体];不存在:[]
     */
    @PostMapping("/findListById")
    public Result findListById(@RequestBody List<Long> ids) {
        return Result.o(userMongoService.findListById(ids));
    }

    /**
     * <h3>查找所有</h3>
     * POST /findAll<br>
     * [实体]
     */
    @PostMapping("/findAll")
    public Result findAll() {
        return Result.o(userMongoService.findAll());
    }

    /**
     * <h3>查找所有，根据指定字段倒序</h3>
     * POST /findListSort?field=name<br>
     * 根据name倒叙的[实体]
     */
    @PostMapping("/findListSort")
    public Result findList(String field) {
        Sort sort = Sort.by(Sort.Order.desc(field));
        return Result.o(userMongoService.findList(sort));
    }

    /**
     * <h3>查找所有，根据Example</h3>
     * POST /findListExample<br>
     * body JSON {"name":"a"}<br>
     * name=a的[实体]
     */
    @PostMapping("/findListExample")
    public Result findList(@RequestBody UserMongo userMongo) {
        Example<UserMongo> example = Example.of(userMongo);
        return Result.o(userMongoService.findList(example));
    }

    /**
     * <h3>查找所有，根据Example和指定字段倒序</h3>
     * POST /findListExampleSort?field=id<br>
     * body JSON {"name":"a"}<br>
     * name=a并且按照id倒序的[实体]
     */
    @PostMapping("/findListExampleSort")
    public Result findList(@RequestBody UserMongo userMongo, String field) {
        Example<UserMongo> example = Example.of(userMongo);
        Sort sort = Sort.by(Sort.Order.desc(field));
        return Result.o(userMongoService.findList(example, sort));
    }

    /**
     * <h3>查找所有，分页</h3>
     * POST /findListPage?page=0&size=1<br>
     * Page
     */
    @PostMapping("/findListPage")
    public Result findList(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return Result.o(userMongoService.findList(pageRequest));
    }

    /**
     * <h3>分页查找</h3>
     * POST /findPage<br>
     * body JSON {"pages":"0","rows":"2","orderBy":"name desc"}<br>
     * Page
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody UserMongo userMongo) {
        return Result.o(userMongoService.findPage(userMongo));
    }

    /**
     * <h3>排序查找</h3>
     * POST /findSort<br>
     * body JSON {"orderBy":"name desc"}<br>
     * List
     */
    @PostMapping("/findSort")
    public Result findSort(@RequestBody UserMongo userMongo) {
        return Result.o(userMongoService.findSort(userMongo));
    }

    /**
     * <h3>查找所有，根据Example和分页</h3>
     * POST /findListPage?page=0&size=1<br>
     * body JSON {"name":"a"}<br>
     * Page
     */
    @PostMapping("/findListExamplePage")
    public Result findList(@RequestBody UserMongo userMongo, int page, int size) {
        Example<UserMongo> example = Example.of(userMongo);
        PageRequest pageRequest = PageRequest.of(page, size);
        return Result.o(userMongoService.findList(example, pageRequest));
    }

    /**
     * <h3>根据名字查询并分页</h3>
     * POST /findByName?name=a&page=0&size=1<br>
     * Page
     */
    @PostMapping("/findByName")
    public Result findByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return Result.o(userMongoService.findByName(name, pageRequest));
    }

    /**
     * 关注+1
     * POST /addFollowers?id=1<br>
     * UpdateResult
     */
    @PostMapping("/addFollowers")
    public Result addFollowers(Long id) {
        return Result.o(userMongoService.addFollowers(id));
    }

}
