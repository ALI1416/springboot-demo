package com.demo.controller;

import cn.z.clock.Clock;
import com.demo.entity.mongo.UserMongo;
import com.demo.entity.pojo.PageInfo;
import com.demo.entity.pojo.PageRequestFix;
import com.demo.entity.pojo.Result;
import com.demo.entity.vo.UserMongoVo;
import com.demo.service.UserMongoService;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
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

    /**
     * <h3>插入一个，id已存在会失败</h3>
     * http://localhost:8080/insert<br>
     * {"id":1,"name":"a","followers":10,"following":20}<br>
     * 返回 true<br>
     * 再次插入 返回 false
     */
    @PostMapping("/insert")
    public Result<Boolean> insert(@RequestBody UserMongoVo userMongo) {
        userMongo.setDate(Clock.timestamp());
        return Result.o(userMongoService.insert(userMongo));
    }

    /**
     * <h3>插入多个，id存在会失败，后面不再插入</h3>
     * http://localhost:8080/insertList<br>
     * [{"id":2,"name":"a","followers":10,"following":20},{"id":3,"name":"a","followers":10,"following":20}]<br>
     * 返回 true<br>
     * [{"id":4,"name":"a","followers":10,"following":20},{"id":2,"name":"a","followers":10,"following":20},{"id":5,"name":"a","followers":10,"following":20}]<br>
     * 返回 false 只插入了id=4<br>
     */
    @PostMapping("/insertList")
    public Result<Boolean> insertList(@RequestBody List<UserMongo> userMongoList) {
        return Result.o(userMongoService.insertList(userMongoList));
    }

    /**
     * <h3>保存一个，id已存在会更新，不存在会插入</h3>
     * http://localhost:8080/save<br>
     * {"id":1,"name":"a","followers":10,"following":20}<br>
     * id=1已存在会更新，不存在会插入
     */
    @PutMapping("/save")
    public Result save(@RequestBody UserMongoVo userMongo) {
        userMongoService.save(userMongo);
        return Result.o();
    }

    /**
     * <h3>保存多个，id已存在会更新，不存在会插入</h3>
     * http://localhost:8080/saveList<br>
     * [{"id":2,"name":"a","followers":10,"following":20},{"id":3,"name":"a","followers":10,"following":20}]<br>
     * 已存在会更新，不存在会插入
     */
    @PutMapping("/saveList")
    public Result saveList(@RequestBody List<UserMongo> userMongoList) {
        userMongoService.saveList(userMongoList);
        return Result.o();
    }

    /**
     * <h3>是否存在id</h3>
     * http://localhost:8080/existsById?id=1<br>
     * id=1存在true不存在false
     */
    @GetMapping("/existsById")
    public Result<Boolean> existsById(long id) {
        return Result.o(userMongoService.existsById(id));
    }

    /**
     * <h3>是否存在</h3>
     * http://localhost:8080/exists<br>
     * {"name":"a"}<br>
     * name=a存在true不存在false
     */
    @PostMapping("/exists")
    public Result<Boolean> exists(@RequestBody UserMongo userMongo) {
        Example<UserMongo> example = Example.of(userMongo);
        return Result.o(userMongoService.exists(example));
    }

    /**
     * <h3>记录总数</h3>
     * http://localhost:8080/countAll<br>
     * 返回记录总数
     */
    @GetMapping("/countAll")
    public Result<Long> countAll() {
        return Result.o(userMongoService.countAll());
    }

    /**
     * <h3>记录总数</h3>
     * http://localhost:8080/count<br>
     * {"name":"a"}<br>
     * name=a的记录总数
     */
    @PostMapping("/count")
    public Result<Long> count(@RequestBody UserMongo userMongo) {
        Example<UserMongo> example = Example.of(userMongo);
        return Result.o(userMongoService.count(example));
    }

    /**
     * <h3>删除，通过id，不存在不会报错</h3>
     * http://localhost:8080/deleteById?id=1
     */
    @DeleteMapping("/deleteById")
    public Result deleteById(long id) {
        userMongoService.deleteById(id);
        return Result.o();
    }

    /**
     * <h3>删除，通过实体里的id，不存在不会报错</h3>
     * http://localhost:8080/delete<br>
     * {"id":1}
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody UserMongoVo userMongo) {
        userMongoService.delete(userMongo);
        return Result.o();
    }

    /**
     * <h3>删除多个，通过id，不存在不会报错</h3>
     * http://localhost:8080/deleteListById<br>
     * [1,2,3]
     */
    @DeleteMapping("/deleteListById")
    public Result deleteListById(@RequestBody List<Long> idList) {
        userMongoService.deleteListById(idList);
        return Result.o();
    }

    /**
     * <h3>删除多个，通过实体里的id，不存在不会报错</h3>
     * http://localhost:8080/deleteList<br>
     * [{"id":1},{"id":2},{"id":3}]
     */
    @DeleteMapping("/deleteList")
    public Result deleteList(@RequestBody List<UserMongo> userMongoList) {
        userMongoService.deleteList(userMongoList);
        return Result.o();
    }

    /**
     * <h3>删除所有</h3>
     */
    @DeleteMapping("/deleteAll")
    public Result deleteAll() {
        userMongoService.deleteAll();
        return Result.o();
    }

    /**
     * <h3>查找，通过id</h3>
     * http://localhost:8080/findById?id=1<br>
     * 存在:实体;不存在:null
     */
    @GetMapping("/findById")
    public Result<UserMongo> findById(long id) {
        return Result.o(userMongoService.findById(id));
    }

    /**
     * <h3>查找第一个</h3>
     * http://localhost:8080/findOne<br>
     * {"name":"a"}<br>
     * 存在:实体;不存在:null
     */
    @PostMapping("/findOne")
    public Result<UserMongo> findOne(@RequestBody UserMongo userMongo) {
        Example<UserMongo> example = Example.of(userMongo);
        return Result.o(userMongoService.findOne(example));
    }

    /**
     * <h3>查找多个，通过id</h3>
     * http://localhost:8080/findListById<br>
     * [1,2,3]<br>
     * 存在:[实体];不存在:[]
     */
    @PostMapping("/findListById")
    public Result<List<UserMongo>> findListById(@RequestBody List<Long> idList) {
        return Result.o(userMongoService.findListById(idList));
    }

    /**
     * <h3>查找所有</h3>
     * http://localhost:8080/findAll<br>
     * [实体]
     */
    @GetMapping("/findAll")
    public Result<List<UserMongo>> findAll() {
        return Result.o(userMongoService.findAll());
    }

    /**
     * <h3>查找所有，根据指定字段倒序</h3>
     * http://localhost:8080/findListSort?field=name<br>
     * 根据name倒序的[实体]
     */
    @GetMapping("/findListSort")
    public Result<List<UserMongo>> findList(String field) {
        Sort sort = Sort.by(Sort.Order.desc(field));
        return Result.o(userMongoService.findList(sort));
    }

    /**
     * <h3>查找所有，根据Example</h3>
     * http://localhost:8080/findListExample<br>
     * {"name":"a"}<br>
     * name=a的[实体]
     */
    @PostMapping("/findListExample")
    public Result<List<UserMongo>> findList(@RequestBody UserMongo userMongo) {
        Example<UserMongo> example = Example.of(userMongo);
        return Result.o(userMongoService.findList(example));
    }

    /**
     * <h3>查找所有，根据Example和指定字段倒序</h3>
     * http://localhost:8080/findListExampleSort?field=id<br>
     * {"name":"a"}<br>
     * name=a并且按照id倒序的[实体]
     */
    @PostMapping("/findListExampleSort")
    public Result<List<UserMongo>> findList(@RequestBody UserMongo userMongo, String field) {
        Example<UserMongo> example = Example.of(userMongo);
        Sort sort = Sort.by(Sort.Order.desc(field));
        return Result.o(userMongoService.findList(example, sort));
    }

    /**
     * <h3>查找所有，分页</h3>
     * http://localhost:8080/findListPage?page=1&size=1<br>
     * PageInfo
     */
    @GetMapping("/findListPage")
    public Result<PageInfo<UserMongo>> findList(int page, int size) {
        PageRequestFix pageRequestFix = PageRequestFix.of(page, size);
        return Result.o(userMongoService.findList(pageRequestFix));
    }

    /**
     * <h3>查找所有，分页，根据指定字段倒序</h3>
     * http://localhost:8080/findListPageSort?page=1&size=1&field=id<br>
     * PageInfo
     */
    @GetMapping("/findListPageSort")
    public Result<PageInfo<UserMongo>> findListSort(int page, int size, String field) {
        Sort sort = Sort.by(Sort.Order.desc(field));
        PageRequestFix pageRequestFix = PageRequestFix.of(page, size, sort);
        return Result.o(userMongoService.findList(pageRequestFix));
    }

    /**
     * <h3>分页查找</h3>
     * http://localhost:8080/findPage<br>
     * {"pages":"1","rows":"2","orderBy":"name desc"}<br>
     * PageInfo
     */
    @PostMapping("/findPage")
    public Result<PageInfo<UserMongo>> findPage(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findPage(userMongo));
    }

    /**
     * <h3>分页查找</h3>
     * http://localhost:8080/findPage2<br>
     * {"pages":"1","rows":"10","date":"2022-01-05","dateEnd":"2022-01-06"}<br>
     * {"pages":"1","rows":"10","paramQueryList":[{"field":"date","value":"2023-03-09","value2":"2023-03-10","type":"timestamp","operator":"bt"}]}<br>
     * PageInfo
     */
    @PostMapping("/findPage2")
    public Result<PageInfo<UserMongo>> findPage2(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findPage2(userMongo));
    }

    /**
     * <h3>排序查找</h3>
     * http://localhost:8080/findSort<br>
     * {"orderBy":"name desc"}<br>
     * List
     */
    @PostMapping("/findSort")
    public Result<List<UserMongo>> findSort(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findSort(userMongo));
    }

    /**
     * <h3>排序查找2</h3>
     * http://localhost:8080/findSort2<br>
     * {"orderBy":"name desc","date":"2022-01-05","dateEnd":"2022-01-06"}<br>
     * PageInfo
     */
    @PostMapping("/findSort2")
    public Result<PageInfo<UserMongo>> findSort2(@RequestBody UserMongoVo userMongo) {
        return Result.o(userMongoService.findSort2(userMongo));
    }

    /**
     * <h3>查找所有，根据Example和分页</h3>
     * http://localhost:8080/findListPage?page=1&size=1<br>
     * {"name":"a"}<br>
     * PageInfo
     */
    @PostMapping("/findListExamplePage")
    public Result<PageInfo<UserMongo>> findList(@RequestBody UserMongo userMongo, int page, int size) {
        Example<UserMongo> example = Example.of(userMongo);
        PageRequestFix pageRequestFix = PageRequestFix.of(page, size);
        return Result.o(userMongoService.findList(example, pageRequestFix));
    }

    /**
     * <h3>根据名字查询并分页</h3>
     * http://localhost:8080/findByName?name=a&page=1&size=1<br>
     * PageInfo
     */
    @GetMapping("/findByName")
    public Result<PageInfo<UserMongo>> findByName(String name, int page, int size) {
        PageRequestFix pageRequestFix = PageRequestFix.of(page, size);
        return Result.o(userMongoService.findByName(name, pageRequestFix));
    }

    /**
     * 关注+1
     * http://localhost:8080/addFollowers?id=1<br>
     * UpdateResult
     */
    @GetMapping("/addFollowers")
    public Result<UpdateResult> addFollowers(long id) {
        return Result.o(userMongoService.addFollowers(id));
    }

}
