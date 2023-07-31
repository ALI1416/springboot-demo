package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.util.RedisUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>SetController</h1>
 *
 * <p>
 * createDate 2021/11/30 11:24:38
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("set")
public class SetController {

    /**
     * <h3>添加</h3>
     * POST/set/sAdd?key=a&value=a 1<br>
     * POST/set/sAdd?key=a&value=b 1<br>
     * POST/set/sAdd?key=a&value=a 0
     */
    @PostMapping("sAdd")
    public Result sAdd(String key, String value) {
        return Result.o(RedisUtils.sAdd(key, value));
    }

    /**
     * <h3>添加多个</h3>
     * POST /set/sAddMulti?key=a<br>
     * body JSON ["a","b"] 2<br>
     * body JSON ["b","c"] 1
     */
    @PostMapping("sAddMulti")
    public Result sAddMulti(String key, @RequestBody List<String> value) {
        return Result.o(RedisUtils.sAddMulti(key, value));
    }

    /**
     * <h3>添加多个</h3>
     * POST /set/sAddMultiArray?key=a&value=b 2<br>
     * POST /set/sAddMultiArray?key=b&value=c 1
     */
    @PostMapping("sAddMultiArray")
    public Result sAddMultiArray(String key, String[] value) {
        return Result.o(RedisUtils.sAddMulti(key, value));
    }

    /**
     * <h3>删除值</h3>
     * 已存在键a值a/b/c<br>
     * POST /set/sDelete?key=a&value=a 1<br>
     * POST /set/sDelete?key=a&value=d 0<br>
     * POST /set/sDelete?key=b&value=d 0
     */
    @PostMapping("sDelete")
    public Result sDelete(String key, String value) {
        return Result.o(RedisUtils.sDelete(key, value));
    }

    /**
     * <h3>删除多个值</h3>
     * POST /set/sDeleteMulti?key=a<br>
     * body JSON ["a","b"] 2<br>
     * body JSON ["b","c"] 1
     */
    @PostMapping("sDeleteMulti")
    public Result sDeleteMulti(String key, @RequestBody List<String> value) {
        return Result.o(RedisUtils.sDeleteMulti(key, value));
    }

    /**
     * <h3>删除多个值</h3>
     * POST /set/sDeleteMultiArray?key=a&value=a&value=b 2<br>
     * POST /set/sDeleteMultiArray?key=b&value=c&value=d 1
     */
    @PostMapping("sDeleteMultiArray")
    public Result sDeleteMultiArray(String key, String[] value) {
        return Result.o(RedisUtils.sDeleteMulti(key, value));
    }

    /**
     * <h3>返回并删除1个随机值</h3>
     * 已存在值a/b/c<br>
     * POST /set/sPop?key=a c<br>
     * POST /set/sPop?key=a a
     */
    @PostMapping("sPop")
    public Result sPop(String key) {
        return Result.o(RedisUtils.sPop(key));
    }

    /**
     * <h3>返回并删除多个随机值</h3>
     * 已存在值a/b/c<br>
     * POST /set/sPopMulti?key=a&count=2<br>
     * ["b","c"]
     */
    @PostMapping("sPopMulti")
    public Result sPopMulti(String key, long count) {
        return Result.o(RedisUtils.sPopMulti(key, count));
    }

    /**
     * <h3>移动元素到目的键</h3>
     * POST /set/sMove?key=a&value=b&destKey=c<br>
     * true
     */
    @PostMapping("sMove")
    public Result sMove(String key, String value, String destKey) {
        return Result.o(RedisUtils.sMove(key, value, destKey));
    }

    /**
     * <h3>元素个数</h3>
     * POST /set/sSize?key=a<br>
     * 3
     */
    @PostMapping("sSize")
    public Result sSize(String key) {
        return Result.o(RedisUtils.sSize(key));
    }

    /**
     * <h3>是否存在元素</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sIsMember?key=a&value=a true<br>
     * POST /set/sIsMember?key=a&value=e false<br>
     * POST /set/sIsMember?key=b&value=a false
     */
    @PostMapping("sIsMember")
    public Result sIsMember(String key, String value) {
        return Result.o(RedisUtils.sIsMember(key, value));
    }

    /**
     * <h3>是否存在多个元素</h3>
     * 存在元素a/b/c
     * POST /set/sIsMultiMember?key=a<br>
     * body JSON ["a","b","d"] {"a":true,"b":true,"d":false}<br>
     */
    @PostMapping("sIsMultiMember")
    public Result sIsMultiMember(String key, @RequestBody List<String> value) {
        return Result.o(RedisUtils.sIsMultiMember(key, value));
    }

    /**
     * <h3>是否存在多个元素</h3>
     * 存在元素a/b/c
     * POST /set/sIsMultiMember?key=a&value=a&value=b&value=d<br>
     * {"a":true,"b":true,"d":false}
     */
    @PostMapping("sIsMultiMemberArray")
    public Result sIsMultiMemberArray(String key, String[] value) {
        return Result.o(RedisUtils.sIsMultiMemberArray(key, value));
    }

    /**
     * <h3>所有元素</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sMembers?key=a ["a","b","c"]<br>
     * POST /set/sMembers?key=b []
     */
    @PostMapping("sMembers")
    public Result sMembers(String key) {
        return Result.o(RedisUtils.sMembers(key));
    }

    /**
     * <h3>随机获取1个元素</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sRandomMember?key=a a<br>
     * POST /set/sRandomMember?key=a c<br>
     * POST /set/sRandomMember?key=b null
     */
    @PostMapping("sRandomMember")
    public Result sRandomMember(String key) {
        return Result.o(RedisUtils.sRandomMember(key));
    }

    /**
     * <h3>随机获取多个元素</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sRandomMember?key=a&count=2 ["a","b"]<br>
     * POST /set/sRandomMember?key=a&count=2 ["a","a"]<br>
     * POST /set/sRandomMember?key=b&count=2 []
     */
    @PostMapping("sRandomMultiMember")
    public Result sRandomMultiMember(String key, long count) {
        return Result.o(RedisUtils.sRandomMultiMember(key, count));
    }

    /**
     * <h3>随机获取多个元素(不重复)</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sRandomMultiDistinctMember?key=a&count=2 ["a","b"]<br>
     * POST /set/sRandomMultiDistinctMember?key=a&count=2 ["a","c"]<br>
     * POST /set/sRandomMultiDistinctMember?key=b&count=2 []
     */
    @PostMapping("sRandomMultiDistinctMember")
    public Result sRandomMultiDistinctMember(String key, long count) {
        return Result.o(RedisUtils.sRandomMultiDistinctMember(key, count));
    }

    /**
     * <h3>两键交集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sIntersect?key=a&otherKey=b<br>
     * ["b","c"]
     */
    @PostMapping("sIntersect")
    public Result sIntersect(String key, String otherKey) {
        return Result.o(RedisUtils.sIntersect(key, otherKey));
    }

    /**
     * <h3>键与其他键的交集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sIntersectMulti?key=a<br>
     * body JSON ["b","c"]<br>
     * ["c"]
     */
    @PostMapping("sIntersectMulti")
    public Result sIntersectMulti(String key, @RequestBody List<String> otherKey) {
        return Result.o(RedisUtils.sIntersectMulti(key, otherKey));
    }

    /**
     * <h3>所有键的交集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sIntersectAll<br>
     * body JSON ["a","b","c"]<br>
     * ["c"]
     */
    @PostMapping("sIntersectAll")
    public Result sIntersect(@RequestBody List<String> keys) {
        return Result.o(RedisUtils.sIntersectAll(keys));
    }

    /**
     * <h3>两键交集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sIntersectAndStore?key=a&otherKey=b&destKey=z<br>
     * 2
     */
    @PostMapping("sIntersectAndStore")
    public Result sIntersectAndStore(String key, String otherKey, String destKey) {
        return Result.o(RedisUtils.sIntersectAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>键与其他键的交集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sIntersectMultiAndStore?key=a&destKey=z<br>
     * body JSON ["b","c"]<br>
     * 1
     */
    @PostMapping("sIntersectMultiAndStore")
    public Result sIntersectMultiAndStore(String key, @RequestBody List<String> otherKey, String destKey) {
        return Result.o(RedisUtils.sIntersectMultiAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>所有键的交集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sIntersectAllAndStore?destKey=z<br>
     * body JSON ["a","b","c"]<br>
     * 1
     */
    @PostMapping("sIntersectAllAndStore")
    public Result sIntersectAndStore(@RequestBody List<String> keys, String destKey) {
        return Result.o(RedisUtils.sIntersectAllAndStore(keys, destKey));
    }

    /**
     * <h3>两键并集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sUnion?key=a&otherKey=b<br>
     * ["c","a","b","d"]
     */
    @PostMapping("sUnion")
    public Result sUnion(String key, String otherKey) {
        return Result.o(RedisUtils.sUnion(key, otherKey));
    }

    /**
     * <h3>键与其他键的并集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sUnionMulti?key=a<br>
     * body JSON ["b","c"]<br>
     * ["c","a","b","d","e"]
     */
    @PostMapping("sUnionMulti")
    public Result sUnionMulti(String key, @RequestBody List<String> otherKey) {
        return Result.o(RedisUtils.sUnionMulti(key, otherKey));
    }

    /**
     * <h3>所有键的并集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sUnionAll?key=a<br>
     * body JSON ["a","b","c"]<br>
     * ["c","a","b","d","e"]
     */
    @PostMapping("sUnionAll")
    public Result sUnion(@RequestBody List<String> keys) {
        return Result.o(RedisUtils.sUnionAll(keys));
    }

    /**
     * <h3>两键并集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sUnionAndStore?key=a&otherKey=b&destKey=z<br>
     * 4
     */
    @PostMapping("sUnionAndStore")
    public Result sUnionAndStore(String key, String otherKey, String destKey) {
        return Result.o(RedisUtils.sUnionAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>键与其他键的并集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sUnionMultiAndStore?key=a&destKey=z<br>
     * body JSON ["b","c"]<br>
     * 5
     */
    @PostMapping("sUnionMultiAndStore")
    public Result sUnionMultiAndStore(String key, @RequestBody List<String> otherKey, String destKey) {
        return Result.o(RedisUtils.sUnionMultiAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>所有键的并集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sUnionAllAndStore?destKey=z<br>
     * body JSON ["a","b","c"]<br>
     * 5
     */
    @PostMapping("sUnionAllAndStore")
    public Result sUnionAndStore(@RequestBody List<String> keys, String destKey) {
        return Result.o(RedisUtils.sUnionAllAndStore(keys, destKey));
    }

    /**
     * <h3>两键差集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sDifference?key=a&otherKey=b<br>
     * ["a"]
     */
    @PostMapping("sDifference")
    public Result sDifference(String key, String otherKey) {
        return Result.o(RedisUtils.sDifference(key, otherKey));
    }

    /**
     * <h3>键与其他键的差集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sDifferenceMulti?key=a<br>
     * body JSON ["b","c"]<br>
     * []
     */
    @PostMapping("sDifferenceMulti")
    public Result sDifferenceMulti(String key, @RequestBody List<String> otherKey) {
        return Result.o(RedisUtils.sDifferenceMulti(key, otherKey));
    }

    /**
     * <h3>所有键的差集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sDifferenceAll?key=a<br>
     * body JSON ["a","b","c"]<br>
     * []
     */
    @PostMapping("sDifferenceAll")
    public Result sDifference(@RequestBody List<String> keys) {
        return Result.o(RedisUtils.sDifferenceAll(keys));
    }

    /**
     * <h3>两键差集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sDifferenceAndStore?key=a&otherKey=b&destKey=z<br>
     * 1
     */
    @PostMapping("sDifferenceAndStore")
    public Result sDifferenceAndStore(String key, String otherKey, String destKey) {
        return Result.o(RedisUtils.sDifferenceAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>键与其他键的差集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sDifferenceMultiAndStore?key=a&destKey=z<br>
     * body JSON ["b","c"]<br>
     * 0
     */
    @PostMapping("sDifferenceMultiAndStore")
    public Result sDifferenceMultiAndStore(String key, @RequestBody List<String> otherKey, String destKey) {
        return Result.o(RedisUtils.sDifferenceMultiAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>所有键的差集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sDifferenceAllAndStore?destKey=z<br>
     * body JSON ["a","b","c"]<br>
     * 0
     */
    @PostMapping("sDifferenceAllAndStore")
    public Result sDifferenceAndStore(@RequestBody List<String> keys, String destKey) {
        return Result.o(RedisUtils.sDifferenceAllAndStore(keys, destKey));
    }

    /**
     * <h3>模糊查询</h3>
     * 实际存在键a值有a/aabbcc/abc/abd/b/bc/bd/c/[]<br>
     * POST /sScan?key=a&match=a 不匹配字符[a]<br>
     * POST /sScan?key=a&match=b? 右侧匹配1个字符[bc,bd]<br>
     * POST /sScan?key=a&match=*c 左侧匹配0+个字符[aabbcc,abc,bc,c]<br>
     * POST /sScan?key=a&match=*b* 两侧匹配0+个字符[aabbcc,abc,abd,b,bc,bd]<br>
     * POST /sScan?key=a&match=[abd] 匹配1个指定字符[a,b]<br>
     * POST /sScan?key=a&match=[^abd] 不匹配1个指定字符[c]<br>
     * POST /sScan?key=a&match=[A-z] 匹配1个指定字符[a,b,c]<br>
     * POST /sScan?key=a&match=\[* 转义匹配匹配1个指定字符[[]]
     */
    @PostMapping("sScan")
    public Result sScan(String key, String match) {
        return Result.o(RedisUtils.sScan(key, match));
    }

}
